package io.remedymatch;

import java.time.Instant;
import java.util.logging.Logger;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.remedymatch.artikel.infrastructure.ArtikelEntity;
import io.remedymatch.artikel.infrastructure.ArtikelJpaRepository;
import io.remedymatch.artikel.infrastructure.ArtikelKategorieEntity;
import io.remedymatch.artikel.infrastructure.ArtikelKategorieJpaRepository;
import lombok.RequiredArgsConstructor;

@RequestMapping(value = "/bootstrap")
@RequiredArgsConstructor
public class BootstrapController {
    Logger logger = Logger.getLogger(BootstrapController.class.getName());
    private final ArtikelJpaRepository artikelJpaRepository;
    private final ArtikelKategorieJpaRepository artikelKategorieRepository;

    @GetMapping
    public String ping() {
        return Instant.now().toString();
    }

    @PostMapping("/testdata")
    @Transactional
    public void addTestdata() {
        var kategorie = artikelKategorieRepository.save(
                ArtikelKategorieEntity.builder()
                .name("kategorie-1")
                .build()
        );

        artikelJpaRepository.save(
                ArtikelEntity.builder()
                .name("beispiel")
                .ean("beispiel-ean")
                .hersteller("beispiel-hersteller")
                .beschreibung("beispiel-beschreibung")
                .artikelKategorie(kategorie)
                .build()
        );
        logger.info( "Testdata created");
    }
}
