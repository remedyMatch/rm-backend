package io.remedymatch.angebot.domain.service;

import io.remedymatch.angebot.domain.model.AngebotAnfrage;
import io.remedymatch.angebot.domain.model.AngebotAnfrageId;
import io.remedymatch.angebot.domain.model.AngebotId;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageJpaRepository;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.geodaten.domain.GeocodingService;
import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.usercontext.UserContextService;
import lombok.AllArgsConstructor;
import lombok.val;
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
    private final GeocodingService geocodingService;
    private final UserContextService userContextService;

    @Transactional(readOnly = true)
    public List<AngebotAnfrage> findAlleAnfragenDerInstitution(final @NotNull @Valid InstitutionId institutionId) {
        return convertAnfragen(anfrageRepository.findAllByInstitution_Id(institutionId.getValue()));
    }

    @Transactional(readOnly = true)
    public List<AngebotAnfrage> findAlleOffeneAnfragenDerInstitution(final @NotNull @Valid InstitutionId institutionId) {
        return convertAnfragen(anfrageRepository.findAllByStatusOffenAndAngebotInstitution_Id(institutionId.getValue()));
    }

    @Transactional(readOnly = true)
    public List<AngebotAnfrage> findAlleOffeneAnfragenDerUserInstitution() {
        return findAlleOffeneAnfragenDerInstitution(userContextService.getContextInstitutionId());
    }

    @Transactional(readOnly = true)
    public List<AngebotAnfrage> findeAlleOffenenAnfragenFuerAngebotIds(final @NotNull @Valid List<AngebotId> angebotIds) {
        return mitEntfernung(convertAnfragen(anfrageRepository.findAllByAngebot_IdInAndOffen(angebotIds.stream().map(AngebotId::getValue).collect(Collectors.toList()))));
    }

    @Transactional(readOnly = true)
    public List<AngebotAnfrage> findeAlleAnfragenFuerIds(final @NotNull @Valid List<AngebotAnfrageId> angebotAnfrageIds) {

        var anfragen = convertAnfragen(anfrageRepository.findAllById(angebotAnfrageIds.stream().map(AngebotAnfrageId::getValue).collect(Collectors.toList())));

        //Nur die beteiligten anfragen zurückgeben
        val institutionId = userContextService.getContextInstitutionId();
        anfragen = anfragen.stream()
                .filter(a -> a.getInstitution().getId().equals(institutionId) || a.getAngebot().getInstitution().getId().equals(institutionId))
                .collect(Collectors.toList());

        return mitEntfernung(anfragen);
    }

    @Transactional(readOnly = true)
    public List<AngebotAnfrage> findAlleAnfragenDerAngebotInstitution(
            final @NotNull @Valid InstitutionId institutionId) {
        return convertAnfragen(anfrageRepository.findAllByAngebot_Institution_Id(institutionId.getValue()));
    }

    @Transactional
    public List<AngebotAnfrage> findeAlleMachedAnfragenDerInstitution() {
        val institutionId = userContextService.getContextInstitutionId();
        return mitEntfernung(convertAnfragen(anfrageRepository.findAllByStatusMatchedAndInstitution_Id(institutionId.getValue())));
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
        anfragen.forEach(anfrage -> anfrage.setEntfernung(geocodingService.berechneDistanzInKilometer(//
                anfrage.getStandort(), //
                anfrage.getAngebot().getStandort())));

        return anfragen;
    }
}
