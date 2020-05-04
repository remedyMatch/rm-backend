package io.remedymatch.bedarf.domain.service;

import io.remedymatch.bedarf.domain.model.BedarfAnfrage;
import io.remedymatch.bedarf.domain.model.BedarfAnfrageId;
import io.remedymatch.bedarf.domain.model.BedarfAnfrageStatus;
import io.remedymatch.bedarf.domain.model.BedarfId;
import io.remedymatch.bedarf.infrastructure.BedarfAnfrageEntity;
import io.remedymatch.bedarf.infrastructure.BedarfAnfrageJpaRepository;
import io.remedymatch.bedarf.infrastructure.BedarfEntity;
import io.remedymatch.bedarf.infrastructure.BedarfJpaRepository;
import io.remedymatch.domain.NotUserInstitutionObjectException;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.domain.OperationNotAlloudException;
import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
import io.remedymatch.institution.domain.service.InstitutionEntityConverter;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
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
public class BedarfService {

    private static final String EXCEPTION_MSG_BEDARF_NICHT_GEFUNDEN = "Bedarf mit diesem Id nicht gefunden. (Id: %s)";
    private static final String EXCEPTION_MSG_BEDARF_BEDIEN = "Bedarf bereits bedient.";
    private static final String EXCEPTION_MSG_BEDARF_NICHT_VON_USER_INSTITUTION = "Bedarf gehoert nicht der Institution des angemeldetes Benutzers.";

    private static final String EXCEPTION_MSG_BEDARF_ANFRAGE_NICHT_GEFUNDEN = "BedarfAnfrage mit diesem Id nicht gefunden. (Id: %s)";
    private static final String EXCEPTION_MSG_BEDARF_ANFRAGE_NICHT_IN_BEDARF = "BedarfAnfrage gehört nicht zu dieser Bedarf. (BedarfId: %s, AnfrageId: $s)";
    private static final String EXCEPTION_MSG_BEDARF_ANFRAGE_GESCHLOSSEN = "BedarfAnfrage ist bereits erledigt. (Status: %s)";

    private static final String EXCEPTION_MSG_STANDORT_NICHT_VON_USER_INSTITUTION = "Standort gehoert nicht der Institution des angemeldetes Benutzers. (Id: %s)";

    private static final String EXCEPTION_MSG_BEDARF_ANFRAGE_NICHT_VON_USER_INSTITUTION = "BedarfAnfrage gehoert nicht der Institution des angemeldetes Benutzers.";

    private final BedarfJpaRepository bedarfRepository;
    private final BedarfAnfrageJpaRepository anfrageRepository;

    private final UserContextService userService;
    private final BedarfProzessService bedarfProzessService;

    @Transactional
    public void bedarfDerUserInstitutionSchliessen(final @NotNull @Valid BedarfId bedarfId) {

        val bedarf = getNichtBedienteBedarfDerUserInstitution(bedarfId);

        // Alle laufende Anfragen stornieren
        anfrageRepository.updateStatus(bedarfId.getValue(), BedarfAnfrageStatus.OFFEN,
                BedarfAnfrageStatus.STORNIERT);

        // Prozesse stornieren
        bedarfProzessService.bedarfSchliessen(bedarfId);

        bedarf.setDeleted(true);
        bedarfRepository.save(bedarf);
    }

    @Transactional
    public void bedarfAnzahlAendern(final @NotNull @Valid BedarfId bedarfId, @NotNull BigDecimal anzahl) {

        // pruefe ob das Angebot existiert
        val bedarf = getNichtBedienteBedarfDerUserInstitution(bedarfId);
        bedarfProzessService.restAngebotAendern(bedarfId, anzahl);

        bedarf.setAnzahl(anzahl);
        bedarfRepository.save(bedarf);
    }

    @Transactional
    public BedarfAnfrage bedarfAnfrageErstellen(
            final @NotNull @Valid BedarfId bedarfId, //
            final @NotNull @Valid InstitutionStandortId standortId, //
            final @NotBlank String kommentar, //
            final @NotNull BigDecimal anzahl) {

        val bedarf = getNichtBedienteBedarf(bedarfId);
        val userInstitution = getUserInstitution();

        var anfrage = anfrageRepository.save(BedarfAnfrageEntity.builder() //
                .bedarf(bedarf) //
                .institution(userInstitution) //
                .standort(getUserInstitutionStandort(userInstitution, standortId)) //
                .anzahl(anzahl) //
                .kommentar(kommentar) //
                .status(BedarfAnfrageStatus.OFFEN) //
                .build());

        bedarfProzessService.anfrageErhalten(new BedarfAnfrageId(anfrage.getId()), bedarfId);
        return BedarfAnfrageEntityConverter.convertAnfrage(anfrage);
    }

    @Transactional
    public void bedarfAnfrageDerUserInstitutionStornieren(//
                                                          final @NotNull @Valid BedarfId bedarfId, //
                                                          final @NotNull @Valid BedarfAnfrageId anfrageId) {
        val anfrage = getOffeneAnfrageDerUserInstitution(bedarfId, anfrageId);
        anfrage.setStatus(BedarfAnfrageStatus.STORNIERT);

        // Anfrage stornieren
        bedarfProzessService.anfrageStornieren(anfrageId, bedarfId);

        anfrageRepository.save(anfrage);
    }


    @Transactional
    public void bedarfAnfrageBeantworten(
            final @NotNull @Valid BedarfId bedarfId, //
            final @NotNull @Valid BedarfAnfrageId anfrageId, //
            final @NotNull Boolean entscheidung) {
        val anfrage = getOffeneAnfrageDerUserInstitution(bedarfId, anfrageId);
        val bedarf = getNichtBedienteBedarf(bedarfId);
        var restAnzahl = bedarf.getRest();
        if (entscheidung) {
            restAnzahl = anfrageAngenommen(anfrage);
        } else {
            anfrageAbgelehnt(anfrage);
        }
        // Anfrage beantworten
        bedarfProzessService.anfrageBeantworten(anfrageId, bedarfId, entscheidung, restAnzahl);
        anfrageRepository.save(anfrage);
    }

    @Transactional
    public void bedarfAnfrageSchliessen(
            final @NotNull @Valid BedarfId bedarfId, //
            final @NotNull @Valid BedarfAnfrageId anfrageId) {
        val anfrage = getOffeneAnfrage(bedarfId, anfrageId);
        anfrage.setStatus(BedarfAnfrageStatus.STORNIERT);
        anfrageRepository.save(anfrage);
    }

    @Transactional
    public void anfrageAbgelehnt(final @NotNull @Valid BedarfAnfrageEntity anfrage) {
        anfrage.setStatus(BedarfAnfrageStatus.ABGELEHNT);
        anfrageRepository.save(anfrage);
    }

    @Transactional
    public BigDecimal anfrageAngenommen(final @NotNull @Valid BedarfAnfrageEntity anfrage) {

        // Bedarf als bedient markieren
        val bedarf = anfrage.getBedarf();

        // Restbestand des Bedarfs herabsetzen oder Exception werfen,
        // wenn die Anfrage größer als das Bedarf ist
        val anfrageAnzahl = anfrage.getAnzahl();
        var restAnzahl = new BigDecimal(0);
        BigDecimal bedarfRest = bedarf.getRest();
        if (anfrageAnzahl.compareTo(bedarfRest) > 0) {
            throw new OperationNotAlloudException("Nicht genügend Ware auf Lager");
        }

        // Bedarf angenommen
        anfrage.setStatus(BedarfAnfrageStatus.ANGENOMMEN);
        anfrageRepository.save(anfrage);

        if (anfrageAnzahl.compareTo(bedarfRest) == 0) {
            bedarf.setBedient(true);
            bedarf.setRest(BigDecimal.ZERO);
            restAnzahl = BigDecimal.ZERO;
        } else {
            restAnzahl = bedarfRest.subtract(anfrageAnzahl);
            bedarf.setRest(restAnzahl);
        }

        bedarfRepository.save(bedarf);
        return restAnzahl;
    }

    /* help methods */

    private BedarfAnfrage updateAnfrage(final @NotNull @Valid BedarfAnfrageEntity anfrageEntity) {
        return BedarfAnfrageEntityConverter.convertAnfrage(anfrageRepository.save(anfrageEntity));
    }

    BedarfEntity getNichtBedienteBedarfDerUserInstitution(final @NotNull @Valid BedarfId bedarfId) {
        Assert.notNull(bedarfId, "BedarfId ist null.");

        val bedarf = getNichtBedienteBedarf(bedarfId);

        if (!userService.isUserContextInstitution(new InstitutionId(bedarf.getInstitution().getId()))) {
            throw new NotUserInstitutionObjectException(
                    String.format(EXCEPTION_MSG_BEDARF_NICHT_VON_USER_INSTITUTION, bedarfId.getValue()));
        }

        return bedarf;
    }

    BedarfEntity getNichtBedienteBedarf(final @NotNull @Valid BedarfId bedarfId) {
        Assert.notNull(bedarfId, "BedarfId ist null.");

        val bedarf = bedarfRepository.findById(bedarfId.getValue()) //
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format(EXCEPTION_MSG_BEDARF_NICHT_GEFUNDEN, bedarfId.getValue())));

        if (bedarf.isBedient()) {
            throw new OperationNotAlloudException(EXCEPTION_MSG_BEDARF_BEDIEN);
        }

        return bedarf;
    }

    BedarfAnfrageEntity getOffeneAnfrageDerUserInstitution(//
                                                           final @NotNull @Valid BedarfId bedarfId, //
                                                           final @NotNull @Valid BedarfAnfrageId bedarfAnfrageId) {
        Assert.notNull(bedarfId, "BedarfId ist null.");
        Assert.notNull(bedarfAnfrageId, "BedarfAnfrageId ist null.");

        val anfrage = getOffeneAnfrage(bedarfId, bedarfAnfrageId);

        if (!userService.isUserContextInstitution(new InstitutionId(anfrage.getInstitution().getId()))) {
            throw new NotUserInstitutionObjectException(
                    String.format(EXCEPTION_MSG_BEDARF_ANFRAGE_NICHT_VON_USER_INSTITUTION, bedarfId.getValue()));
        }

        return anfrage;
    }

    BedarfAnfrageEntity getOffeneAnfrage(//
                                         final @NotNull @Valid BedarfId bedarfId, //
                                         final @NotNull @Valid BedarfAnfrageId bedarfAnfrageId) {
        Assert.notNull(bedarfId, "BedarfId ist null.");
        Assert.notNull(bedarfAnfrageId, "BedarfAnfrageId ist null.");

        val anfrage = getOffeneAnfrage(bedarfAnfrageId);

        if (!bedarfId.getValue().equals(anfrage.getBedarf().getId())) {
            throw new OperationNotAlloudException(String.format(EXCEPTION_MSG_BEDARF_ANFRAGE_NICHT_IN_BEDARF,
                    bedarfId.getValue(), bedarfAnfrageId.getValue()));
        }

        if (!BedarfAnfrageStatus.OFFEN.equals(anfrage.getStatus())) {
            throw new OperationNotAlloudException(
                    String.format(EXCEPTION_MSG_BEDARF_ANFRAGE_GESCHLOSSEN, anfrage.getStatus()));
        }

        return anfrage;
    }

    BedarfAnfrageEntity getOffeneAnfrage(//
                                         final @NotNull @Valid BedarfAnfrageId bedarfAnfrageId) {
        Assert.notNull(bedarfAnfrageId, "BedarfAnfrageId ist null.");

        return anfrageRepository.findById(bedarfAnfrageId.getValue())//
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format(EXCEPTION_MSG_BEDARF_ANFRAGE_NICHT_GEFUNDEN, bedarfAnfrageId.getValue())));
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
