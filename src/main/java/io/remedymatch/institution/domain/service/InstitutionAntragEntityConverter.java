package io.remedymatch.institution.domain.service;

import io.remedymatch.institution.domain.model.InstitutionAntrag;
import io.remedymatch.institution.domain.model.InstitutionAntragId;
import io.remedymatch.institution.infrastructure.InstitutionAntragEntity;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;

public final class InstitutionAntragEntityConverter {

    private InstitutionAntragEntityConverter() {

    }

    static List<InstitutionAntrag> convertAntragEntities(final List<InstitutionAntragEntity> entities) {
        return entities.stream().map(InstitutionAntragEntityConverter::convertAntrag).collect(Collectors.toList());
    }

    public static InstitutionAntrag convertAntrag(final InstitutionAntragEntity entity) {
        if (entity == null) {
            return null;
        }

        return InstitutionAntrag.builder() //
                .id(new InstitutionAntragId(entity.getId())) //
                .name(entity.getName()) //
                .plz(entity.getPlz()) //
                .ort(entity.getOrt()) //
                .strasse(entity.getStrasse())//
                .hausnummer(entity.getHausnummer())//
                .land(entity.getLand()) //
                .rolle(entity.getRolle()) //
                .antragsteller(entity.getAntragsteller()) //
                .status(entity.getStatus()) //
                .institutionTyp(entity.getInstitutionTyp()) //
                .webseite(entity.getWebseite()) //
                .build();
    }

    static List<InstitutionAntragEntity> convertAntraege(final List<InstitutionAntrag> antraege) {
        return antraege.stream().map(InstitutionAntragEntityConverter::convertAntrag).collect(Collectors.toList());
    }

    public static InstitutionAntragEntity convertAntrag(final InstitutionAntrag institutionAntrag) {
        if (institutionAntrag == null) {
            return null;
        }

        val builder = InstitutionAntragEntity.builder();

        if (institutionAntrag.getId() != null) {
            builder.id(institutionAntrag.getId().getValue());
        }

        return builder.name(institutionAntrag.getName()) //
                .plz(institutionAntrag.getPlz()) //
                .ort(institutionAntrag.getOrt()) //
                .strasse(institutionAntrag.getStrasse())//
                .hausnummer(institutionAntrag.getHausnummer())//
                .land(institutionAntrag.getLand()) //
                .rolle(institutionAntrag.getRolle()) //
                .status(institutionAntrag.getStatus()) //
                .antragsteller(institutionAntrag.getAntragsteller()) //
                .institutionTyp(institutionAntrag.getInstitutionTyp()) //
                .webseite(institutionAntrag.getWebseite()) //
                .build();
    }
}