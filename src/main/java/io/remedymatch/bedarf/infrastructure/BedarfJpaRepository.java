package io.remedymatch.bedarf.infrastructure;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BedarfJpaRepository extends JpaRepository<BedarfEntity, UUID> {
	List<BedarfEntity> findAllByBedientFalse();

	List<BedarfEntity> findAllByInstitution_Id(UUID id);
}
