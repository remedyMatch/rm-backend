package io.remedymatch.institution.controller;

import io.remedymatch.institution.domain.model.Institution;
import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.institution.domain.model.InstitutionUpdate;

import java.util.UUID;

public final class InstitutionMapper {

    private InstitutionMapper() {

    }

    public static InstitutionRO mapToInstitutionRO(Institution institution) {
        return InstitutionRO.builder() //
                .id(institution.getId().getValue()) //
                .name(institution.getName()) //
                .institutionKey(institution.getInstitutionKey()) //
                .typ(institution.getTyp()) //
                .standorte(InstitutionStandortMapper.mapToStandorteRO(institution.getStandorte())) //
                .build();
    }

    static InstitutionUpdate mapToUpdate(final InstitutionUpdateRequest institutionUpdateRequest) {
        return InstitutionUpdate.builder()//
                .neueName(institutionUpdateRequest.getName()) //
                .build();
    }

    static InstitutionId mapToInstitutionId(final UUID institutionId) {
        return new InstitutionId(institutionId);
    }
}