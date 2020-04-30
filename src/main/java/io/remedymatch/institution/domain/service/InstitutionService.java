package io.remedymatch.institution.domain.service;

import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.domain.OperationNotAlloudException;
import io.remedymatch.engine.client.EngineClient;
import io.remedymatch.engine.domain.BusinessKey;
import io.remedymatch.geodaten.domain.StandortService;
import io.remedymatch.institution.domain.model.*;
import io.remedymatch.institution.infrastructure.*;
import io.remedymatch.institution.process.InstitutionAntragProzessConstants;
import io.remedymatch.usercontext.UserContextService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Validated
@Service
@Transactional
@Slf4j
public class InstitutionService {

    private static final String EXCEPTION_MSG_UPDATE_OHNE_DATEN = "Keine Aenderungen in InstitutionUpdate gefunden";
    private static final String EXCEPTION_MSG_STANDORT_NICHT_IN_USER_INSTITUTION = "Standort nicht in UserInstitution gedfunden. (StandortId: $s)";

    private final InstitutionJpaRepository institutionRepository;
    private final InstitutionStandortJpaRepository institutionStandortRepository;
    private final InstitutionAntragJpaRepository institutionAntragJpaRepository;
    private final EngineClient engineClient;

    private final StandortService standortService;
    private final UserContextService userService;

    public Institution institutionAnlegen(final @NotNull @Valid NeueInstitution neueInstitution) {

        log.debug("Lege neue Institution an: " + neueInstitution);

        val hauptstandort = standortErstellen(neueInstitution.getHauptstandort());
        return updateInstitution(InstitutionEntity.builder() //
                .name(neueInstitution.getName()) //
                .institutionKey(neueInstitution.getInstitutionKey()) //
                .typ(neueInstitution.getTyp()) //
                .hauptstandort(hauptstandort) //
                .standorte(Arrays.asList(hauptstandort)) //
                .build());
    }

    public Institution userInstitutionAktualisieren(final @NotNull @Valid InstitutionUpdate update) {

        log.debug("Aktualisiere User Institution: " + update);

        if (StringUtils.isBlank(update.getNeueName()) && update.getNeuesTyp() == null
                && update.getNeuesHauptstandortId() == null) {
            throw new OperationNotAlloudException(EXCEPTION_MSG_UPDATE_OHNE_DATEN);
        }

        val userInstitution = getUserInstitution();
        if (StringUtils.isNotBlank(update.getNeueName())) {
            userInstitution.setName(update.getNeueName());
        }
        if (update.getNeuesTyp() != null) {
            userInstitution.setTyp(update.getNeuesTyp());
        }
        if (update.getNeuesHauptstandortId() != null) {
            userInstitution.setHauptstandort(getStandort(userInstitution, update.getNeuesHauptstandortId()));
        }

        return updateInstitution(userInstitution);
    }

    public Institution userInstitutionHauptstandortHinzufuegen(
            final @NotNull @Valid NeuerInstitutionStandort neuesStandort) {

        log.debug("Setze neues Hauptstandort in User Institution: " + neuesStandort);

        val userInstitution = getUserInstitution();
        val standort = standortErstellen(neuesStandort);
        userInstitution.addStandort(standort);
        userInstitution.setHauptstandort(standort);

        return updateInstitution(userInstitution);
    }

    public Institution userInstitutionStandortHinzufuegen(
            final @NotNull @Valid NeuerInstitutionStandort neuesStandort) {

        log.debug("Setze neues Standort in User Institution: " + neuesStandort);

        val userInstitution = getUserInstitution();
        userInstitution.getStandorte().add(standortErstellen(neuesStandort));

        return updateInstitution(userInstitution);
    }


    public void institutionBeantragen(@NotNull @Valid final InstitutionAntrag antrag) {
        log.debug("Institution beantragen gestartet: " + antrag);

        //weitere Daten füllen
        val antragsteller = userService.getContextUser().getId().getValue();
        antrag.setAntragsteller(antragsteller);
        antrag.setStatus(InstitutionAntragStatus.OFFEN);

        val gespeicherterAntrag = updateAntrag(antrag);
        institutionAntragProzessStarten(gespeicherterAntrag);
    }


    public List<InstitutionAntrag> ladeErstellteAntraege() {
        return institutionAntragJpaRepository.findAllByAntragsteller(userService.getContextUserId().getValue())
                .stream().map(InstitutionAntragEntityConverter::convertAntrag).collect(Collectors.toList());
    }

    public void antragAblehnen(final @NotNull InstitutionAntragId antragId) {
        val antrag = institutionAntragJpaRepository.findById(antragId.getValue())
                .orElseThrow(() -> new ObjectNotFoundException("Antrag ist nicht vorhanden"));
        antrag.setStatus(InstitutionAntragStatus.ABGELEHNT);
        institutionAntragJpaRepository.save(antrag);
    }

    public void antragGenehmigen(final @NotNull InstitutionAntragId antragId) {
        val antrag = institutionAntragJpaRepository.findById(antragId.getValue())
                .orElseThrow(() -> new ObjectNotFoundException("Antrag ist nicht vorhanden"));

        //antrag als genehmigt markieren
        antrag.setStatus(InstitutionAntragStatus.GENEHMIGT);
        institutionAntragJpaRepository.save(antrag);

        //institution aus antrag erstellen
        val institution = institutionAusAntragErstellen(antrag);
        this.institutionAnlegen(institution);

        //institution dem user zuweisen
    }

    public Institution institutionAusAntragAnlegen(final @NotNull InstitutionAntragId antragId) {
        val antrag = institutionAntragJpaRepository.findById(antragId.getValue())
                .orElseThrow(() -> new ObjectNotFoundException("Antrag ist nicht vorhanden"));

        val neueInstitution = institutionAusAntragErstellen(antrag);
        return this.institutionAnlegen(neueInstitution);
    }

    /* help methods */

    private NeueInstitution institutionAusAntragErstellen(InstitutionAntragEntity antrag) {
        val neueInstitution = NeueInstitution.builder()
                .name(antrag.getName())
                .typ(antrag.getInstitutionTyp())
                //TODO key muss überarbeitet werden
                .institutionKey(antrag.getWebseite())
                .build();
        val hauptstandort = NeuerInstitutionStandort.builder()
                .ort(antrag.getOrt())
                .hausnummer(antrag.getHausnummer())
                .land(antrag.getLand())
                .name(antrag.getName())
                .plz(antrag.getPlz())
                .strasse(antrag.getStrasse())
                .build();
        neueInstitution.setHauptstandort(hauptstandort);
        return neueInstitution;
    }

    private void institutionAntragProzessStarten(@NotNull @Valid final InstitutionAntrag antrag) {

        val variables = new HashMap<String, Object>();
        engineClient.prozessStarten(InstitutionAntragProzessConstants.PROZESS_KEY, new BusinessKey(antrag.getId().getValue()), userService.getContextUserId(), variables);
    }

    private InstitutionAntrag updateAntrag(InstitutionAntrag antrag) {
        return InstitutionAntragEntityConverter.convertAntrag(institutionAntragJpaRepository.save(InstitutionAntragEntityConverter.convertAntrag(antrag)));
    }

    private Institution updateInstitution(final @NotNull @Valid InstitutionEntity institutionEntity) {
        return InstitutionEntityConverter.convertInstitution(institutionRepository.save(institutionEntity));
    }

    private InstitutionEntity getUserInstitution() {
        return InstitutionEntityConverter.convertInstitution(userService.getContextInstitution());
    }

    InstitutionStandortEntity getStandort(//
                                          final @NotNull @Valid InstitutionEntity institution, //
                                          @NotNull @Valid final InstitutionStandortId standortId) {
        return institution.findStandort(standortId.getValue()).orElseThrow(() -> new ObjectNotFoundException(String
                .format(EXCEPTION_MSG_STANDORT_NICHT_IN_USER_INSTITUTION, institution.getId(), standortId.getValue())));
    }

    private InstitutionStandortEntity standortErstellen(final @NotNull @Valid NeuerInstitutionStandort neuesStandort) {
        return institutionStandortRepository.save(mitGeodatenErweitern(InstitutionStandortEntity.builder() //
                .name(neuesStandort.getName()) //
                .strasse(neuesStandort.getStrasse()) //
                .hausnummer(neuesStandort.getHausnummer()) //
                .plz(neuesStandort.getPlz()) //
                .ort(neuesStandort.getOrt()) //
                .land(neuesStandort.getLand()) //
                .build()));
    }

    InstitutionStandortEntity mitGeodatenErweitern(final InstitutionStandortEntity standort) {
        val addresseFuerGeocoding = formatAdresse(standort);
        log.info("Suche Geodaten für: " + addresseFuerGeocoding);
        var longlatList = standortService.findePointsByAdressString(addresseFuerGeocoding);

        if (longlatList == null || longlatList.size() == 0) {
            throw new ObjectNotFoundException("Die Adresse konnte nicht aufgelöst werden");
        }

        standort.setLatitude(BigDecimal.valueOf(longlatList.get(0).getLatitude()));
        standort.setLongitude(BigDecimal.valueOf(longlatList.get(0).getLongitude()));

        return standort;
    }

    private String formatAdresse(final InstitutionStandortEntity standort) {
        return String.format("%s %s, %s %s, %s", //
                standort.getStrasse(), //
                standort.getHausnummer(), //
                standort.getPlz(), //
                standort.getOrt(), //
                standort.getLand());
    }

}
