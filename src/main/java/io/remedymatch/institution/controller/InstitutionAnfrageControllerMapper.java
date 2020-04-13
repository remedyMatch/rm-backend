package io.remedymatch.institution.controller;

import java.util.List;
import java.util.stream.Collectors;

import io.remedymatch.institution.domain.model.InstitutionAnfrage;

final class InstitutionAnfrageControllerMapper {
	private InstitutionAnfrageControllerMapper() {
	}

	static List<InstitutionAnfrageRO> mapToAnfragenRO(final List<InstitutionAnfrage> anfragen) {
		return anfragen.stream().map(InstitutionAnfrageControllerMapper::mapToAnfrageRO).collect(Collectors.toList());
	}

	static InstitutionAnfrageRO mapToAnfrageRO(final InstitutionAnfrage anfrage) {
		return InstitutionAnfrageRO.builder() //
				.id(anfrage.getId()) //
				.angebotId(anfrage.getAngebotId()) //
				.bedarfId(anfrage.getBedarfId()) //
				.institutionAn(InstitutionMapper.mapToInstitutionRO(anfrage.getInstitutionAn())) //
				.standortAn(InstitutionStandortMapper.mapToStandortRO(anfrage.getStandortAn())) //
				.institutionVon(InstitutionMapper.mapToInstitutionRO(anfrage.getInstitutionVon())) //
				.standortVon(InstitutionStandortMapper.mapToStandortRO(anfrage.getStandortVon())) //
				.artikelId(anfrage.getArtikelId().getValue()) //
				.artikelVarianteId(anfrage.getArtikelVarianteId().getValue()) //
				.anzahl(anfrage.getAnzahl()) //
				.kommentar(anfrage.getKommentar()) //
				.prozessInstanzId(anfrage.getProzessInstanzId()) //
				.status(anfrage.getStatus()) //
				.entfernung(anfrage.getEntfernung()) //
				.build();
	}
}
