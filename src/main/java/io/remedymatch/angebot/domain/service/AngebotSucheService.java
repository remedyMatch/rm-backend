package io.remedymatch.angebot.domain.service;

import io.remedymatch.angebot.domain.model.Angebot;
import io.remedymatch.angebot.domain.model.AngebotFilterEntry;
import io.remedymatch.angebot.infrastructure.AngebotEntity;
import io.remedymatch.angebot.infrastructure.AngebotJpaRepository;
import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.model.ArtikelKategorieId;
import io.remedymatch.geodaten.domain.GeocodingService;
import io.remedymatch.usercontext.UserContextService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static io.remedymatch.angebot.domain.service.AngebotFilterConverter.convertFilterEntries;

@AllArgsConstructor
@Validated
@Service
public class AngebotSucheService {

    private final AngebotJpaRepository angebotRepository;
    private final AngebotAnfrageSucheService angebotAnfrageSucheService;

    private final UserContextService userService;
    private final GeocodingService geocodingService;

    @Transactional(readOnly = true)
    public List<AngebotFilterEntry> getArtikelKategorieFilter() {
        return convertFilterEntries(angebotRepository.findAllKategorienMitUnbedientenAngebotenFilter());
    }

    @Transactional(readOnly = true)
    public List<AngebotFilterEntry> getArtikelFilter(final @NotNull @Valid ArtikelKategorieId kategorieId) {
        return convertFilterEntries(angebotRepository.findAllArtikelInKategorieMitUnbedientenAngebotenFilter(kategorieId.getValue()));
    }

    @Transactional(readOnly = true)
    public List<AngebotFilterEntry> getArtikelVarianteFilter(final @NotNull @Valid ArtikelId artikelId) {
        return convertFilterEntries(angebotRepository.findAllArtikelVariantenInArtikelMitUnbedientenAngebotenFilter(artikelId.getValue()));
    }

    @Transactional(readOnly = true)
    public List<Angebot> findAlleNichtBedienteAngebote() {
        return mitEntfernung(angebotRepository.findAllByDeletedFalseAndBedientFalse());
    }

    @Transactional(readOnly = true)
    public List<Angebot> findAlleNichtBedienteAngeboteDerUserInstitution() {

        val userInstitution = userService.getContextInstitution();
        val angebote = mitEntfernung(angebotRepository.findAllByDeletedFalseAndBedientFalseAndInstitution_Id(userInstitution.getId().getValue()));

        //TODO geht das einfacher?
        val angebotIds = angebote.stream().map(Angebot::getId).collect(Collectors.toList());
        val anfragen = angebotAnfrageSucheService.findeAlleOffenenAnfragenFuerAngebotIds(angebotIds);
        val anfrageMap = anfragen.stream().collect(Collectors.groupingBy(anfrage -> anfrage.getAngebot().getId().getValue()));
        angebote.forEach(a -> {
            if (anfrageMap.containsKey(a.getId().getValue())) {
                a.setAnfragen(anfrageMap.get(a.getId().getValue()));
            }
        });
        return angebote;
    }

    private List<Angebot> mitEntfernung(final List<AngebotEntity> angebote) {
        return angebote.stream().map(this::mitEntfernung).collect(Collectors.toList());
    }


    private Angebot mitEntfernung(final AngebotEntity angebot) {
        val convertedAngebot = AngebotEntityConverter.convertAngebot(angebot);
        convertedAngebot.setEntfernung(geocodingService.berechneUserDistanzInKilometer(convertedAngebot.getStandort()));
        return convertedAngebot;
    }
}
