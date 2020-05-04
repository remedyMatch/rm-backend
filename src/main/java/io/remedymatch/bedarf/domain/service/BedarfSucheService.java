package io.remedymatch.bedarf.domain.service;

import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.model.ArtikelKategorieId;
import io.remedymatch.bedarf.domain.model.Bedarf;
import io.remedymatch.bedarf.domain.model.BedarfFilterEntry;
import io.remedymatch.bedarf.infrastructure.BedarfEntity;
import io.remedymatch.bedarf.infrastructure.BedarfJpaRepository;
import io.remedymatch.geodaten.geocoding.domain.GeoCalcService;
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

import static io.remedymatch.bedarf.domain.service.BedarfFilterConverter.convertFilterEntries;

@AllArgsConstructor
@Validated
@Service
public class BedarfSucheService {

    private final BedarfJpaRepository bedarfRepository;
    private final BedarfAnfrageSucheService bedarfAnfrageSucheService;

    private final UserContextService userService;
    private final GeoCalcService geoCalcService;

    @Transactional(readOnly = true)
    public List<BedarfFilterEntry> getArtikelKategorieFilter() {
        return convertFilterEntries(bedarfRepository.countAllBedarfKategorienByDeletedFalseAndBedientFalse());
    }

    @Transactional(readOnly = true)
    public List<BedarfFilterEntry> getArtikelFilter(final @NotNull @Valid ArtikelKategorieId kategorieId) {
        return convertFilterEntries(bedarfRepository
                .countAllBedarfArtikelByDeletedFalseAndBedientFalseAndArtikel_ArtikelKategorie(kategorieId.getValue()));
    }

    @Transactional(readOnly = true)
    public List<BedarfFilterEntry> getArtikelVarianteFilter(final @NotNull @Valid ArtikelId artikelId) {
        return convertFilterEntries(bedarfRepository
                .countAllBedarfArtikelVariantenByDeletedFalseAndBedientFalseAndArtikel_Id(artikelId.getValue()));
    }

    @Transactional(readOnly = true)
    public List<Bedarf> findAlleNichtBedienteBedarfe() {
        return mitEntfernung(bedarfRepository.findAllByDeletedFalseAndBedientFalse());
    }

    @Transactional(readOnly = true)
    public List<Bedarf> findAlleNichtBedienteBedarfeDerUserInstitution() {
        val userInstitution = userService.getContextInstitution();
        val bedarfe = mitEntfernung(bedarfRepository
                .findAllByDeletedFalseAndBedientFalseAndInstitution_Id(userInstitution.getId().getValue()));

        //TODO geht das einfacher?
        val bedarfIds = bedarfe.stream().map(Bedarf::getId).collect(Collectors.toList());
        val anfragen = bedarfAnfrageSucheService.findeAlleOffenenAnfragenFuerBedarfIds(bedarfIds);
        val anfrageMap = anfragen.stream().collect(Collectors.groupingBy(anfrage -> anfrage.getBedarf().getId().getValue()));
        bedarfe.forEach(b -> {
            if (anfrageMap.containsKey(b.getId().getValue())) {
                b.setAnfragen(anfrageMap.get(b.getId().getValue()));
            }
        });
        return bedarfe;
    }

    private List<Bedarf> mitEntfernung(final List<BedarfEntity> bedarfe) {
        return bedarfe.stream().map(this::mitEntfernung).collect(Collectors.toList());
    }

    private Bedarf mitEntfernung(final BedarfEntity bedarf) {
        val convertedBedarf = BedarfEntityConverter.convertBedarf(bedarf);
        convertedBedarf.setEntfernung(geoCalcService.berechneUserDistanzInKilometer(convertedBedarf.getStandort()));
        return convertedBedarf;
    }
}
