package io.remedymatch.angebot.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AngebotJpaRepository extends JpaRepository<AngebotEntity, UUID> {
    List<AngebotEntity> findAllByDeletedFalseAndBedientFalse();

    List<AngebotEntity> findAllByDeletedFalseAndInstitution_Id(UUID id);
}
