package io.remedymatch.bedarf.domain.service;

import static io.remedymatch.bedarf.process.BedarfAnfrageProzessConstants.PROZESS_KEY;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.model.ArtikelVarianteId;
import io.remedymatch.artikel.domain.service.ArtikelEntityConverter;
import io.remedymatch.artikel.domain.service.ArtikelSucheService;
import io.remedymatch.artikel.infrastructure.ArtikelEntity;
import io.remedymatch.artikel.infrastructure.ArtikelVarianteEntity;
import io.remedymatch.bedarf.domain.model.Bedarf;
import io.remedymatch.bedarf.domain.model.BedarfAnfrage;
import io.remedymatch.bedarf.domain.model.BedarfAnfrageStatus;
import io.remedymatch.bedarf.domain.model.BedarfId;
import io.remedymatch.bedarf.infrastructure.BedarfAnfrageEntity;
import io.remedymatch.bedarf.infrastructure.BedarfAnfrageJpaRepository;
import io.remedymatch.bedarf.infrastructure.BedarfEntity;
import io.remedymatch.bedarf.infrastructure.BedarfJpaRepository;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.domain.OperationNotAlloudException;
import io.remedymatch.engine.client.EngineClient;
import io.remedymatch.engine.domain.BusinessKey;
import io.remedymatch.geodaten.geocoding.domain.GeoCalcService;
import io.remedymatch.institution.domain.InstitutionEntityConverter;
import io.remedymatch.institution.domain.InstitutionStandortId;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import io.remedymatch.user.domain.UserService;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Service
@Validated
@Transactional
public class BedarfAnlageService {
	private static final String EXCEPTION_MSG_ANGEBOT_NICHT_GEFUNDEN = "Bedarf mit diesem Id nicht gefunden. (Id: %s)";
	private static final String EXCEPTION_MSG_ARTIKEL_NICHT_GEFUNDEN = "Artikel mit diesem Id nicht gefunden. (Id: %s)";
	private static final String EXCEPTION_MSG_STANDORT_NICHT_VON_USER_INSTITUTION = "Standort gehoert nicht der Institution des angemeldetes Benutzers. (Id: %s)";
	private static final String EXCEPTION_MSG_ANGEBOT_NICHT_VON_USER_INSTITUTION = "Bedarf gehoert nicht der Institution des angemeldetes Benutzers. (Id: %s)";

	private final BedarfJpaRepository bedarfRepository;
	private final BedarfAnfrageJpaRepository bedarfAnfrageRepository;

	private final UserService userService;
	private final ArtikelSucheService artikelSucheService;
	private final GeoCalcService geoCalcService;
	private final EngineClient engineClient;

	@Transactional
	public Bedarf neueBedarfEinstellen(final @NotNull @Valid NeuesBedarf neuesBedarf) {
		val userInstitution = getUserInstitution();

		return mitEntfernung(bedarfRepository.save(BedarfEntity.builder() //
				.anzahl(neuesBedarf.getAnzahl()) //
				.rest(neuesBedarf.getAnzahl()) //
				.artikel(getArtikel(neuesBedarf.getArtikelId())) //
				.artikelVariante(getArtikelVariante(neuesBedarf.getArtikelVarianteId())) //
				.institution(userInstitution) //
				.standort(getStandort(userInstitution, neuesBedarf.getStandortId())) //
				.steril(neuesBedarf.isSteril()) //
				.medizinisch(neuesBedarf.isMedizinisch()) //
				.kommentar(neuesBedarf.getKommentar()) //
				.bedient(false) //
				.build()));
	}

	@Transactional
	public BedarfAnfrage bedarfAnfrageErstellen(//
			final @NotNull @Valid BedarfId bedarfId, //
			final @NotNull @Valid InstitutionStandortId standortId, //
			final @NotBlank String kommentar, //
			final @NotNull BigDecimal anzahl) {
		val userInstitution = getUserInstitution();
		val bedarf = getBedarf(userInstitution, bedarfId);

		var anfrage = bedarfAnfrageRepository.save(BedarfAnfrageEntity.builder() //
				.bedarf(bedarf) //
				.institutionVon(userInstitution) //
				.standortVon(getStandort(userInstitution, standortId)) //
				.anzahl(anzahl) //
				.kommentar(kommentar) //
				.status(BedarfAnfrageStatus.Offen) //
				.build());
		val anfrageId = anfrage.getId();

		anfrage.setProzessInstanzId(engineClient.prozessStarten(//
				PROZESS_KEY, //
				new BusinessKey(anfrageId.toString()), //
				Variables.createVariables()//
						.putValue("institution", bedarf.getInstitution().getId().toString()) //
						.putValue("objektId", anfrageId.toString())).getValue());

		bedarfAnfrageRepository.save(anfrage);
		return BedarfAnfrageEntityConverter.convertAnfrage(anfrage);
	}

	private Bedarf mitEntfernung(final BedarfEntity bedarf) {
		val convertedBedarf = BedarfEntityConverter.convert(bedarf);
		convertedBedarf.setEntfernung(geoCalcService.berechneUserDistanzInKilometer(convertedBedarf.getStandort()));
		return convertedBedarf;
	}

	private BedarfEntity getBedarf(//
			final @NotNull InstitutionEntity userInstitution, //
			final @NotNull @Valid BedarfId bedarfId) {
		Assert.notNull(userInstitution, "InstitutionEntity ist null.");
		Assert.notNull(bedarfId, "BedarfId ist null.");

		val bedarf = bedarfRepository.findById(bedarfId.getValue()) //
				.orElseThrow(() -> new ObjectNotFoundException(
						String.format(EXCEPTION_MSG_ANGEBOT_NICHT_GEFUNDEN, bedarfId.getValue())));

		if (!userInstitution.getId().equals(bedarf.getInstitution().getId())) {
			throw new OperationNotAlloudException(
					String.format(EXCEPTION_MSG_ANGEBOT_NICHT_VON_USER_INSTITUTION, bedarfId.getValue()));
		}

		return bedarf;
	}

	private ArtikelEntity getArtikel(//
			final @NotNull @Valid ArtikelId artikelId) {
		Assert.notNull(artikelId, "ArtikelId sind null.");

		return artikelSucheService.findArtikel(artikelId).map(ArtikelEntityConverter::convertArtikel) //
				.orElseThrow(() -> new ObjectNotFoundException(
						String.format(EXCEPTION_MSG_ARTIKEL_NICHT_GEFUNDEN, artikelId.getValue())));
	}

	private ArtikelVarianteEntity getArtikelVariante(//
			final @NotNull @Valid ArtikelVarianteId artikelVarianteId) {
		Assert.notNull(artikelVarianteId, "ArtikelVarianteId sind null.");

		// FIXME: ist optional
		
		return artikelSucheService.findArtikelVariante(artikelVarianteId).map(ArtikelEntityConverter::convertVariante) //
				.orElseThrow(() -> new ObjectNotFoundException(
						String.format(EXCEPTION_MSG_ARTIKEL_NICHT_GEFUNDEN, artikelVarianteId.getValue())));
	}

	private InstitutionEntity getUserInstitution() {
		return InstitutionEntityConverter.convert(userService.getContextInstitution());
	}

	private InstitutionStandortEntity getStandort( //
			final @NotNull InstitutionEntity userInstitution, //
			final @NotNull @Valid InstitutionStandortId institutionStandortId) {
		Assert.notNull(userInstitution, "InstitutionEntity ist null.");
		Assert.notNull(institutionStandortId, "InstitutionStandortId ist null.");

		return userInstitution.findStandort(institutionStandortId.getValue()) //
				.orElseThrow(() -> new ObjectNotFoundException(String
						.format(EXCEPTION_MSG_STANDORT_NICHT_VON_USER_INSTITUTION, institutionStandortId.getValue())));

	}
}
