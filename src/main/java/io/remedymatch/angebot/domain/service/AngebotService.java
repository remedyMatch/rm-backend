package io.remedymatch.angebot.domain.service;

import io.remedymatch.angebot.domain.model.AngebotAnfrage;
import io.remedymatch.angebot.domain.model.AngebotAnfrageId;
import io.remedymatch.angebot.domain.model.AngebotAnfrageStatus;
import io.remedymatch.angebot.domain.model.AngebotId;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageEntity;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageJpaRepository;
import io.remedymatch.angebot.infrastructure.AngebotEntity;
import io.remedymatch.angebot.infrastructure.AngebotJpaRepository;
import io.remedymatch.domain.NotUserInstitutionObjectException;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.domain.OperationNotAlloudException;
import io.remedymatch.engine.domain.ProzessInstanzId;
import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
import io.remedymatch.institution.domain.service.InstitutionEntityConverter;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import io.remedymatch.person.domain.model.PersonId;
import io.remedymatch.usercontext.UserContextService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@Validated
@Service
@Transactional
public class AngebotService {

    private static final String EXCEPTION_MSG_ANGEBOT_NICHT_GEFUNDEN = "Angebot mit diesem Id nicht gefunden. (Id: %s)";
    private static final String EXCEPTION_MSG_ANGEBOT_BEDIEN = "Angebot bereits bedient.";
    private static final String EXCEPTION_MSG_ANGEBOT_NICHT_VON_USER_INSTITUTION = "Angebot gehoert nicht der Institution des angemeldetes Benutzers.";

    private static final String EXCEPTION_MSG_ANGEBOT_ANFRAGE_NICHT_GEFUNDEN = "AngebotAnfrage mit diesem Id nicht gefunden. (Id: %s)";
    private static final String EXCEPTION_MSG_ANGEBOT_ANFRAGE_NICHT_IN_ANGEBOT = "AngebotAnfrage gehört nicht zu dieser Angebot. (AngebotId: %s, AnfrageId: $s)";
    private static final String EXCEPTION_MSG_ANGEBOT_ANFRAGE_GESCHLOSSEN = "AngebotAnfrage ist bereits erledigt. (Status: %s)";

    private static final String EXCEPTION_MSG_STANDORT_NICHT_VON_USER_INSTITUTION = "Standort gehoert nicht der Institution des angemeldetes Benutzers. (Id: %s)";

    private static final String EXCEPTION_MSG_ANGEBOT_ANFRAGE_NICHT_VON_USER_INSTITUTION = "AngebotAnfrage gehoert nicht der Institution des angemeldetes Benutzers.";

    private final AngebotJpaRepository angebotRepository;
    private final AngebotAnfrageJpaRepository anfrageRepository;

    private final UserContextService userService;
    private final AngebotProzessService anfrageProzessService;

    @Transactional
    public void angebotDerUserInstitutionLoeschen(final @NotNull @Valid AngebotId angebotId) {

        // pruefe ob die Angebot existiert
        val angebot = getNichtBedienteAngebotDerUserInstitution(angebotId);

        // Alle offene Anfragen stornieren
        anfrageRepository.updateStatus(angebotId.getValue(), AngebotAnfrageStatus.OFFEN,
                AngebotAnfrageStatus.STORNIERT);

        // Prozesse stornieren
        anfrageProzessService.prozesseStornieren(angebotId);

        angebot.setDeleted(true);
        angebotRepository.save(angebot);
    }

    @Transactional
    public AngebotAnfrage angebotAnfrageErstellen(
            final @NotNull @Valid AngebotId angebotId, //
            final @NotNull @Valid InstitutionStandortId standortId, //
            final @NotBlank String kommentar, //
            final @NotNull BigDecimal anzahl) {

        val angebot = getNichtBedienteAngebot(angebotId);
        val userInstitution = getUserInstitution();

        var anfrage = anfrageRepository.save(AngebotAnfrageEntity.builder() //
                .angebot(angebot) //
                .institution(userInstitution) //
                .standort(getUserInstitutionStandort(userInstitution, standortId)) //
                .anzahl(anzahl) //
                .kommentar(kommentar) //
                .status(AngebotAnfrageStatus.OFFEN) //
                .build());
        val anfrageId = anfrage.getId();

        anfrage.setProzessInstanzId(anfrageProzessService.prozessStarten(//
                angebotId, //
                new PersonId(angebot.getCreatedBy()), //
                new AngebotAnfrageId(anfrageId), //
                new InstitutionId(angebot.getInstitution().getId())).getValue());

        updateAnfrage(anfrage);
        return AngebotAnfrageEntityConverter.convertAnfrage(anfrage);
    }

    @Transactional
    public void angebotAnfrageDerUserInstitutionStornieren(
            final @NotNull @Valid AngebotId angebotId, //
            final @NotNull @Valid AngebotAnfrageId anfrageId) {
        val anfrage = getOffeneAnfrageDerUserInstitution(angebotId, anfrageId);
        anfrage.setStatus(AngebotAnfrageStatus.STORNIERT);

        // Prozess stornieren
        anfrageProzessService.prozessStornieren(new ProzessInstanzId(anfrage.getProzessInstanzId()));

        anfrageRepository.save(anfrage);
    }

    @Transactional
    public void anfrageAbgelehnt(final @NotNull @Valid AngebotAnfrageId anfrageId) {
        val anfrage = getOffeneAnfrage(anfrageId);
        anfrage.setStatus(AngebotAnfrageStatus.ABGELEHNT);

        anfrageRepository.save(anfrage);
    }

    @Transactional
    public void anfrageAngenommen(final @NotNull @Valid AngebotAnfrageId anfrageId) {
        val anfrage = getOffeneAnfrage(anfrageId);

        // Angebot als bedient markieren
        val angebot = anfrage.getAngebot();

        // Restbestand des Angebots herabsetzen oder Exception werfen,
        // wenn die Anfrage größer als das Angebot ist
        val anfrageAnzahl = anfrage.getAnzahl();
        BigDecimal angebotRest = angebot.getRest();
        if (anfrageAnzahl.compareTo(angebotRest) > 0) {
            anfrage.setStatus(AngebotAnfrageStatus.STORNIERT);
            anfrageRepository.save(anfrage);
            throw new OperationNotAlloudException("Nicht genügend Ware auf Lager");
        }

        // Angebot angenommen
        anfrage.setStatus(AngebotAnfrageStatus.ANGENOMMEN);
        anfrageRepository.save(anfrage);

        if (anfrageAnzahl.compareTo(angebotRest) == 0) {
            angebot.setBedient(true);
            angebot.setRest(BigDecimal.ZERO);
        } else {
            angebot.setRest(angebotRest.subtract(anfrageAnzahl));
        }

        angebotRepository.save(angebot);
    }

    /* help methods */

    private AngebotAnfrage updateAnfrage(final @NotNull @Valid AngebotAnfrageEntity anfrageEntity) {
        return AngebotAnfrageEntityConverter.convertAnfrage(anfrageRepository.save(anfrageEntity));
    }

    AngebotEntity getNichtBedienteAngebotDerUserInstitution(final @NotNull @Valid AngebotId angebotId) {
        Assert.notNull(angebotId, "AngebotId ist null.");

        val angebot = getNichtBedienteAngebot(angebotId);

        if (!userService.isUserContextInstitution(new InstitutionId(angebot.getInstitution().getId()))) {
            throw new NotUserInstitutionObjectException(
                    String.format(EXCEPTION_MSG_ANGEBOT_NICHT_VON_USER_INSTITUTION, angebotId.getValue()));
        }

        return angebot;
    }

    AngebotEntity getNichtBedienteAngebot(final @NotNull @Valid AngebotId angebotId) {
        Assert.notNull(angebotId, "AngebotId ist null.");

        val angebot = angebotRepository.findById(angebotId.getValue()) //
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format(EXCEPTION_MSG_ANGEBOT_NICHT_GEFUNDEN, angebotId.getValue())));

        if (angebot.isBedient()) {
            throw new OperationNotAlloudException(EXCEPTION_MSG_ANGEBOT_BEDIEN);
        }

        return angebot;
    }

    AngebotAnfrageEntity getOffeneAnfrageDerUserInstitution(//
                                                            final @NotNull @Valid AngebotId angebotId, //
                                                            final @NotNull @Valid AngebotAnfrageId angebotAnfrageId) {
        Assert.notNull(angebotId, "AngebotId ist null.");
        Assert.notNull(angebotAnfrageId, "AngebotAnfrageId ist null.");

        val anfrage = getOffeneAnfrage(angebotId, angebotAnfrageId);

        if (!userService.isUserContextInstitution(new InstitutionId(anfrage.getInstitution().getId()))) {
            throw new NotUserInstitutionObjectException(
                    String.format(EXCEPTION_MSG_ANGEBOT_ANFRAGE_NICHT_VON_USER_INSTITUTION, angebotId.getValue()));
        }

        return anfrage;
    }

    AngebotAnfrageEntity getOffeneAnfrage(//
                                          final @NotNull @Valid AngebotId angebotId, //
                                          final @NotNull @Valid AngebotAnfrageId angebotAnfrageId) {
        Assert.notNull(angebotId, "AngebotId ist null.");
        Assert.notNull(angebotAnfrageId, "AngebotAnfrageId ist null.");

        val anfrage = getOffeneAnfrage(angebotAnfrageId);

        if (!angebotId.getValue().equals(anfrage.getAngebot().getId())) {
            throw new OperationNotAlloudException(String.format(EXCEPTION_MSG_ANGEBOT_ANFRAGE_NICHT_IN_ANGEBOT,
                    angebotId.getValue(), angebotAnfrageId.getValue()));
        }

        if (!AngebotAnfrageStatus.OFFEN.equals(anfrage.getStatus())) {
            throw new OperationNotAlloudException(
                    String.format(EXCEPTION_MSG_ANGEBOT_ANFRAGE_GESCHLOSSEN, anfrage.getStatus()));
        }

        return anfrage;
    }

    AngebotAnfrageEntity getOffeneAnfrage(//
                                          final @NotNull @Valid AngebotAnfrageId angebotAnfrageId) {
        Assert.notNull(angebotAnfrageId, "AngebotAnfrageId ist null.");

        return anfrageRepository.findById(angebotAnfrageId.getValue())//
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format(EXCEPTION_MSG_ANGEBOT_ANFRAGE_NICHT_GEFUNDEN, angebotAnfrageId.getValue())));
    }

    private InstitutionEntity getUserInstitution() {
        return InstitutionEntityConverter.convertInstitution(userService.getContextInstitution());
    }

    InstitutionStandortEntity getUserInstitutionStandort( //
                                                          final @NotNull InstitutionEntity userInstitution, //
                                                          final @NotNull @Valid InstitutionStandortId institutionStandortId) {
        Assert.notNull(userInstitution, "InstitutionEntity ist null.");
        Assert.notNull(institutionStandortId, "InstitutionStandortId ist null.");

        return userInstitution.findStandort(institutionStandortId.getValue()) //
                .orElseThrow(() -> new NotUserInstitutionObjectException(String
                        .format(EXCEPTION_MSG_STANDORT_NICHT_VON_USER_INSTITUTION, institutionStandortId.getValue())));
    }
}
