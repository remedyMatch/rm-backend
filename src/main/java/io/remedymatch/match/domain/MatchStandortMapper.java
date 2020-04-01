package io.remedymatch.match.domain;

import io.remedymatch.institution.domain.InstitutionStandort;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;

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
    
    public static MatchStandortEntity mapToMatchStandort(InstitutionStandort standort) {
        return MatchStandortEntity.builder()
                .land(standort.getLand())
                .ort(standort.getOrt())
                .plz(standort.getPlz())
                .strasse(standort.getStrasse())
                .longitude(standort.getLongitude())
                .latitude(standort.getLatitude())
                .build();
    }

}
