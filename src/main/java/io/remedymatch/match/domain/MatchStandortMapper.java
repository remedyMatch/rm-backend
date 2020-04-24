package io.remedymatch.match.domain;

import io.remedymatch.institution.domain.model.InstitutionStandort;

class MatchStandortMapper {
	static MatchStandort mapToMatchStandort(InstitutionStandort standort) {
		if (standort == null) {
			return null;
		}
		
		return MatchStandort.builder() //
				.institutionStandortId(standort.getId().getValue()) //
				.name(standort.getName()) //
				.strasse(standort.getStrasse()) //
				.hausnummer(standort.getHausnummer()) //
				.land(standort.getLand()) //
				.ort(standort.getOrt()) //
				.plz(standort.getPlz()) //
				.longitude(standort.getLongitude()) //
				.latitude(standort.getLatitude()) //
				.build();
	}
}
