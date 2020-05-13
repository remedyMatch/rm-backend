package io.remedymatch.nachricht.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NachrichtJpaRepository extends JpaRepository<NachrichtEntity, UUID> {

}
