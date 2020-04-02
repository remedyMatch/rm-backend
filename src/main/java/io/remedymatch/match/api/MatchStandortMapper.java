package io.remedymatch.match.api;

import io.remedymatch.match.domain.MatchStandort;

class MatchStandortMapper {

	static MatchStandortDTO mapToDTO(MatchStandort standort) {
		if (standort == null) {
			return null;
		}
		
		return MatchStandortDTO.builder() //
				.id(standort.getId().getValue()) //
				.institutionStandortId(standort.getInstitutionStandortId()) //
				.name(standort.getName()) //
				.land(standort.getLand()) //
				.ort(standort.getOrt()) //
				.plz(standort.getPlz()) //
				.strasse(standort.getStrasse()) //
				.longitude(standort.getLongitude()) //
				.latitude(standort.getLatitude()) //
				.build();
	}
}
