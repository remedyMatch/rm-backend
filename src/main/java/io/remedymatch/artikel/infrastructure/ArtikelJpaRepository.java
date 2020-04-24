package io.remedymatch.artikel.infrastructure;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArtikelJpaRepository extends JpaRepository<ArtikelEntity, UUID> {

	@Query("SELECT a FROM Artikel a " //
			+ "WHERE UPPER(a.name) LIKE CONCAT('%', UPPER(:name),'%')")
	List<ArtikelEntity> findByNameContainingIgnoreCase(@Param("name") final String namePart);
}
