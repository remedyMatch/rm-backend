package io.remedymatch.angebot.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AngebotJpaRepository extends JpaRepository<AngebotEntity, UUID> {
    List<AngebotEntity> findAllByDeletedFalseAndBedientFalseAndOeffentlichTrue();

    List<AngebotEntity> findAllByDeletedFalseAndBedientFalseAndOeffentlichTrueAndArtikelVariante_Id(final UUID id);

    List<AngebotEntity> findAllByDeletedFalseAndBedientFalseAndInstitution_Id(UUID id);

    @Query("SELECT new io.remedymatch.angebot.infrastructure.FilterEntry(ak.id as id, count(*) as anzahl) " //
            + "FROM Angebot a " //
            + "LEFT JOIN Artikel ar on ar.id = a.artikelVariante.artikel " //
            + "LEFT JOIN ArtikelKategorie ak on ak.id = ar.artikelKategorie " //
            + "WHERE a.institution.id <> :institutionId " //
            + "GROUP BY ak.id")
    List<FilterEntry> findAllKategorienMitUnbedientenAngebotenFilterOhneEigene(@Param("institutionId") UUID institutionId);

    @Query("SELECT new io.remedymatch.angebot.infrastructure.FilterEntry(ak.id as id, count(*) as anzahl) " //
            + "FROM Angebot a " //
            + "LEFT JOIN Artikel ar on ar.id = a.artikelVariante.artikel " //
            + "LEFT JOIN ArtikelKategorie ak on ak.id = ar.artikelKategorie " //
            + "GROUP BY ak.id")
    List<FilterEntry> findAllKategorienMitUnbedientenAngebotenFilter();

    @Query("SELECT new io.remedymatch.angebot.infrastructure.FilterEntry(ar.id as id, count(*) as anzahl) " //
            + "FROM Angebot a " //
            + "LEFT JOIN Artikel ar on (ar.id = a.artikelVariante.artikel and ar.artikelKategorie = :kategorieId) " //
            + "WHERE a.institution.id <> :institutionId " //
            + "GROUP BY ar.id")
    List<FilterEntry> findAllArtikelInKategorieMitUnbedientenAngebotenFilterOhneEigene(@Param("kategorieId") UUID kategorieId, @Param("institutionId") UUID institutionId);

    @Query("SELECT new io.remedymatch.angebot.infrastructure.FilterEntry(ar.id as id, count(*) as anzahl) " //
            + "FROM Angebot a " //
            + "LEFT JOIN Artikel ar on (ar.id = a.artikelVariante.artikel and ar.artikelKategorie = :kategorieId) " //
            + "GROUP BY ar.id")
    List<FilterEntry> findAllArtikelInKategorieMitUnbedientenAngebotenFilter(@Param("kategorieId") UUID kategorieId);

    @Query("SELECT new io.remedymatch.angebot.infrastructure.FilterEntry(a.artikelVariante.id as id, count(*) as anzahl) " //
            + "FROM Angebot a " //
            + "WHERE a.bedient = false and a.deleted = false and a.artikelVariante.artikel = :artikelId and a.institution.id <> :institutionId " //
            + "GROUP BY a.artikelVariante.id")
    List<FilterEntry> findAllArtikelVariantenInArtikelMitUnbedientenAngebotenFilterOhneEigene(@Param("artikelId") UUID artikelId, @Param("institutionId") UUID institutionId);

    @Query("SELECT new io.remedymatch.angebot.infrastructure.FilterEntry(a.artikelVariante.id as id, count(*) as anzahl) " //
            + "FROM Angebot a " //
            + "WHERE a.bedient = false and a.deleted = false and a.artikelVariante.artikel = :artikelId " //
            + "GROUP BY a.artikelVariante.id")
    List<FilterEntry> findAllArtikelVariantenInArtikelMitUnbedientenAngebotenFilter(@Param("artikelId") UUID artikelId);

}
