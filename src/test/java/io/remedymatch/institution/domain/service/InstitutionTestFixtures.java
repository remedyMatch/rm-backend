package io.remedymatch.institution.domain.service;

import io.remedymatch.institution.domain.model.*;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public final class InstitutionTestFixtures {
    private InstitutionTestFixtures() {

    }

    public static final InstitutionId INSTITUTION_ID = new InstitutionId(UUID.randomUUID());
    public static final String INSTITUTION_NAME = "Institution Name";
    public static final String INSTITUTION_KEY = "institutionKey";
    public static final InstitutionTyp INSTITUTION_TYP = InstitutionTyp.KRANKENHAUS;

    public static InstitutionId beispielInstitutionId() {
        return INSTITUTION_ID;
    }

    public static Institution beispielInstitution() {
        return Institution.builder() //
                .id(beispielInstitutionId()) //
                .name(INSTITUTION_NAME) //
                .institutionKey(INSTITUTION_KEY) //
                .typ(INSTITUTION_TYP) //
                .standorte(new ArrayList<>(Arrays.asList(beispielHaupstandort(), beispielStandort1(), beispielStandort2()))) //
                .build();
    }

    public static InstitutionEntity beispielInstitutionEntity() {
        return InstitutionEntity.builder() //
                .id(beispielInstitutionId().getValue()) //
                .name(INSTITUTION_NAME) //
                .institutionKey(INSTITUTION_KEY) //
                .typ(INSTITUTION_TYP) //
                .standorte(new ArrayList<>(Arrays.asList(beispielHaupstandortEntity(), beispielStandort1Entity(), beispielStandort2Entity()))) //
                .build();
    }

    public static final InstitutionStandortId HAUPTSTANDORT_ID = new InstitutionStandortId(UUID.randomUUID());
    public static final String HAUPTSTANDORT_NAME = "Hauptstandort Name";
    public static final String HAUPTSTANDORT_PLZ = "Hauptstandort PLZ";
    public static final String HAUPTSTANDORT_ORT = "Hauptstandort Ort";
    public static final String HAUPTSTANDORT_STRASSE = "Hauptstandort Strasse";
    public static final String HAUPTSTANDORT_HAUSNUMMER = "Hauptstandort 10a";
    public static final String HAUPTSTANDORT_LAND = "Hauptstandort Land";
    public static final BigDecimal HAUPTSTANDORT_LONGITUDE = BigDecimal.valueOf(1234);
    public static final BigDecimal HAUPTSTANDORT_LATITUDE = BigDecimal.valueOf(5678);

    public static final InstitutionStandortId STANDORT_1_ID = new InstitutionStandortId(UUID.randomUUID());
    public static final String STANDORT_1_NAME = "Standort 1 Name";
    public static final String STANDORT_1_PLZ = "Standort 1 PLZ";
    public static final String STANDORT_1_ORT = "Standort 1 Ort";
    public static final String STANDORT_1_STRASSE = "Standort 1 Strasse";
    public static final String STANDORT_1_HAUSNUMMER = "Hauptstandort 1 11c";
    public static final String STANDORT_1_LAND = "Standort 1 Land";
    public static final BigDecimal STANDORT_1_LONGITUDE = BigDecimal.valueOf(1000);
    public static final BigDecimal STANDORT_1_LATITUDE = BigDecimal.valueOf(2000);

    public static final InstitutionStandortId STANDORT_2_ID = new InstitutionStandortId(UUID.randomUUID());
    public static final String STANDORT_2_NAME = "Standort 2 Name";
    public static final String STANDORT_2_PLZ = "Standort 2 PLZ";
    public static final String STANDORT_2_ORT = "Standort 2 Ort";
    public static final String STANDORT_2_STRASSE = "Standort 2 Strasse";
    public static final String STANDORT_2_HAUSNUMMER = "Standort 2 22b";
    public static final String STANDORT_2_LAND = "Standort 2 Land";
    public static final BigDecimal STANDORT_2_LONGITUDE = BigDecimal.valueOf(9999);
    public static final BigDecimal STANDORT_2_LATITUDE = BigDecimal.valueOf(8888);

    public static InstitutionStandortId beispielHaupstandortId() {
        return HAUPTSTANDORT_ID;
    }

    public static InstitutionStandort beispielHaupstandort() {
        return InstitutionStandort.builder() //
                .id(beispielHaupstandortId()) //
                .name(HAUPTSTANDORT_NAME) //
                .plz(HAUPTSTANDORT_PLZ) //
                .ort(HAUPTSTANDORT_ORT) //
                .strasse(HAUPTSTANDORT_STRASSE) //
                .hausnummer(HAUPTSTANDORT_HAUSNUMMER) //
                .land(HAUPTSTANDORT_LAND) //
                .longitude(HAUPTSTANDORT_LONGITUDE) //
                .latitude(HAUPTSTANDORT_LATITUDE) //
                .build();
    }

    public static InstitutionStandortEntity beispielHaupstandortEntity() {
        return InstitutionStandortEntity.builder() //
                .id(beispielHaupstandortId().getValue()) //
                .name(HAUPTSTANDORT_NAME) //
                .plz(HAUPTSTANDORT_PLZ) //
                .ort(HAUPTSTANDORT_ORT) //
                .strasse(HAUPTSTANDORT_STRASSE) //
                .hausnummer(HAUPTSTANDORT_HAUSNUMMER) //
                .land(HAUPTSTANDORT_LAND) //
                .longitude(HAUPTSTANDORT_LONGITUDE) //
                .latitude(HAUPTSTANDORT_LATITUDE) //
                .build();
    }

    public static InstitutionStandortId beispielStandort1Id() {
        return STANDORT_1_ID;
    }

    public static InstitutionStandort beispielStandort1() {
        return InstitutionStandort.builder() //
                .id(beispielStandort1Id()) //
                .name(STANDORT_1_NAME) //
                .plz(STANDORT_1_PLZ) //
                .ort(STANDORT_1_ORT) //
                .strasse(STANDORT_1_STRASSE) //
                .hausnummer(STANDORT_1_HAUSNUMMER) //
                .land(STANDORT_1_LAND) //
                .longitude(STANDORT_1_LONGITUDE) //
                .latitude(STANDORT_1_LATITUDE) //
                .build();
    }

    public static InstitutionStandortEntity beispielStandort1Entity() {
        return InstitutionStandortEntity.builder() //
                .id(beispielStandort1Id().getValue()) //
                .name(STANDORT_1_NAME) //
                .plz(STANDORT_1_PLZ) //
                .ort(STANDORT_1_ORT) //
                .strasse(STANDORT_1_STRASSE) //
                .hausnummer(STANDORT_1_HAUSNUMMER) //
                .land(STANDORT_1_LAND) //
                .longitude(STANDORT_1_LONGITUDE) //
                .latitude(STANDORT_1_LATITUDE) //
                .build();
    }

    public static InstitutionStandortId beispielStandort2Id() {
        return STANDORT_2_ID;
    }

    public static InstitutionStandort beispielStandort2() {
        return InstitutionStandort.builder() //
                .id(beispielStandort2Id()) //
                .name(STANDORT_2_NAME) //
                .plz(STANDORT_2_PLZ) //
                .ort(STANDORT_2_ORT) //
                .strasse(STANDORT_2_STRASSE) //
                .hausnummer(STANDORT_2_HAUSNUMMER) //
                .land(STANDORT_2_LAND) //
                .longitude(STANDORT_2_LONGITUDE) //
                .latitude(STANDORT_2_LATITUDE) //
                .build();
    }

    public static InstitutionStandortEntity beispielStandort2Entity() {
        return InstitutionStandortEntity.builder() //
                .id(beispielStandort2Id().getValue()) //
                .name(STANDORT_2_NAME) //
                .plz(STANDORT_2_PLZ) //
                .ort(STANDORT_2_ORT) //
                .strasse(STANDORT_2_STRASSE) //
                .hausnummer(STANDORT_2_HAUSNUMMER) //
                .land(STANDORT_2_LAND) //
                .longitude(STANDORT_2_LONGITUDE) //
                .latitude(STANDORT_2_LATITUDE) //
                .build();
    }

    public static final InstitutionId INSTITUTION_1_ID = new InstitutionId(UUID.randomUUID());
    public static final String INSTITUTION_1_NAME = "Institution 1 Name";
    public static final String INSTITUTION_1_KEY = "institution1Key";
    public static final InstitutionTyp INSTITUTION_1_TYP = InstitutionTyp.ARZT;

    public static final InstitutionStandortId INSTITUTION_1_HAUPTSTANDORT_ID = new InstitutionStandortId(UUID.randomUUID());
    public static final String INSTITUTION_1_HAUPTSTANDORT_NAME = "Institution 1 Hauptstandort Name";
    public static final String INSTITUTION_1_HAUPTSTANDORT_PLZ = "Institution 1 Hauptstandort PLZ";
    public static final String INSTITUTION_1_HAUPTSTANDORT_ORT = "Institution 1 Hauptstandort Ort";
    public static final String INSTITUTION_1_HAUPTSTANDORT_STRASSE = "Institution 1 Hauptstandort Strasse";
    public static final String INSTITUTION_1_HAUPTSTANDORT_HAUSNUMMER = "Institution 1 Hauptstandort 69d";
    public static final String INSTITUTION_1_HAUPTSTANDORT_LAND = "Institution 1 Hauptstandort Land";
    public static final BigDecimal INSTITUTION_1_HAUPTSTANDORT_LONGITUDE = BigDecimal.valueOf(126456);
    public static final BigDecimal INSTITUTION_1_HAUPTSTANDORT_LATITUDE = BigDecimal.valueOf(484165);


    public static InstitutionId beispielInstitution1Id() {
        return INSTITUTION_1_ID;
    }

    public static Institution beispielInstitution1() {
        return Institution.builder() //
                .id(beispielInstitution1Id()) //
                .name(INSTITUTION_1_NAME) //
                .institutionKey(INSTITUTION_1_KEY) //
                .typ(INSTITUTION_1_TYP) //
                .standorte(Arrays.asList(beispielInstitution1Hauptstandort())) //
                .build();
    }

    public static InstitutionEntity beispielInstitution1Entity() {
        return InstitutionEntity.builder() //
                .id(beispielInstitution1Id().getValue()) //
                .name(INSTITUTION_1_NAME) //
                .institutionKey(INSTITUTION_1_KEY) //
                .typ(INSTITUTION_1_TYP) //
                .standorte(Arrays.asList(beispielInstitution1HauptstandortEntity())) //
                .build();
    }

    public static InstitutionStandortId beispielInstitution1HauptstandortId() {
        return INSTITUTION_1_HAUPTSTANDORT_ID;
    }

    public static InstitutionStandort beispielInstitution1Hauptstandort() {
        return InstitutionStandort.builder() //
                .id(beispielInstitution1HauptstandortId()) //
                .name(INSTITUTION_1_HAUPTSTANDORT_NAME) //
                .plz(INSTITUTION_1_HAUPTSTANDORT_PLZ) //
                .ort(INSTITUTION_1_HAUPTSTANDORT_ORT) //
                .strasse(INSTITUTION_1_HAUPTSTANDORT_STRASSE) //
                .hausnummer(INSTITUTION_1_HAUPTSTANDORT_HAUSNUMMER) //
                .land(INSTITUTION_1_HAUPTSTANDORT_LAND) //
                .longitude(INSTITUTION_1_HAUPTSTANDORT_LONGITUDE) //
                .latitude(INSTITUTION_1_HAUPTSTANDORT_LATITUDE) //
                .build();
    }

    public static InstitutionStandortEntity beispielInstitution1HauptstandortEntity() {
        return InstitutionStandortEntity.builder() //
                .id(beispielInstitution1HauptstandortId().getValue()) //
                .name(INSTITUTION_1_HAUPTSTANDORT_NAME) //
                .plz(INSTITUTION_1_HAUPTSTANDORT_PLZ) //
                .ort(INSTITUTION_1_HAUPTSTANDORT_ORT) //
                .strasse(INSTITUTION_1_HAUPTSTANDORT_STRASSE) //
                .hausnummer(INSTITUTION_1_HAUPTSTANDORT_HAUSNUMMER) //
                .land(INSTITUTION_1_HAUPTSTANDORT_LAND) //
                .longitude(INSTITUTION_1_HAUPTSTANDORT_LONGITUDE) //
                .latitude(INSTITUTION_1_HAUPTSTANDORT_LATITUDE) //
                .build();
    }
}
