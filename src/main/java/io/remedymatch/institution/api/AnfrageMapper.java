package io.remedymatch.institution.api;

import io.remedymatch.institution.domain.Anfrage;

class AnfrageMapper {
	static AnfrageDTO mapToDTO(Anfrage anfrage) {
		return AnfrageDTO.builder() //
				.id(anfrage.getId()) //
				.institutionAn(InstitutionMapper.mapToDTO(anfrage.getInstitutionAn())) //
				.standortAn(InstitutionStandortMapper.mapToDTO(anfrage.getStandortAn())) //
				.institutionVon(InstitutionMapper.mapToDTO(anfrage.getInstitutionVon())) //
				.standortVon(InstitutionStandortMapper.mapToDTO(anfrage.getStandortVon())) //
				.angebotId(anfrage.getAngebotId()) //
				.bedarfId(anfrage.getBedarfId()) //
				.anzahl(anfrage.getAnzahl()) //
				.kommentar(anfrage.getKommentar()) //
				.prozessInstanzId(anfrage.getProzessInstanzId()) //
				.status(anfrage.getStatus()) //
				.entfernung(anfrage.getEntfernung()) //
				.build();
	}
}
