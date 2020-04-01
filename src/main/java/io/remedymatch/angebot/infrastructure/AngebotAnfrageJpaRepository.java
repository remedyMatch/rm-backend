package io.remedymatch.angebot.infrastructure;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import io.remedymatch.angebot.domain.AngebotAnfrageStatus;

public interface AngebotAnfrageJpaRepository extends CrudRepository<AngebotAnfrageEntity, UUID> {

	List<AngebotAnfrageEntity> findAllByInstitutionAn_Id(UUID id);

	List<AngebotAnfrageEntity> findAllByInstitutionVon_Id(UUID id);
	
	@Modifying
    @Query("UPDATE AngebotAnfrageEntity a SET a.status=:statusNeu WHERE a.angebot.id = :angebotId AND a.status=:statusAlt")
    void updateStatus(//
    		@Param("angebotId") UUID angebotId, //
    		@Param("statusAlt") AngebotAnfrageStatus statusAlt, //
    		@Param("statusNeu") AngebotAnfrageStatus statusNeu);
}
