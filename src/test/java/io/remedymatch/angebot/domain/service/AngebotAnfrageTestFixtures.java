package io.remedymatch.angebot.domain.service;

import io.remedymatch.angebot.domain.model.AngebotAnfrage;
import io.remedymatch.angebot.domain.model.AngebotAnfrageId;
import io.remedymatch.angebot.domain.model.AngebotAnfrageStatus;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageEntity;
import io.remedymatch.bedarf.domain.model.BedarfId;
import io.remedymatch.institution.domain.service.InstitutionTestFixtures;

import java.math.BigDecimal;
import java.util.UUID;

public final class AngebotAnfrageTestFixtures {
    private AngebotAnfrageTestFixtures() {

    }

    public static final BedarfId BEDARF_ID = new BedarfId(UUID.randomUUID());
    public static final AngebotAnfrageId ANGEBOT_ANFRAGE_ID = new AngebotAnfrageId(UUID.randomUUID());
    public static final BigDecimal ANGEBOT_ANFRAGE_ANZAHL = BigDecimal.valueOf(120.0);
    public static final String ANGEBOT_ANFRAGE_KOMMENTAR = "Angebot Anfrage Kommentar";
    public static final AngebotAnfrageStatus ANGEBOT_ANFRAGE_STATUS = AngebotAnfrageStatus.OFFEN;

    public static AngebotAnfrageId beispielAngebotAnfrageId() {
        return ANGEBOT_ANFRAGE_ID;
    }

    public static AngebotAnfrage beispielAngebotAnfrage() {
        return AngebotAnfrage.builder() //
                .id(beispielAngebotAnfrageId()) //
                .angebot(AngebotTestFixtures.beispielAngebot()) //
                .institution(InstitutionTestFixtures.beispielInstitution()) //
                .standort(InstitutionTestFixtures.beispielHaupstandort()) //
                .bedarfId(BEDARF_ID) //
                .anzahl(ANGEBOT_ANFRAGE_ANZAHL) //
                .status(ANGEBOT_ANFRAGE_STATUS) //
                .build();
    }

    static AngebotAnfrageEntity beispielAngebotAnfrageEntity() {
        return AngebotAnfrageEntity.builder() //
                .id(beispielAngebotAnfrageId().getValue()) //
                .angebot(AngebotTestFixtures.beispielAngebotEntityMitAngebotSteller()) //
                .institution(InstitutionTestFixtures.beispielInstitutionEntity()) //
                .standort(InstitutionTestFixtures.beispielHaupstandortEntity()) //
                .bedarfId(BEDARF_ID.getValue()) //
                .anzahl(ANGEBOT_ANFRAGE_ANZAHL) //
                .status(ANGEBOT_ANFRAGE_STATUS) //
                .build();
    }
}
