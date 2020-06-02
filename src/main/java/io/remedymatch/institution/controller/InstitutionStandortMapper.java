package io.remedymatch.institution.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import io.remedymatch.institution.domain.model.InstitutionStandort;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
import io.remedymatch.institution.domain.model.NeuerInstitutionStandort;

public final class InstitutionStandortMapper {

	private InstitutionStandortMapper() {

	}

	public static List<InstitutionStandortRO> mapToStandorteRO(final List<InstitutionStandort> standorte) {
		return standorte.stream().map(InstitutionStandortMapper::mapToStandortRO).collect(Collectors.toList());
	}

	public static InstitutionStandortRO mapToStandortRO(InstitutionStandort standort) {
		if (standort == null) {
			return null;
		}
		
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

	static NeuerInstitutionStandort mapToNeuesStandort(final NeuerInstitutionStandortRequest neuesStandortRequest) {
		return NeuerInstitutionStandort.builder()//
				.name(neuesStandortRequest.getName()) //
				.strasse(neuesStandortRequest.getStrasse()) //
				.hausnummer(neuesStandortRequest.getHausnummer()) //
				.plz(neuesStandortRequest.getPlz()) //
				.ort(neuesStandortRequest.getOrt()) //
				.land(neuesStandortRequest.getLand()) //
				.build();
	}

	static InstitutionStandortId mapToStandortId(final UUID standortId) {
		return new InstitutionStandortId(standortId);
	}
}
