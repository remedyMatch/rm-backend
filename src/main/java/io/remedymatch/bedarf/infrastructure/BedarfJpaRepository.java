package io.remedymatch.bedarf.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BedarfJpaRepository extends JpaRepository<BedarfEntity, UUID> {
    List<BedarfEntity> findAllByDeletedFalseAndBedientFalse();

    List<BedarfEntity> findAllByDeletedFalseAndBedientFalseAndInstitution_Id(UUID id);
}
