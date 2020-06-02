package io.remedymatch.nachricht.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface KonversationJpaRepository extends JpaRepository<KonversationEntity, UUID> {

    @Query("SELECT k FROM Konversation k " //
            + "LEFT JOIN k.beteiligte b " //
            + "WHERE b.institution.id = :institutionId ")
    List<KonversationEntity> findAllByInstitutionId(@Param("institutionId") UUID institutionId);


    @Query("SELECT k FROM Konversation k " //
            + "LEFT JOIN k.beteiligte b " //
            + "WHERE b.institution.id = :institutionId AND k.id = :konversationId")
    Optional<KonversationEntity> findByIdAndInstitutionId(@Param("konversationId") UUID konversationId, @Param("institutionId") UUID institutionId);
}
