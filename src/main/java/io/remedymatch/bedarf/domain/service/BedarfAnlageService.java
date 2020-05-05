package io.remedymatch.bedarf.domain.service;

import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.model.ArtikelVariante;
import io.remedymatch.artikel.domain.model.ArtikelVarianteId;
import io.remedymatch.artikel.domain.service.ArtikelEntityConverter;
import io.remedymatch.artikel.domain.service.ArtikelSucheService;
import io.remedymatch.artikel.infrastructure.ArtikelEntity;
import io.remedymatch.artikel.infrastructure.ArtikelVarianteEntity;
import io.remedymatch.bedarf.domain.model.Bedarf;
import io.remedymatch.bedarf.domain.model.NeuerBedarf;
import io.remedymatch.bedarf.infrastructure.BedarfEntity;
import io.remedymatch.bedarf.infrastructure.BedarfJpaRepository;
import io.remedymatch.domain.NotUserInstitutionObjectException;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.domain.OperationNotAlloudException;
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
public class BedarfAnlageService {
    private static final String EXCEPTION_MSG_ARTIKEL_NICHT_GEFUNDEN = "Artikel mit diesem Id nicht gefunden. (Id: %s)";
    private static final String EXCEPTION_MSG_ARTIKEL_VARIANTE_NICHT_GEFUNDEN = "ArtikelVariante mit diesem Id nicht gefunden. (Id: %s)";
    private static final String EXCEPTION_MSG_ARTIKEL_UND_ARTIKEL_VARIANTE_PASSEN_NICHT_ZUSAMMEN = "Artikel und ArtikelVariante passen nicht zusammen.";
    private static final String EXCEPTION_MSG_ARTIKEL_UND_ARTIKEL_VARIANTE_LEER = "Artikel und ArtikelVariante sind beide leer.";

    private static final String EXCEPTION_MSG_STANDORT_NICHT_VON_USER_INSTITUTION = "Standort gehoert nicht der Institution des angemeldetes Benutzers. (Id: %s)";

    private final BedarfJpaRepository bedarfRepository;

    private final UserContextService userService;
    private final ArtikelSucheService artikelSucheService;
    private final GeocodingService geocodingService;

    @Transactional
    public Bedarf neuenBedarfEinstellen(final @NotNull @Valid NeuerBedarf neuerBedarf) {
        if (neuerBedarf.getArtikelId() == null && neuerBedarf.getArtikelVarianteId() == null) {
            throw new OperationNotAlloudException(EXCEPTION_MSG_ARTIKEL_UND_ARTIKEL_VARIANTE_LEER);
        }

        neuerBedarf.setStandortId(userService.getContextUser().getAktuelleInstitution().getStandort().getId());

        val userInstitution = getUserInstitution();

        ArtikelEntity artikelEntity = null;
        ArtikelVarianteEntity artikelVarianteEntity = null;
        if (neuerBedarf.getArtikelVarianteId() != null) {
            val artikelVariante = getArtikelVariante(neuerBedarf.getArtikelVarianteId());
            if (neuerBedarf.getArtikelId() != null
                    && !artikelVariante.getArtikelId().equals(neuerBedarf.getArtikelId())) {
                throw new OperationNotAlloudException(EXCEPTION_MSG_ARTIKEL_UND_ARTIKEL_VARIANTE_PASSEN_NICHT_ZUSAMMEN);
            }

            artikelVarianteEntity = ArtikelEntityConverter.convertVariante(artikelVariante);
            artikelEntity = getArtikel(artikelVariante.getArtikelId());
        } else {
            artikelEntity = getArtikel(neuerBedarf.getArtikelId());
        }

        return mitEntfernung(bedarfRepository.save(BedarfEntity.builder() //
                .anzahl(neuerBedarf.getAnzahl()) //
                .rest(neuerBedarf.getAnzahl()) //
                .artikel(artikelEntity) //
                .artikelVariante(artikelVarianteEntity) //
                .institution(userInstitution) //
                .standort(getUserInstitutionStandort(userInstitution, neuerBedarf.getStandortId())) //
                .steril(neuerBedarf.isSteril()) //
                .medizinisch(neuerBedarf.isMedizinisch()) //
                .kommentar(neuerBedarf.getKommentar()) //
                .bedient(false) //
                .build()));
    }

    private Bedarf mitEntfernung(final @NotNull BedarfEntity bedarf) {
        val convertedBedarf = BedarfEntityConverter.convertBedarf(bedarf);
        convertedBedarf.setEntfernung(geocodingService.berechneUserDistanzInKilometer(convertedBedarf.getStandort()));
        return convertedBedarf;
    }

    ArtikelEntity getArtikel(//
                             final @NotNull @Valid ArtikelId artikelId) {
        Assert.notNull(artikelId, "ArtikelId sind null.");

        return artikelSucheService.findArtikel(artikelId).map(ArtikelEntityConverter::convertArtikel) //
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format(EXCEPTION_MSG_ARTIKEL_NICHT_GEFUNDEN, artikelId.getValue())));
    }

    ArtikelVariante getArtikelVariante(//
                                       final @NotNull @Valid ArtikelVarianteId artikelVarianteId) {
        Assert.notNull(artikelVarianteId, "ArtikelVarianteId sind null.");

        return artikelSucheService.findArtikelVariante(artikelVarianteId) //
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
