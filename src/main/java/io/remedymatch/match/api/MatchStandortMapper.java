package io.remedymatch.match.api;

import io.remedymatch.match.domain.MatchStandort;
import io.remedymatch.match.domain.MatchStandortId;

class MatchStandortMapper {

	static MatchStandortDTO mapToDTO(MatchStandort standort) {
		return MatchStandortDTO.builder() //
				.id(standort.getId().getValue()) //
				.land(standort.getLand()) //
				.ort(standort.getOrt()) //
				.plz(standort.getPlz()) //
				.strasse(standort.getStrasse()) //
				.longitude(standort.getLongitude()) //
				.latitude(standort.getLatitude()) //
				.build();
	}

	static MatchStandort mapToStandort(MatchStandortDTO dto) {
		return MatchStandort.builder() //
				.id(new MatchStandortId(dto.getId())) //
				.land(dto.getLand()) //
				.ort(dto.getOrt()) //
				.plz(dto.getPlz()) //
				.strasse(dto.getStrasse()) //
				.longitude(dto.getLongitude()) //
				.latitude(dto.getLatitude()) //
				.build();
	}

}
