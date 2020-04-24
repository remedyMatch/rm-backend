package io.remedymatch.match.domain;

import io.remedymatch.match.infrastructure.MatchStandortEntity;
import io.remedymatch.match.infrastructure.MatchStandortEntity.MatchStandortEntityBuilder;

class MatchStandortEntityConverter {

	static MatchStandort convert(final MatchStandortEntity entity) {
		if (entity == null) {
			return null;
		}

		return MatchStandort.builder()//
				.id(new MatchStandortId(entity.getId())) //
				.institutionStandortId(entity.getInstitutionStandortId()) //
				.name(entity.getName()) //
				.strasse(entity.getStrasse())//
				.hausnummer(entity.getHausnummer())//
				.plz(entity.getPlz()) //
				.ort(entity.getOrt()) //
				.land(entity.getLand()) //
				.longitude(entity.getLongitude()) //
				.latitude(entity.getLatitude()) //
				.build();
	}

	static MatchStandortEntity convert(final MatchStandort matchStandort) {
		if (matchStandort == null) {
			return null;
		}

		MatchStandortEntityBuilder builder = MatchStandortEntity.builder();
		if (matchStandort.getId() != null) {
			builder.id(matchStandort.getId().getValue());
		}

		return builder
				.institutionStandortId(matchStandort.getInstitutionStandortId()) //
				.name(matchStandort.getName()) //
				.strasse(matchStandort.getStrasse())//
				.hausnummer(matchStandort.getHausnummer()) //
				.plz(matchStandort.getPlz()) //
				.ort(matchStandort.getOrt()) //
				.land(matchStandort.getLand()) //
				.longitude(matchStandort.getLongitude()) //
				.latitude(matchStandort.getLatitude()) // ;
				.build();
	}
}