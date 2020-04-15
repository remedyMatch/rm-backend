package io.remedymatch.bedarf.domain.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.model.ArtikelVariante;
import io.remedymatch.artikel.domain.model.ArtikelVarianteId;
import io.remedymatch.artikel.domain.service.ArtikelEntityConverter;
import io.remedymatch.artikel.domain.service.ArtikelSucheService;
import io.remedymatch.artikel.infrastructure.ArtikelEntity;
import io.remedymatch.artikel.infrastructure.ArtikelVarianteEntity;
import io.remedymatch.bedarf.domain.model.Bedarf;
import io.remedymatch.bedarf.domain.model.NeuesBedarf;
import io.remedymatch.bedarf.infrastructure.BedarfEntity;
import io.remedymatch.bedarf.infrastructure.BedarfJpaRepository;
import io.remedymatch.domain.NotUserInstitutionObjectException;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.domain.OperationNotAlloudException;
import io.remedymatch.geodaten.geocoding.domain.GeoCalcService;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
import io.remedymatch.institution.domain.service.InstitutionEntityConverter;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import io.remedymatch.usercontext.UserContextService;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Service
@Validated
@Transactional
public class BedarfAnlageService {
	private static final String EXCEPTION_MSG_ARTIKEL_NICHT_GEFUNDEN = "Artikel mit diesem Id nicht gefunden. (Id: %s)";
	private static final String EXCEPTION_MSG_ARTIKEL_VARIANTE_NICHT_GEFUNDEN = "ArtikelVariante mit diesem Id nicht gefunden. (Id: %s)";
	private static final String EXCEPTION_MSG_ARTIKEL_UND_ARTIKEL_VARIANTE_PASSEN_NICHT_ZUSAMMEN = "Artikel und ArtikelVariante passen nicht zusammen.";
	private static final String EXCEPTION_MSG_ARTIKEL_UND_ARTIKEL_VARIANTE_LEER = "Artikel und ArtikelVariante sind beide leer.";

	private static final String EXCEPTION_MSG_STANDORT_NICHT_VON_USER_INSTITUTION = "Standort gehoert nicht der Institution des angemeldetes Benutzers. (Id: %s)";

	private final BedarfJpaRepository bedarfRepository;

	private final UserContextService userService;
	private final ArtikelSucheService artikelSucheService;
	private final GeoCalcService geoCalcService;

	@Transactional
	public Bedarf neuesBedarfEinstellen(final @NotNull @Valid NeuesBedarf neuesBedarf) {
		if (neuesBedarf.getArtikelId() == null && neuesBedarf.getArtikelVarianteId() == null) {
			throw new OperationNotAlloudException(EXCEPTION_MSG_ARTIKEL_UND_ARTIKEL_VARIANTE_LEER);
		}
		
		val userInstitution = getUserInstitution();

		ArtikelEntity artikelEntity = null;
		ArtikelVarianteEntity artikelVarianteEntity = null;
		if (neuesBedarf.getArtikelVarianteId() != null) {
			val artikelVariante = getArtikelVariante(neuesBedarf.getArtikelVarianteId());
			if (neuesBedarf.getArtikelId() != null
					&& !artikelVariante.getArtikelId().equals(neuesBedarf.getArtikelId())) {
				throw new OperationNotAlloudException(EXCEPTION_MSG_ARTIKEL_UND_ARTIKEL_VARIANTE_PASSEN_NICHT_ZUSAMMEN);
			}

			artikelVarianteEntity = ArtikelEntityConverter.convertVariante(artikelVariante);
			artikelEntity = getArtikel(artikelVariante.getArtikelId());
		} else {
			artikelEntity = getArtikel(neuesBedarf.getArtikelId());
		}

		return mitEntfernung(bedarfRepository.save(BedarfEntity.builder() //
				.anzahl(neuesBedarf.getAnzahl()) //
				.rest(neuesBedarf.getAnzahl()) //
				.artikel(artikelEntity) //
				.artikelVariante(artikelVarianteEntity) //
				.institution(userInstitution) //
				.standort(getUserInstitutionStandort(userInstitution, neuesBedarf.getStandortId())) //
				.steril(neuesBedarf.isSteril()) //
				.medizinisch(neuesBedarf.isMedizinisch()) //
				.kommentar(neuesBedarf.getKommentar()) //
				.bedient(false) //
				.build()));
	}

	private Bedarf mitEntfernung(final @NotNull BedarfEntity bedarf) {
		val convertedBedarf = BedarfEntityConverter.convertBedarf(bedarf);
		convertedBedarf.setEntfernung(geoCalcService.berechneUserDistanzInKilometer(convertedBedarf.getStandort()));
		return convertedBedarf;
	}

	ArtikelEntity getArtikel(//
			final @NotNull @Valid ArtikelId artikelId) {
		Assert.notNull(artikelId, "ArtikelId sind null.");

		return artikelSucheService.findArtikel(artikelId).map(ArtikelEntityConverter::convertArtikel) //
				.orElseThrow(() -> new ObjectNotFoundException(
						String.format(EXCEPTION_MSG_ARTIKEL_NICHT_GEFUNDEN, artikelId.getValue())));
	}

	ArtikelVariante getArtikelVariante(//
			final @NotNull @Valid ArtikelVarianteId artikelVarianteId) {
		Assert.notNull(artikelVarianteId, "ArtikelVarianteId sind null.");

		return artikelSucheService.findArtikelVariante(artikelVarianteId) //
				.orElseThrow(() -> new ObjectNotFoundException(
						String.format(EXCEPTION_MSG_ARTIKEL_VARIANTE_NICHT_GEFUNDEN, artikelVarianteId.getValue())));
	}

	private InstitutionEntity getUserInstitution() {
		return InstitutionEntityConverter.convertInstitution(userService.getContextInstitution());
	}

	InstitutionStandortEntity getUserInstitutionStandort( //
			final @NotNull InstitutionEntity userInstitution, //
			final @NotNull @Valid InstitutionStandortId institutionStandortId) {
		Assert.notNull(userInstitution, "InstitutionEntity ist null.");
		Assert.notNull(institutionStandortId, "InstitutionStandortId ist null.");

		return userInstitution.findStandort(institutionStandortId.getValue()) //
				.orElseThrow(() -> new NotUserInstitutionObjectException(String
						.format(EXCEPTION_MSG_STANDORT_NICHT_VON_USER_INSTITUTION, institutionStandortId.getValue())));
	}
}
