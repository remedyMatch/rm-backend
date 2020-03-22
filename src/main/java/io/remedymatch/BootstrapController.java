package io.remedymatch;

import io.remedymatch.artikel.domain.ArtikelEntity;
import io.remedymatch.artikel.domain.ArtikelJpaRepository;
import io.remedymatch.artikel.domain.ArtikelKategorieEntity;
import io.remedymatch.artikel.domain.ArtikelKategorieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;
import java.util.logging.Logger;

@RequestMapping(value = "/bootstrap")
@RequiredArgsConstructor
public class BootstrapController {
    Logger logger = Logger.getLogger(BootstrapController.class.getName());
    private final ArtikelJpaRepository artikelJpaRepository;
    private final ArtikelKategorieRepository artikelKategorieRepository;

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
