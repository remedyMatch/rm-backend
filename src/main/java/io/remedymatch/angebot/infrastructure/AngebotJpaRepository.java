package io.remedymatch.angebot.infrastructure;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface AngebotJpaRepository extends CrudRepository<AngebotEntity, UUID> {
	List<AngebotEntity> findAllByBedientFalse();

	List<AngebotEntity> findAllByInstitution_Id(UUID id);
}
