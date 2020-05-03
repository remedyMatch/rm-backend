package io.remedymatch.angebot.domain.service;

import io.remedymatch.angebot.domain.model.AngebotAnfrage;
import io.remedymatch.angebot.domain.model.AngebotAnfrageId;
import io.remedymatch.angebot.domain.model.AngebotId;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageJpaRepository;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.geodaten.geocoding.domain.GeoCalcService;
import io.remedymatch.institution.domain.model.InstitutionId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.remedymatch.angebot.domain.service.AngebotAnfrageEntityConverter.convertAnfragen;

@AllArgsConstructor
@Validated
@Service
public class AngebotAnfrageSucheService {

    private static final String EXCEPTION_MSG_ANGEBOT_ANFRAGE_NICHT_GEFUNDEN = "AngebotAnfrage mit diesem Id nicht gefunden. (Id: %s)";

    private final AngebotAnfrageJpaRepository anfrageRepository;
    private final GeoCalcService geoCalcService;

    @Transactional(readOnly = true)
    public List<AngebotAnfrage> findAlleAnfragenDerInstitution(final @NotNull @Valid InstitutionId institutionId) {
        return convertAnfragen(anfrageRepository.findAllByInstitution_Id(institutionId.getValue()));
    }

    @Transactional(readOnly = true)
    public List<AngebotAnfrage> findeAlleOffenenAnfragenFuerAngebotIds(final @NotNull @Valid List<AngebotId> angebotIds) {
        return mitEntfernung(convertAnfragen(anfrageRepository.findAllByAngebot_IdIn(angebotIds.stream().map(AngebotId::getValue).collect(Collectors.toList()))));
    }

    @Transactional(readOnly = true)
    public List<AngebotAnfrage> findAlleAnfragenDerAngebotInstitution(
            final @NotNull @Valid InstitutionId institutionId) {
        return convertAnfragen(anfrageRepository.findAllByAngebot_Institution_Id(institutionId.getValue()));
    }

    /**
     * In Vergleich zur findAnfrage wirft eine ObjectNotFoundException wenn nicht
     * gefunden
     *
     * @param anfrageId AngebotAnfrageId
     * @return AngebotAnfrage
     * @throws ObjectNotFoundException
     */
    @Transactional(readOnly = true)
    public AngebotAnfrage getAnfrageOrElseThrow(final @NotNull @Valid AngebotAnfrageId anfrageId)
            throws ObjectNotFoundException {
        return findAnfrage(anfrageId).orElseThrow(() -> new ObjectNotFoundException(
                String.format(EXCEPTION_MSG_ANGEBOT_ANFRAGE_NICHT_GEFUNDEN, anfrageId.getValue())));
    }

    @Transactional(readOnly = true)
    public Optional<AngebotAnfrage> findAnfrage(final @NotNull @Valid AngebotAnfrageId anfrageId) {
        return anfrageRepository.findById(anfrageId.getValue()).map(AngebotAnfrageEntityConverter::convertAnfrage);
    }

    private List<AngebotAnfrage> mitEntfernung(final List<AngebotAnfrage> anfragen) {
        anfragen.forEach(anfrage -> anfrage.setEntfernung(geoCalcService.berechneDistanzInKilometer(//
                anfrage.getStandort(), //
                anfrage.getAngebot().getStandort())));

        return anfragen;
    }
}
