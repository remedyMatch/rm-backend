package io.remedymatch.bedarf.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BedarfJpaRepository extends JpaRepository<BedarfEntity, UUID> {
    List<BedarfEntity> findAllByDeletedFalseAndBedientFalseAndOeffentlichTrue();

    List<BedarfEntity> findAllByDeletedFalseAndBedientFalseAndOeffentlichTrueAndArtikelVariante_Id(final UUID id);

    List<BedarfEntity> findAllByDeletedFalseAndBedientFalseAndInstitution_Id(UUID id);

    @Query("SELECT new io.remedymatch.bedarf.infrastructure.FilterEntry(b.artikel.artikelKategorie as id, count(*) as anzahl) " //
            + "FROM Bedarf b " //
            + "WHERE b.deleted = false and b.bedient = false and b.institution.id <> :institutionId " //
            + "GROUP BY b.artikel.artikelKategorie")
    List<FilterEntry> countAllBedarfKategorienByDeletedFalseAndBedientFalseOhneEigene(@Param("institutionId") UUID institutionId);

    @Query("SELECT new io.remedymatch.bedarf.infrastructure.FilterEntry(b.artikel.artikelKategorie as id, count(*) as anzahl) " //
            + "FROM Bedarf b " //
            + "WHERE b.deleted = false and b.bedient = false " //
            + "GROUP BY b.artikel.artikelKategorie")
    List<FilterEntry> countAllBedarfKategorienByDeletedFalseAndBedientFalse();

    @Query("SELECT new io.remedymatch.bedarf.infrastructure.FilterEntry(b.artikel.id as id, count(*) as anzahl) " //
            + "FROM Bedarf b " //
            + "WHERE b.deleted = false and b.bedient = false and b.artikel.artikelKategorie = :kategorieId and b.institution.id <> :institutionId " //
            + "GROUP BY b.artikel.id")
    List<FilterEntry> countAllBedarfArtikelByDeletedFalseAndBedientFalseAndArtikel_ArtikelKategorieOhneEigene(
            @Param("kategorieId") UUID kategorieId,
            @Param("institutionId") UUID institutionId);

    @Query("SELECT new io.remedymatch.bedarf.infrastructure.FilterEntry(b.artikel.id as id, count(*) as anzahl) " //
            + "FROM Bedarf b " //
            + "WHERE b.deleted = false and b.bedient = false and b.artikel.artikelKategorie = :kategorieId " //
            + "GROUP BY b.artikel.id")
    List<FilterEntry> countAllBedarfArtikelByDeletedFalseAndBedientFalseAndArtikel_ArtikelKategorie(@Param("kategorieId") UUID kategorieId);

    @Query("SELECT new io.remedymatch.bedarf.infrastructure.FilterEntry(b.artikelVariante.id as id, count(*) as anzahl) " //
            + "FROM Bedarf b " //
            + "WHERE b.deleted = false and b.bedient = false and b.artikel.id = :artikelId and b.institution.id <> :institutionId " //
            + "GROUP BY b.artikelVariante.id")
    List<FilterEntry> countAllBedarfArtikelVariantenByDeletedFalseAndBedientFalseAndArtikel_IdOhneEigene(
            @Param("artikelId") UUID artikelId,
            @Param("institutionId") UUID institutionId);

    @Query("SELECT new io.remedymatch.bedarf.infrastructure.FilterEntry(b.artikelVariante.id as id, count(*) as anzahl) " //
            + "FROM Bedarf b " //
            + "WHERE b.deleted = false and b.bedient = false and b.artikel.id = :artikelId " //
            + "GROUP BY b.artikelVariante.id")
    List<FilterEntry> countAllBedarfArtikelVariantenByDeletedFalseAndBedientFalseAndArtikel_Id(@Param("artikelId") UUID artikelId);
}
