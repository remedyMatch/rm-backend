package io.remedymatch.angebot.domain.service;

import io.remedymatch.angebot.domain.model.Angebot;
import io.remedymatch.angebot.domain.model.AngebotId;
import io.remedymatch.angebot.infrastructure.AngebotEntity;
import io.remedymatch.artikel.domain.service.ArtikelEntityConverter;
import io.remedymatch.institution.domain.service.InstitutionEntityConverter;
import io.remedymatch.institution.domain.service.InstitutionStandortEntityConverter;

import java.util.List;
import java.util.stream.Collectors;

final class AngebotEntityConverter {

    private AngebotEntityConverter() {

    }

    static List<Angebot> convertAngebote(final List<AngebotEntity> entities) {
        return entities.stream().map(AngebotEntityConverter::convertAngebot).collect(Collectors.toList());
    }

    static Angebot convertAngebot(final AngebotEntity entity) {
        if (entity == null) {
            return null;
        }

        return Angebot.builder() //
                .id(new AngebotId(entity.getId())) //
                .anzahl(entity.getAnzahl()) //
                .rest(entity.getRest()) //
                .oeffentlich(entity.isOeffentlich()) //
                .artikelVariante(ArtikelEntityConverter.convertVariante(entity.getArtikelVariante())) //
                .artikel(ArtikelEntityConverter.convertArtikel(entity.getArtikel())) //
                .institution(InstitutionEntityConverter.convertInstitution(entity.getInstitution()))//
                .standort(InstitutionStandortEntityConverter.convertStandort(entity.getStandort())) //
                .haltbarkeit(entity.getHaltbarkeit()) //
                .steril(entity.isSteril()) //
                .originalverpackt(entity.isOriginalverpackt()) //
                .medizinisch(entity.isMedizinisch()) //
                .kommentar(entity.getKommentar()) //
                .bedient(entity.isBedient()) //
                .build();
    }
}