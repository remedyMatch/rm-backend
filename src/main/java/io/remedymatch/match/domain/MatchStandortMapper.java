package io.remedymatch.match.domain;

import io.remedymatch.institution.domain.InstitutionStandort;

class MatchStandortMapper {
	static MatchStandort mapToMatchStandort(InstitutionStandort standort) {
		return MatchStandort.builder() //
				.land(standort.getLand()) //
				.ort(standort.getOrt()) //
				.plz(standort.getPlz()) //
				.strasse(standort.getStrasse()) //
				.longitude(standort.getLongitude()) //
				.latitude(standort.getLatitude()) //
				.build();
	}
}
