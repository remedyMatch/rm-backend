package io.remedymatch.angebot.infrastructure;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AngebotJpaRepository extends JpaRepository<AngebotEntity, UUID> {
	List<AngebotEntity> findAllByBedientFalse();

	List<AngebotEntity> findAllByInstitution_Id(UUID id);
}
