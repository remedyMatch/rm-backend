package io.remedymatch.angebot.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import io.remedymatch.angebot.domain.model.Angebot;
import io.remedymatch.angebot.domain.model.AngebotAnfrage;
import io.remedymatch.angebot.domain.model.AngebotId;
import io.remedymatch.angebot.domain.model.NeuesAngebot;
import io.remedymatch.artikel.domain.model.ArtikelVarianteId;
import io.remedymatch.institution.controller.InstitutionMapper;
import io.remedymatch.institution.controller.InstitutionStandortMapper;
import io.remedymatch.institution.domain.model.InstitutionStandortId;

class AngebotControllerMapper {
	private AngebotControllerMapper() {

	}

	static AngebotAnfrageRO mapToAnfrageRO(final AngebotAnfrage angebotAnfrage) {
		return AngebotAnfrageRO.builder() //
				.id(angebotAnfrage.getId().getValue()) //
				.angebot(mapToAngebotRO(angebotAnfrage.getAngebot())) //
				.institution(InstitutionMapper.mapToInstitutionRO(angebotAnfrage.getInstitution())) //
				.standort(InstitutionStandortMapper.mapToStandortRO(angebotAnfrage.getStandort())) //
				.anzahl(angebotAnfrage.getAnzahl()) //
				.kommentar(angebotAnfrage.getKommentar()) //
				.prozessInstanzId(angebotAnfrage.getProzessInstanzId()) //
				.status(angebotAnfrage.getStatus()) //
				.build();
	}

	static List<AngebotRO> mapToAngeboteRO(final List<Angebot> angebote) {
		return angebote.stream().map(AngebotControllerMapper::mapToAngebotRO).collect(Collectors.toList());
	}

	static AngebotRO mapToAngebotRO(final Angebot angebot) {
		return AngebotRO.builder() //
				.id(angebot.getId().getValue()) //
				.artikelVarianteId(angebot.getArtikelVariante().getId().getValue()) //
				.anzahl(angebot.getAnzahl()) //
				.rest(angebot.getRest()) //
				.institutionId(angebot.getInstitution().getId().getValue()) //
				.standort(InstitutionStandortMapper.mapToStandortRO(angebot.getStandort())) //
				.haltbarkeit(angebot.getHaltbarkeit()).medizinisch(angebot.isMedizinisch()).steril(angebot.isSteril()) //
				.originalverpackt(angebot.isOriginalverpackt()) //
				.medizinisch(angebot.isMedizinisch()) //
				.kommentar(angebot.getKommentar()) //
				.entfernung(angebot.getEntfernung()) //
				.build();
	}

	static NeuesAngebot mapToNeueAngebot(final NeuesAngebotRequest neueAngebotRequest) {
		return NeuesAngebot.builder()//
				.artikelVarianteId(new ArtikelVarianteId(neueAngebotRequest.getArtikelVarianteId())) //
				.anzahl(neueAngebotRequest.getAnzahl()) //
				.standortId(new InstitutionStandortId(neueAngebotRequest.getStandortId())) //
				.haltbarkeit(neueAngebotRequest.getHaltbarkeit()) //
				.steril(neueAngebotRequest.isSteril()) //
				.originalverpackt(neueAngebotRequest.isOriginalverpackt()) //
				.medizinisch(neueAngebotRequest.isMedizinisch()) //
				.kommentar(neueAngebotRequest.getKommentar()) //
				.build();
	}

	static AngebotId mapToAngebotId(final UUID angebotId) {
		return new AngebotId(angebotId);
	}
}