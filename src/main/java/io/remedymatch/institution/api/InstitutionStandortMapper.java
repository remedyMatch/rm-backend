package io.remedymatch.institution.api;

import java.util.UUID;

import io.remedymatch.institution.domain.InstitutionStandort;
import io.remedymatch.institution.domain.InstitutionStandortId;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;

public class InstitutionStandortMapper {

	public static InstitutionStandortDTO mapToDTO(InstitutionStandortEntity entity) {
		return InstitutionStandortDTO.builder() //
				.id(entity.getId()) //
				.land(entity.getLand()) //
				.ort(entity.getOrt()) //
				.name(entity.getName()) //
				.plz(entity.getPlz()) //
				.strasse(entity.getStrasse()) //
				.longitude(entity.getLongitude()) //
				.latitude(entity.getLatitude()) //
				.build();
	}

	public static InstitutionStandortDTO mapToDTO(InstitutionStandort entity) {
		return InstitutionStandortDTO.builder() //
				.id(entity.getId().getValue()) //
				.land(entity.getLand()) //
				.ort(entity.getOrt()) //
				.name(entity.getName()) //
				.plz(entity.getPlz()) //
				.strasse(entity.getStrasse()) //
				.longitude(entity.getLongitude()) //
				.latitude(entity.getLatitude()) //
				.build();
	}

	public static InstitutionStandortEntity mapToEntity(InstitutionStandortDTO dto) {
		return InstitutionStandortEntity.builder()//
				.id(dto.getId()) //
				.land(dto.getLand()) //
				.ort(dto.getOrt()) //
				.plz(dto.getPlz()) //
				.name(dto.getName()) //
				.strasse(dto.getStrasse()) //
				.longitude(dto.getLongitude()) //
				.latitude(dto.getLatitude()) //
				.build();
	}

	public static InstitutionStandort mapToStandort(InstitutionStandortDTO dto) {
		return InstitutionStandort.builder()//
				.id(mapToStandortId(dto.getId())) //
				.land(dto.getLand()) //
				.ort(dto.getOrt()) //
				.plz(dto.getPlz()) //
				.name(dto.getName()) //
				.strasse(dto.getStrasse()) //
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
