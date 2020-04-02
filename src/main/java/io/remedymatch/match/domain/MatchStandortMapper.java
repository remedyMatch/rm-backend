package io.remedymatch.match.domain;

import io.remedymatch.institution.domain.InstitutionStandort;

class MatchStandortMapper {
	static MatchStandort mapToMatchStandort(InstitutionStandort standort) {
		if (standort == null) {
			return null;
		}
		
		return MatchStandort.builder() //
				.institutionStandortId(standort.getId().getValue()) //
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
