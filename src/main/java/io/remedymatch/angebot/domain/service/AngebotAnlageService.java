package io.remedymatch.angebot.domain.service;

import static io.remedymatch.artikel.domain.service.ArtikelEntityConverter.convertArtikel;
import static io.remedymatch.artikel.domain.service.ArtikelEntityConverter.convertVariante;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.remedymatch.angebot.domain.model.Angebot;
import io.remedymatch.angebot.domain.model.NeuesAngebot;
import io.remedymatch.angebot.infrastructure.AngebotEntity;
import io.remedymatch.angebot.infrastructure.AngebotJpaRepository;
import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.model.ArtikelVarianteId;
import io.remedymatch.artikel.domain.service.ArtikelSucheService;
import io.remedymatch.artikel.infrastructure.ArtikelEntity;
import io.remedymatch.artikel.infrastructure.ArtikelVarianteEntity;
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
public class AngebotAnlageService {

	private final AngebotJpaRepository angebotRepository;
	private final UserContextService userService;
	private final ArtikelSucheService artikelSucheService;
	private final GeocodingService geocodingService;
	private final AngebotProzessService angebotProzessService;

	@Transactional
	public Angebot neueAngebotEinstellen(final @NotNull @Valid NeuesAngebot neuesAngebot) {

		val artikelVariante = getArtikelVariante(neuesAngebot.getArtikelVarianteId());
		val artikel = getArtikel(new ArtikelId(artikelVariante.getArtikel()));

		val angebot = mitEntfernung(angebotRepository.save(AngebotEntity.builder() //
				.anzahl(neuesAngebot.getAnzahl()) //
				.rest(neuesAngebot.getAnzahl()) //
				.artikel(artikel) //
				.artikelVariante(artikelVariante) //
				.institution(getUserInstitution()) //
				.standort(getUserStandort()) //
				.haltbarkeit(neuesAngebot.getHaltbarkeit()) //
				.steril(neuesAngebot.isSteril()) //
				.originalverpackt(neuesAngebot.isOriginalverpackt()) //
				.oeffentlich(neuesAngebot.isOeffentlich()) //
				.medizinisch(neuesAngebot.isMedizinisch()) //
				.kommentar(neuesAngebot.getKommentar()) //
				.bedient(false) //
				.build()));
		
		angebotProzessService.prozessStarten(angebot.getId(), userService.getContextUserId(),
				angebot.getInstitution().getId());

		return angebot;
	}

	private Angebot mitEntfernung(final @NotNull AngebotEntity angebot) {
		val convertedAngebot = AngebotEntityConverter.convertAngebot(angebot);
		convertedAngebot.setEntfernung(geocodingService.berechneUserDistanzInKilometer(convertedAngebot.getStandort()));
		return convertedAngebot;
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
