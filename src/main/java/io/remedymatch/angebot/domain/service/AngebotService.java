package io.remedymatch.angebot.domain.service;

import io.remedymatch.angebot.domain.model.AngebotAnfrage;
import io.remedymatch.angebot.domain.model.AngebotAnfrageId;
import io.remedymatch.angebot.domain.model.AngebotAnfrageStatus;
import io.remedymatch.angebot.domain.model.AngebotId;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageEntity;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageJpaRepository;
import io.remedymatch.angebot.infrastructure.AngebotEntity;
import io.remedymatch.angebot.infrastructure.AngebotJpaRepository;
import io.remedymatch.bedarf.domain.model.BedarfId;
import io.remedymatch.domain.NotUserInstitutionObjectException;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.domain.OperationNotAllowedException;
import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
import io.remedymatch.institution.domain.service.InstitutionEntityConverter;
import io.remedymatch.institution.domain.service.InstitutionStandortEntityConverter;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import io.remedymatch.nachricht.domain.model.Nachricht;
import io.remedymatch.nachricht.domain.model.NachrichtReferenz;
import io.remedymatch.nachricht.domain.model.NachrichtReferenzTyp;
import io.remedymatch.nachricht.domain.model.NeueNachricht;
import io.remedymatch.nachricht.domain.service.NachrichtService;
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
import java.util.List;

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
    private final AngebotProzessService angebotProzessService;

    private final NachrichtService nachrichtService;

    @Transactional
    public void angebotDerUserInstitutionSchliessen(final @NotNull @Valid AngebotId angebotId) {

        // pruefe ob die Angebot existiert
        val angebot = getNichtBedienteAngebotDerUserInstitution(angebotId);
        // Prozesse stornieren
        angebotProzessService.angebotSchliessen(angebotId);
        angebot.setDeleted(true);
        angebotRepository.save(angebot);
    }

    @Transactional
    public void angebotAlsGeschlossenMarkieren(final @NotNull @Valid AngebotId angebotId) {
        val angebot = getAngebot(angebotId);
        angebot.setDeleted(true);
        angebotRepository.save(angebot);
    }

    @Transactional
    public void angebotAnzahlAendern(final @NotNull @Valid AngebotId angebotId, @NotNull BigDecimal anzahl) {

        // pruefe ob das Angebot existiert
        val angebot = getNichtBedienteAngebotDerUserInstitution(angebotId);
        angebotProzessService.restAngebotAendern(angebotId, anzahl);

        angebot.setAnzahl(anzahl);
        angebotRepository.save(angebot);
    }


    public AngebotAnfrage angebotAnfrageErstellen(final @NotNull @Valid AngebotId angebotId, //
                                                  final @NotBlank String nachricht, //
                                                  final @NotNull BigDecimal anzahl, final @NotNull BedarfId bedarfId) {

        val angebot = getNichtBedienteAngebot(angebotId);

        if (angebot.getInstitution().getId().equals(userService.getContextInstitutionId())) {
            throw new OperationNotAllowedException("Das eigene Angebot kann nicht angefragt werden");
        }

        var anfrage = anfrageRepository.save(AngebotAnfrageEntity.builder() //
                .angebot(angebot) //
                .institution(getUserInstitution()) //
                .standort(getUserStandort()) //
                .anzahl(anzahl) //
                .bedarfId(bedarfId.getValue()) //
                .status(AngebotAnfrageStatus.OFFEN) //
                .build());

        angebotProzessService.anfrageErhalten(new AngebotAnfrageId(anfrage.getId()), angebotId, bedarfId);

        this.nachrichtZuAnfrageSenden(new AngebotAnfrageId(anfrage.getId()), NeueNachricht.builder().nachricht(nachricht).build());

        return AngebotAnfrageEntityConverter.convertAnfrage(anfrage);
    }

    public void angebotAnfrageDerUserInstitutionStornieren(final @NotNull @Valid AngebotId angebotId, //
                                                           final @NotNull @Valid AngebotAnfrageId anfrageId) {
        val anfrage = getOffeneAnfrageDerUserInstitution(angebotId, anfrageId);
        anfrage.setStatus(AngebotAnfrageStatus.STORNIERT);

        // Anfrage stornieren
        angebotProzessService.anfrageStornieren(anfrageId, angebotId);

        anfrageRepository.save(anfrage);
    }

    public void angebotAnfrageSchliessen(final @NotNull @Valid AngebotId angebotId, //
                                         final @NotNull @Valid AngebotAnfrageId anfrageId) {
        val anfrage = getOffeneAnfrage(angebotId, anfrageId);
        anfrage.setStatus(AngebotAnfrageStatus.STORNIERT);
        anfrageRepository.save(anfrage);
    }

    public void angebotAnfrageBeantworten(final @NotNull @Valid AngebotId angebotId, //
                                          final @NotNull @Valid AngebotAnfrageId anfrageId, //
                                          final @NotNull Boolean entscheidung) {
        val anfrage = getOffeneAnfrageDerUserInstitution(angebotId, anfrageId);
        val angebot = getNichtBedienteAngebot(angebotId);
        var restAnzahl = angebot.getRest();
        if (entscheidung) {
            restAnzahl = anfrageAngenommen(anfrage);
        } else {
            anfrageAbgelehnt(anfrage);
        }
        // Anfrage beantworten
        angebotProzessService.anfrageBeantworten(anfrageId, angebotId, entscheidung, restAnzahl);

        anfrageRepository.save(anfrage);
    }

    public void anfrageAbgelehnt(final @NotNull AngebotAnfrageEntity anfrage) {
        anfrage.setStatus(AngebotAnfrageStatus.ABGELEHNT);
        anfrageRepository.save(anfrage);
    }

    public BigDecimal anfrageAngenommen(final @NotNull AngebotAnfrageEntity anfrage) {

        // Angebot als bedient markieren
        val angebot = anfrage.getAngebot();

        // Restbestand des Angebots herabsetzen oder Exception werfen,
        // wenn die Anfrage größer als das Angebot ist
        val anfrageAnzahl = anfrage.getAnzahl();
        var restAnzahl = new BigDecimal(0);
        BigDecimal angebotRest = angebot.getRest();
        if (anfrageAnzahl.compareTo(angebotRest) > 0) {
            throw new OperationNotAllowedException("Nicht genügend Ware auf Lager");
        }

        // Angebot angenommen
        anfrage.setStatus(AngebotAnfrageStatus.ANGENOMMEN);
        anfrageRepository.save(anfrage);

        if (anfrageAnzahl.compareTo(angebotRest) == 0) {
            angebot.setBedient(true);
            angebot.setRest(BigDecimal.ZERO);
            restAnzahl = BigDecimal.ZERO;
        } else {
            restAnzahl = angebotRest.subtract(anfrageAnzahl);
            angebot.setRest(restAnzahl);
        }
        angebotRepository.save(angebot);
        return restAnzahl;
    }


    public void nachrichtZuAnfrageSenden(final @NotNull AngebotAnfrageId anfrageId, final @Valid NeueNachricht nachricht) {
        nachricht.setReferenzId(new NachrichtReferenz(anfrageId.getValue()));
        nachricht.setReferenzTyp(NachrichtReferenzTyp.ANGEBOT_ANFRAGE);
        nachrichtService.nachrichtSenden(nachricht);
    }

    public List<Nachricht> nachrichtenZuAnfrageLaden(final @NotNull AngebotAnfrageId anfrageId) {
        return nachrichtService.nachrichtenZuReferenzLaden(new NachrichtReferenz(anfrageId.getValue()));
    }


    /* help methods */

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
            throw new OperationNotAllowedException(EXCEPTION_MSG_ANGEBOT_BEDIEN);
        }

        return angebot;
    }

    AngebotEntity getAngebot(final @NotNull @Valid AngebotId angebotId) {
        Assert.notNull(angebotId, "AngebotId ist null.");

        val angebot = angebotRepository.findById(angebotId.getValue()) //
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format(EXCEPTION_MSG_ANGEBOT_NICHT_GEFUNDEN, angebotId.getValue())));

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
            throw new OperationNotAllowedException(String.format(EXCEPTION_MSG_ANGEBOT_ANFRAGE_NICHT_IN_ANGEBOT,
                    angebotId.getValue(), angebotAnfrageId.getValue()));
        }

        if (!AngebotAnfrageStatus.OFFEN.equals(anfrage.getStatus())) {
            throw new OperationNotAllowedException(
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
        return InstitutionEntityConverter.convertInstitution(userService.getContextStandort().getInstitution());
    }

    private InstitutionStandortEntity getUserStandort() {
        return InstitutionStandortEntityConverter.convertStandort(userService.getContextStandort().getStandort());
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
