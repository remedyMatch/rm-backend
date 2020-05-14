package io.remedymatch.bedarf.domain.service;

import io.remedymatch.angebot.domain.model.AngebotId;
import io.remedymatch.bedarf.domain.model.BedarfAnfrage;
import io.remedymatch.bedarf.domain.model.BedarfAnfrageId;
import io.remedymatch.bedarf.infrastructure.BedarfAnfrageEntity;
import io.remedymatch.institution.domain.service.InstitutionEntityConverter;
import io.remedymatch.institution.domain.service.InstitutionStandortEntityConverter;

import java.util.List;
import java.util.stream.Collectors;

final class BedarfAnfrageEntityConverter {

    private BedarfAnfrageEntityConverter() {

    }

    static List<BedarfAnfrage> convertAnfragen(final List<BedarfAnfrageEntity> entities) {
        return entities.stream().map(BedarfAnfrageEntityConverter::convertAnfrage).collect(Collectors.toList());
    }

    static BedarfAnfrage convertAnfrage(final BedarfAnfrageEntity entity) {
        return BedarfAnfrage.builder() //
                .id(new BedarfAnfrageId(entity.getId())) //
                .bedarf(BedarfEntityConverter.convertBedarf(entity.getBedarf())) //
                .institution(InstitutionEntityConverter.convertInstitution(entity.getInstitution())) //
                .standort(InstitutionStandortEntityConverter.convertStandort(entity.getStandort())) //
                .anzahl(entity.getAnzahl()) //
                .angebotId(new AngebotId(entity.getAngebotId())) //
                .status(entity.getStatus()) //
                .build();
    }
}