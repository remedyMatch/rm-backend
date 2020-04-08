package io.remedymatch.angebot.domain.service;

import static io.remedymatch.angebot.process.AngebotAnfrageProzessConstants.PROZESS_KEY;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import io.remedymatch.angebot.domain.model.Angebot;
import io.remedymatch.angebot.domain.model.AngebotAnfrage;
import io.remedymatch.angebot.domain.model.AngebotAnfrageStatus;
import io.remedymatch.angebot.domain.model.AngebotId;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageEntity;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageJpaRepository;
import io.remedymatch.angebot.infrastructure.AngebotEntity;
import io.remedymatch.angebot.infrastructure.AngebotJpaRepository;
import io.remedymatch.artikel.domain.model.ArtikelVarianteId;
import io.remedymatch.artikel.domain.service.ArtikelEntityConverter;
import io.remedymatch.artikel.domain.service.ArtikelSucheService;
import io.remedymatch.artikel.infrastructure.ArtikelVarianteEntity;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.domain.OperationNotAlloudException;
import io.remedymatch.engine.client.EngineClient;
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
public class AngebotAnlageService {
	private static final String EXCEPTION_MSG_ANGEBOT_NICHT_GEFUNDEN = "Angebot mit diesem Id nicht gefunden. (Id: %s)";
	private static final String EXCEPTION_MSG_ARTIKEL_NICHT_GEFUNDEN = "Artikel mit diesem Id nicht gefunden. (Id: %s)";
	private static final String EXCEPTION_MSG_STANDORT_NICHT_VON_USER_INSTITUTION = "Standort gehoert nicht der Institution des angemeldetes Benutzers. (Id: %s)";
	private static final String EXCEPTION_MSG_ANGEBOT_NICHT_VON_USER_INSTITUTION = "Angebot gehoert nicht der Institution des angemeldetes Benutzers. (Id: %s)";

	private final AngebotJpaRepository angebotRepository;
	private final AngebotAnfrageJpaRepository angebotAnfrageRepository;

	private final UserService userService;
	private final ArtikelSucheService artikelSucheService;
	private final GeoCalcService geoCalcService;
	private final EngineClient engineClient;

	@Transactional
	public Angebot neueAngebotEinstellen(final @NotNull @Valid NeuesAngebot neuesAngebot) {
		val userInstitution = getUserInstitution();

		return mitEntfernung(angebotRepository.save(AngebotEntity.builder() //
				.anzahl(neuesAngebot.getAnzahl()) //
				.rest(neuesAngebot.getAnzahl()) //
				.artikelVariante(getArtikelVariante(neuesAngebot.getArtikelVarianteId())) //
				.institution(userInstitution) //
				.standort(getStandort(userInstitution, neuesAngebot.getStandortId())) //
				.haltbarkeit(neuesAngebot.getHaltbarkeit()) //
				.steril(neuesAngebot.isSteril()) //
				.originalverpackt(neuesAngebot.isOriginalverpackt()) //
				.medizinisch(neuesAngebot.isMedizinisch()) //
				.kommentar(neuesAngebot.getKommentar()) //
				.bedient(false) //
				.build()));
	}

	@Transactional
	public AngebotAnfrage angebotAnfrageErstellen(//
			final @NotNull @Valid AngebotId angebotId, //
			final @NotNull @Valid InstitutionStandortId standortId, //
			final @NotBlank String kommentar, //
			final @NotNull BigDecimal anzahl) {
		val userInstitution = getUserInstitution();
		val angebot = getAngebot(userInstitution, angebotId);

		var anfrage = angebotAnfrageRepository.save(AngebotAnfrageEntity.builder() //
				.angebot(angebot) //
				.institutionVon(userInstitution) //
				.standortVon(getStandort(userInstitution, standortId)) //
				.anzahl(anzahl) //
				.kommentar(kommentar) //
				.status(AngebotAnfrageStatus.Offen) //
				.build());
		val anfrageId = anfrage.getId();

		anfrage.setProzessInstanzId(engineClient.prozessStarten(//
				PROZESS_KEY, //
				anfrageId.toString(), //
				Variables.createVariables()//
						.putValue("institution", angebot.getInstitution().getId().toString()) //
						.putValue("objektId", anfrageId.toString())));

		angebotAnfrageRepository.save(anfrage);
		return AngebotAnfrageEntityConverter.convertAnfrage(anfrage);
	}

	private Angebot mitEntfernung(final AngebotEntity angebot) {
		val convertedAngebot = AngebotEntityConverter.convert(angebot);
		convertedAngebot.setEntfernung(geoCalcService.berechneUserDistanzInKilometer(convertedAngebot.getStandort()));
		return convertedAngebot;
	}

	private AngebotEntity getAngebot(//
			final @NotNull InstitutionEntity userInstitution, //
			final @NotNull @Valid AngebotId angebotId) {
		Assert.notNull(userInstitution, "InstitutionEntity ist null.");
		Assert.notNull(angebotId, "AngebotId ist null.");

		val angebot = angebotRepository.findById(angebotId.getValue()) //
				.orElseThrow(() -> new ObjectNotFoundException(
						String.format(EXCEPTION_MSG_ANGEBOT_NICHT_GEFUNDEN, angebotId.getValue())));

		if (!userInstitution.getId().equals(angebot.getInstitution().getId())) {
			throw new OperationNotAlloudException(
					String.format(EXCEPTION_MSG_ANGEBOT_NICHT_VON_USER_INSTITUTION, angebotId.getValue()));
		}

		return angebot;
	}

	private ArtikelVarianteEntity getArtikelVariante(//
			final @NotNull @Valid ArtikelVarianteId artikelVarianteId) {
		Assert.notNull(artikelVarianteId, "ArtikelVarianteId sind null.");

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
