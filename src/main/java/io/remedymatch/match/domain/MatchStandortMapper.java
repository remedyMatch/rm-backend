package io.remedymatch.match.domain;

import io.remedymatch.institution.domain.InstitutionStandortEntity;

public class MatchStandortMapper {


    public static MatchStandortEntity mapToMatchStandort(InstitutionStandortEntity standortEntity) {
        return MatchStandortEntity.builder()
                .land(standortEntity.getLand())
                .ort(standortEntity.getOrt())
                .plz(standortEntity.getPlz())
                .strasse(standortEntity.getStrasse())
                .longitude(standortEntity.getLongitude())
                .latitude(standortEntity.getLatitude())
                .build();
    }

}
