package io.remedymatch.bedarf.infrastructure;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.remedymatch.bedarf.domain.anfrage.BedarfAnfrageStatus;


public interface BedarfAnfrageJpaRepository extends JpaRepository<BedarfAnfrageEntity, UUID> {

	List<BedarfAnfrageEntity> findAllByBedarf_Institution_Id(UUID id);
	
	List<BedarfAnfrageEntity> findAllByInstitutionVon_Id(UUID id);
	
	@Modifying
    @Query("UPDATE BedarfAnfrage a SET a.status=:statusNeu WHERE a.bedarf.id = :bedarfId AND a.status=:statusAlt")
    void updateStatus(//
    		@Param("bedarfId") UUID bedarfId, //
    		@Param("statusAlt") BedarfAnfrageStatus statusAlt, //
    		@Param("statusNeu") BedarfAnfrageStatus statusNeu);
}
