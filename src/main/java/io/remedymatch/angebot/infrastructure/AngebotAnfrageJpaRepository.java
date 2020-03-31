package io.remedymatch.angebot.infrastructure;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface AngebotAnfrageJpaRepository extends CrudRepository<AngebotAnfrageEntity, UUID> {

	List<AngebotAnfrageEntity> findAllByInstitutionAn_Id(UUID id);

	List<AngebotAnfrageEntity> findAllByInstitutionVon_Id(UUID id);
}
