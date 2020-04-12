package io.remedymatch.match.api;

import io.remedymatch.match.domain.MatchStandort;

class MatchStandortMapper {

	static MatchStandortRO mapToDTO(MatchStandort standort) {
		if (standort == null) {
			return null;
		}
		
		return MatchStandortRO.builder() //
				.id(standort.getId().getValue()) //
				.institutionStandortId(standort.getInstitutionStandortId()) //
				.name(standort.getName()) //
				.strasse(standort.getStrasse()) //
				.hausnummer(standort.getHausnummer()) //
				.plz(standort.getPlz()) //
				.ort(standort.getOrt()) //
				.land(standort.getLand()) //
				.longitude(standort.getLongitude()) //
				.latitude(standort.getLatitude()) //
				.build();
	}
}
