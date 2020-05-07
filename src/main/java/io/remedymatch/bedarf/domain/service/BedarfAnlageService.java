package io.remedymatch.bedarf.domain.service;

import static io.remedymatch.artikel.domain.service.ArtikelEntityConverter.convertArtikel;
import static io.remedymatch.artikel.domain.service.ArtikelEntityConverter.convertVariante;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.model.ArtikelVarianteId;
import io.remedymatch.artikel.domain.service.ArtikelSucheService;
import io.remedymatch.artikel.infrastructure.ArtikelEntity;
import io.remedymatch.artikel.infrastructure.ArtikelVarianteEntity;
import io.remedymatch.bedarf.domain.model.Bedarf;
import io.remedymatch.bedarf.domain.model.NeuerBedarf;
import io.remedymatch.bedarf.infrastructure.BedarfEntity;
import io.remedymatch.bedarf.infrastructure.BedarfJpaRepository;
import io.remedymatch.domain.OperationNotAlloudException;
import io.remedymatch.geodaten.domain.GeocodingService;
import io.remedymatch.institution.domain.service.InstitutionEntityConverter;
import io.remedymatch.institution.domain.service.InstitutionStandortEntityConverter;
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

	private static final String EXCEPTION_MSG_ARTIKEL_UND_ARTIKEL_VARIANTE_PASSEN_NICHT_ZUSAMMEN = "Artikel und ArtikelVariante passen nicht zusammen.";
	private static final String EXCEPTION_MSG_ARTIKEL_UND_ARTIKEL_VARIANTE_LEER = "Artikel und ArtikelVariante sind beide leer.";

	private final BedarfJpaRepository bedarfRepository;

	private final UserContextService userService;
	private final ArtikelSucheService artikelSucheService;
	private final GeocodingService geocodingService;

	@Transactional
	public Bedarf neuenBedarfEinstellen(final @NotNull @Valid NeuerBedarf neuerBedarf) {

		if (neuerBedarf.getArtikelId() == null && neuerBedarf.getArtikelVarianteId() == null) {
			throw new OperationNotAlloudException(EXCEPTION_MSG_ARTIKEL_UND_ARTIKEL_VARIANTE_LEER);
		}

		ArtikelEntity artikelEntity = null;
		ArtikelVarianteEntity artikelVariante = null;
		if (neuerBedarf.getArtikelVarianteId() != null) {
			artikelVariante = getArtikelVariante(neuerBedarf.getArtikelVarianteId());
			if (neuerBedarf.getArtikelId() != null
					&& !artikelVariante.getArtikel().equals(neuerBedarf.getArtikelId().getValue())) {
				throw new OperationNotAlloudException(EXCEPTION_MSG_ARTIKEL_UND_ARTIKEL_VARIANTE_PASSEN_NICHT_ZUSAMMEN);
			}

			artikelEntity = getArtikel(new ArtikelId(artikelVariante.getArtikel()));
		} else {
			artikelEntity = getArtikel(neuerBedarf.getArtikelId());
		}

		return mitEntfernung(bedarfRepository.save(BedarfEntity.builder() //
				.anzahl(neuerBedarf.getAnzahl()) //
				.rest(neuerBedarf.getAnzahl()) //
				.artikel(artikelEntity) //
				.artikelVariante(artikelVariante) //
				.institution(getUserInstitution()) //
				.standort(getUserStandort()) //
				.steril(neuerBedarf.isSteril()) //
				.medizinisch(neuerBedarf.isMedizinisch()) //
				.oeffentlich(neuerBedarf.isOeffentlich()) //
				.kommentar(neuerBedarf.getKommentar()) //
				.bedient(false) //
				.build()));
	}

	private Bedarf mitEntfernung(final @NotNull BedarfEntity bedarf) {
		val convertedBedarf = BedarfEntityConverter.convertBedarf(bedarf);
		convertedBedarf.setEntfernung(geocodingService.berechneUserDistanzInKilometer(convertedBedarf.getStandort()));
		return convertedBedarf;
	}

	private ArtikelVarianteEntity getArtikelVariante(final @NotNull @Valid ArtikelVarianteId artikelVarianteId) {
		return convertVariante(artikelSucheService.getArtikelVarianteOrElseThrow(artikelVarianteId));
	}

	private ArtikelEntity getArtikel(final @NotNull @Valid ArtikelId artikelId) {
		return convertArtikel(artikelSucheService.getArtikelOrElseThrow(artikelId));
	}

	private InstitutionEntity getUserInstitution() {
		return InstitutionEntityConverter.convertInstitution(userService.getContextStandort().getInstitution());
	}

	private InstitutionStandortEntity getUserStandort() {
		return InstitutionStandortEntityConverter.convertStandort(userService.getContextStandort().getStandort());
	}
}
