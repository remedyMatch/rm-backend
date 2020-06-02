package io.remedymatch.bedarf.domain.service;

import io.remedymatch.artikel.domain.service.ArtikelEntityConverter;
import io.remedymatch.bedarf.domain.model.Bedarf;
import io.remedymatch.bedarf.domain.model.BedarfId;
import io.remedymatch.bedarf.infrastructure.BedarfEntity;
import io.remedymatch.institution.domain.service.InstitutionEntityConverter;
import io.remedymatch.institution.domain.service.InstitutionStandortEntityConverter;

import java.util.List;
import java.util.stream.Collectors;

final class BedarfEntityConverter {

    private BedarfEntityConverter() {

    }

    static List<Bedarf> convertBedarfe(final List<BedarfEntity> entities) {
        return entities.stream().map(BedarfEntityConverter::convertBedarf).collect(Collectors.toList());
    }

    static Bedarf convertBedarf(final BedarfEntity entity) {
        if (entity == null) {
            return null;
        }

        return Bedarf.builder() //
                .id(new BedarfId(entity.getId())) //
                .anzahl(entity.getAnzahl()) //
                .rest(entity.getRest()) //
                .artikel(ArtikelEntityConverter.convertArtikel(entity.getArtikel())) //
                .artikelVariante(entity.getArtikelVariante() != null
                        ? ArtikelEntityConverter.convertVariante(entity.getArtikelVariante())
                        : null) //
                .institution(InstitutionEntityConverter.convertInstitution(entity.getInstitution()))//
                .standort(InstitutionStandortEntityConverter.convertStandort(entity.getStandort())).steril(entity.isSteril()) //
                .medizinisch(entity.isMedizinisch()) //
                .kommentar(entity.getKommentar()) //
                .oeffentlich(entity.isOeffentlich()) //
                .bedient(entity.isBedient()) //
                .build();
    }
}