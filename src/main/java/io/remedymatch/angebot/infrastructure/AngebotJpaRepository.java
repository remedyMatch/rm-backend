package io.remedymatch.angebot.infrastructure;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AngebotJpaRepository extends JpaRepository<AngebotEntity, UUID> {
    List<AngebotEntity> findAllByDeletedFalseAndBedientFalse();

    List<AngebotEntity> findAllByDeletedFalseAndBedientFalseAndInstitution_Id(UUID id);

    @Query("SELECT new io.remedymatch.angebot.infrastructure.FilterEntry(ak.id as id, count(*) as anzahl) " //
    		+ "FROM Angebot a " //
    		+ "LEFT JOIN Artikel ar on ar.id = a.artikelVariante.artikel " //
    		+ "LEFT JOIN ArtikelKategorie ak on ak.id = ar.artikelKategorie " //
    		+ "GROUP BY ak.id")
    List<FilterEntry> findAllKategorienMitUnbedientenAnbebotenFilter();
    
    @Query("SELECT new io.remedymatch.angebot.infrastructure.FilterEntry(ar.id as id, count(*) as anzahl) " //
    		+ "FROM Angebot a " //
    		+ "LEFT JOIN Artikel ar on (ar.id = a.artikelVariante.artikel and ar.artikelKategorie = :kategorieId) " //
    		+ "GROUP BY ar.id")
    List<FilterEntry> findAllArtikelInKagegorieMitUnbedientenAnbebotenFilter(@Param("kategorieId") UUID kategorieId);
}
