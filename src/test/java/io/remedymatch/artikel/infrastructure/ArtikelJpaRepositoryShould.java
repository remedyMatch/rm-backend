package io.remedymatch.artikel.infrastructure;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.TestApplication;
import io.remedymatch.artikel.domain.model.ArtikelKategorieId;
import lombok.val;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class)
@DirtiesContext
@ActiveProfiles("test")
@Tag("InMemory")
@Tag("SpringBoot")
@DisplayName("ArtikelJpaRepository soll")
public class ArtikelJpaRepositoryShould {
    @Autowired
    private ArtikelJpaRepository jpaRepository;

    @Test
    @Transactional
    @Rollback(true)
    @DisplayName("Artikel zurueckliefern, wenn vorhanden")
    public void artikel_zurueckliefern_wenn_vorhanden() {

    	val artikelKategorieId = randomArtikelKategorieId();
    	
    	val article = artikel(artikelKategorieId, "Mein Artikel");
        val id = jpaRepository.save(article).getId();

        val expectedArtikel = artikel(artikelKategorieId, "Mein Artikel");
        expectedArtikel.setId(id);
        
        assertEquals(Optional.of(expectedArtikel), jpaRepository.findById(id));
    }
    
    @Test
    @Transactional
    @Rollback(true)
    @Disabled
    @DisplayName("Artikel anhand Teil von Name suchen koennen")
    public void artikel_anhand_Teil_von_Name_suchen_koennen() {

    	val artikelKategorieId = randomArtikelKategorieId();
    	
        val artikel1Id = jpaRepository.save(artikel(artikelKategorieId, "Mein 1 Artikel")).getId();
        val artikel2Id = jpaRepository.save(artikel(artikelKategorieId, "MEIN 2 Artikel")).getId();
        val artikel3Id = jpaRepository.save(artikel(artikelKategorieId, "Mein 3 ARTIKEL")).getId();
        jpaRepository.save(artikel(artikelKategorieId, " Mei n 1 AR TIKEL ")).getId();

        val expectedArtikel1 = artikel(artikelKategorieId, "Mein 1 Artikel");
        expectedArtikel1.setId(artikel1Id);
        val expectedArtikel2 = artikel(artikelKategorieId, "MEIN 2 Artikel");
        expectedArtikel2.setId(artikel2Id);
        val expectedArtikel3 = artikel(artikelKategorieId, "Mein 3 ARTIKEL");
        expectedArtikel3.setId(artikel3Id);
        
        List<ArtikelEntity> findByNameContainingIgnoreCase = jpaRepository.findByNameContainingIgnoreCase("Mein");
        
        assertEquals(3, findByNameContainingIgnoreCase.size());
        
		assertThat(//
        		findByNameContainingIgnoreCase, //
				containsInAnyOrder(expectedArtikel1, expectedArtikel2, expectedArtikel3));

        assertThat(//
        		jpaRepository.findByNameContainingIgnoreCase("Artikel"), //
				containsInAnyOrder(expectedArtikel1, expectedArtikel2, expectedArtikel3));

        assertThat(//
        		jpaRepository.findByNameContainingIgnoreCase("eIn"), //
				containsInAnyOrder(expectedArtikel1, expectedArtikel2, expectedArtikel3));
    }

    private ArtikelKategorieId randomArtikelKategorieId() {
		return new ArtikelKategorieId(UUID.randomUUID());
	}
    
    private ArtikelEntity artikel(//
    		final ArtikelKategorieId artikelKategorieId, //
    		final String name) {
        return ArtikelEntity.builder() //
        		.artikelKategorie(artikelKategorieId.getValue()) //
                .name(name) //
                .beschreibung("Sample Beschreibung") //
                .build();
    }
}
