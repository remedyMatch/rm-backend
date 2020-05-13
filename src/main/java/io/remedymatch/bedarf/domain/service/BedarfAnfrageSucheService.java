package io.remedymatch.bedarf.domain.service;

import io.remedymatch.bedarf.domain.model.BedarfAnfrage;
import io.remedymatch.bedarf.domain.model.BedarfAnfrageId;
import io.remedymatch.bedarf.domain.model.BedarfId;
import io.remedymatch.bedarf.infrastructure.BedarfAnfrageJpaRepository;
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

import static io.remedymatch.bedarf.domain.service.BedarfAnfrageEntityConverter.convertAnfragen;

@AllArgsConstructor
@Validated
@Service
public class BedarfAnfrageSucheService {

    private static final String EXCEPTION_MSG_BEDARF_ANFRAGE_NICHT_GEFUNDEN = "BedarfAnfrage mit diesem Id nicht gefunden. (Id: %s)";

    private final BedarfAnfrageJpaRepository anfrageRepository;
    private final GeocodingService geoCalcService;
    private final UserContextService userContextService;

    @Transactional(readOnly = true)
    public List<BedarfAnfrage> findAlleAnfragenDerInstitution(final @NotNull @Valid InstitutionId institutionId) {
        return convertAnfragen(anfrageRepository.findAllByInstitution_Id(institutionId.getValue()));
    }

    @Transactional(readOnly = true)
    public List<BedarfAnfrage> findAlleAnfragenDerBedarfInstitution(
            final @NotNull @Valid InstitutionId institutionId) {
        return convertAnfragen(anfrageRepository.findAllByBedarf_Institution_Id(institutionId.getValue()));
    }

    @Transactional(readOnly = true)
    public List<BedarfAnfrage> findAlleOffeneAnfragenDerInstitution(final @NotNull @Valid InstitutionId institutionId) {
        return BedarfAnfrageEntityConverter.convertAnfragen(anfrageRepository.findAllByStatusOffenAndBedarfInstitution_Id(institutionId.getValue()));
    }

    @Transactional(readOnly = true)
    public List<BedarfAnfrage> findAlleOffeneAnfragenDerUserInstitution() {
        return findAlleOffeneAnfragenDerInstitution(userContextService.getContextInstitutionId());
    }

    @Transactional(readOnly = true)
    public List<BedarfAnfrage> findeAlleOffenenAnfragenFuerBedarfIds(final @NotNull @Valid List<BedarfId> bedarfIds) {
        return mitEntfernung(BedarfAnfrageEntityConverter.convertAnfragen(anfrageRepository.findAllByAngebot_IdIn(bedarfIds.stream().map(BedarfId::getValue).collect(Collectors.toList()))));
    }

    @Transactional
    public List<BedarfAnfrage> findeAlleMachedAnfragenDerInstitution() {
        val institutionId = userContextService.getContextInstitutionId();
        return mitEntfernung(BedarfAnfrageEntityConverter.convertAnfragen(anfrageRepository.findAllByStatusMatchedAndInstitution_Id(institutionId.getValue())));
    }

    @Transactional(readOnly = true)
    public List<BedarfAnfrage> findeAlleAnfragenFuerIds(final @NotNull @Valid List<BedarfAnfrageId> bedarfAnfrageIds) {

        var anfragen = BedarfAnfrageEntityConverter.convertAnfragen(
                anfrageRepository.findAllById(bedarfAnfrageIds.stream()
                        .map(BedarfAnfrageId::getValue)
                        .collect(Collectors.toList())));

        //Nur die beteiligten anfragen zurückgeben
        val institutionId = userContextService.getContextInstitutionId();
        anfragen = anfragen.stream()
                .filter(a -> a.getInstitution().getId().equals(institutionId) || a.getBedarf().getInstitution().getId().equals(institutionId))
                .collect(Collectors.toList());

        return mitEntfernung(anfragen);
    }

    /**
     * In Vergleich zur findAnfrage wirft eine ObjectNotFoundException wenn nicht
     * gefunden
     *
     * @param anfrageId BedarfAnfrageId
     * @return BedarfAnfrage
     * @throws ObjectNotFoundException
     */
    @Transactional(readOnly = true)
    public BedarfAnfrage getAnfrageOrElseThrow(final @NotNull @Valid BedarfAnfrageId anfrageId)
            throws ObjectNotFoundException {
        return findAnfrage(anfrageId).orElseThrow(() -> new ObjectNotFoundException(
                String.format(EXCEPTION_MSG_BEDARF_ANFRAGE_NICHT_GEFUNDEN, anfrageId.getValue())));
    }

    @Transactional(readOnly = true)
    public Optional<BedarfAnfrage> findAnfrage(final @NotNull @Valid BedarfAnfrageId anfrageId) {
        return anfrageRepository.findById(anfrageId.getValue()).map(BedarfAnfrageEntityConverter::convertAnfrage);
    }

    private List<BedarfAnfrage> mitEntfernung(final List<BedarfAnfrage> anfragen) {
        anfragen.forEach(anfrage -> anfrage.setEntfernung(geoCalcService.berechneDistanzInKilometer(//
                anfrage.getStandort(), //
                anfrage.getBedarf().getStandort())));
        return anfragen;
    }
}
