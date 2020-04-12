package io.remedymatch.institution.api;

import java.util.UUID;

import io.remedymatch.institution.domain.InstitutionStandort;
import io.remedymatch.institution.domain.InstitutionStandortId;

public class InstitutionStandortMapper {

	public static InstitutionStandortRO mapToStandortRO(InstitutionStandort standort) {
		return InstitutionStandortRO.builder()//
				.id(standort.getId().getValue()) //
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

	public static InstitutionStandort mapToStandort(InstitutionStandortRO dto) {
		return InstitutionStandort.builder()//
				.id(mapToStandortId(dto.getId())) //
				.name(dto.getName()) //
				.strasse(dto.getStrasse()) //
				.hausnummer(dto.getHausnummer()) //
				.plz(dto.getPlz()) //
				.ort(dto.getOrt()) //
				.land(dto.getLand()) //
				.longitude(dto.getLongitude()) //
				.latitude(dto.getLatitude()) //
				.build();
	}

	static InstitutionStandortId mapToStandortId(final String standortId) {
		return mapToStandortId(UUID.fromString(standortId));
	}

	static InstitutionStandortId mapToStandortId(final UUID standortId) {
		return new InstitutionStandortId(standortId);
	}
}
