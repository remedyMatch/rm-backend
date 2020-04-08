package io.remedymatch.angebot.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import io.remedymatch.angebot.domain.model.Angebot;
import io.remedymatch.angebot.domain.model.AngebotId;
import io.remedymatch.angebot.domain.service.NeuesAngebot;
import io.remedymatch.artikel.domain.model.ArtikelVarianteId;
import io.remedymatch.institution.api.InstitutionStandortMapper;
import io.remedymatch.institution.domain.InstitutionStandortId;

class AngebotControllerMapper {

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
				.standort(InstitutionStandortMapper.mapToDTO(angebot.getStandort())) //
				.haltbarkeit(angebot.getHaltbarkeit()).medizinisch(angebot.isMedizinisch()).steril(angebot.isSteril()) //
				.originalverpackt(angebot.isOriginalverpackt()) //
				.medizinisch(angebot.isMedizinisch()) //
				.kommentar(angebot.getKommentar()) //
				.bedient(angebot.isBedient()) //
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

	static AngebotId maptToAngebotId(final String angebotId) {
		return maptToAngebotId(UUID.fromString(angebotId));
	}

	static AngebotId maptToAngebotId(final UUID angebotId) {
		return new AngebotId(angebotId);
	}
}