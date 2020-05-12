package io.remedymatch.nachricht.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NachrichtJpaRepository extends JpaRepository<NachrichtEntity, UUID> {

    List<NachrichtEntity> findAllByReferenzId(UUID referenzId);
}
