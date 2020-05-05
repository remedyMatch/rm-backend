package io.remedymatch.angebot.domain.service;

import io.remedymatch.angebot.domain.model.Angebot;
import io.remedymatch.angebot.domain.model.NeuesAngebot;
import io.remedymatch.angebot.infrastructure.AngebotEntity;
import io.remedymatch.angebot.infrastructure.AngebotJpaRepository;
import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.model.ArtikelVarianteId;
import io.remedymatch.artikel.domain.service.ArtikelEntityConverter;
import io.remedymatch.artikel.domain.service.ArtikelSucheService;
import io.remedymatch.artikel.infrastructure.ArtikelVarianteEntity;
import io.remedymatch.domain.NotUserInstitutionObjectException;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.geodaten.domain.GeocodingService;
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
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Service
@Validated
@Transactional
public class AngebotAnlageService {
    private static final String EXCEPTION_MSG_ARTIKEL_VARIANTE_NICHT_GEFUNDEN = "ArtikelVariante mit diesem Id nicht gefunden. (Id: %s)";

    private static final String EXCEPTION_MSG_STANDORT_NICHT_VON_USER_INSTITUTION = "Standort gehoert nicht der Institution des angemeldetes Benutzers. (Id: %s)";
    private static final String EXCEPTION_MSG_ARTIKEL_VARIANTE_NICHT_VORHANDEN = "Artikel Variante nicht vorhanden";
    private static final String EXCEPTION_MSG_ARTIKEL_NICHT_VORHANDEN = "Artikel nicht vorhanden";

    private final AngebotJpaRepository angebotRepository;
    private final UserContextService userService;
    private final ArtikelSucheService artikelSucheService;
    private final GeocodingService geocodingService;
    private final AngebotProzessService angebotProzessService;

    @Transactional
    public Angebot neueAngebotEinstellen(final @NotNull @Valid NeuesAngebot neuesAngebot) {

        //standort ist der zugewiesene standort der Institution
        neuesAngebot.setStandortId(userService.getContextUser().getAktuelleInstitution().getStandort().getId());

        val userInstitution = getUserInstitution();
        val artikelVariante = getArtikelVariante(neuesAngebot.getArtikelVarianteId());
        val artikel = artikelSucheService.findArtikel(new ArtikelId(artikelVariante.getArtikel()))
                .orElseThrow(() -> new ObjectNotFoundException(EXCEPTION_MSG_ARTIKEL_NICHT_VORHANDEN));

        val angebot = mitEntfernung(angebotRepository.save(AngebotEntity.builder() //
                .anzahl(neuesAngebot.getAnzahl()) //
                .rest(neuesAngebot.getAnzahl()) //
                .artikelVariante(artikelVariante) //
                .institution(userInstitution) //
                .artikel(ArtikelEntityConverter.convertArtikel(artikel)) //
                .standort(getUserInstitutionStandort(userInstitution, neuesAngebot.getStandortId())) //
                .haltbarkeit(neuesAngebot.getHaltbarkeit()) //
                .steril(neuesAngebot.isSteril()) //
                .originalverpackt(neuesAngebot.isOriginalverpackt()) //
                .medizinisch(neuesAngebot.isMedizinisch()) //
                .kommentar(neuesAngebot.getKommentar()) //
                .bedient(false) //
                .build()));
        angebotProzessService.prozessStarten(angebot.getId(), userService.getContextUserId(), angebot.getInstitution().getId());

        return angebot;
    }

    private Angebot mitEntfernung(final @NotNull AngebotEntity angebot) {
        val convertedAngebot = AngebotEntityConverter.convertAngebot(angebot);
        convertedAngebot.setEntfernung(geocodingService.berechneUserDistanzInKilometer(convertedAngebot.getStandort()));
        return convertedAngebot;
    }

    ArtikelVarianteEntity getArtikelVariante(//
                                             final @NotNull @Valid ArtikelVarianteId artikelVarianteId) {
        Assert.notNull(artikelVarianteId, "ArtikelVarianteId sind null.");

        return artikelSucheService.findArtikelVariante(artikelVarianteId).map(ArtikelEntityConverter::convertVariante) //
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format(EXCEPTION_MSG_ARTIKEL_VARIANTE_NICHT_GEFUNDEN, artikelVarianteId.getValue())));
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
