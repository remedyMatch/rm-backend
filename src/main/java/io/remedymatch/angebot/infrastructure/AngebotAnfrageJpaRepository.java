package io.remedymatch.angebot.infrastructure;

import io.remedymatch.angebot.domain.model.AngebotAnfrageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AngebotAnfrageJpaRepository extends JpaRepository<AngebotAnfrageEntity, UUID> {

    List<AngebotAnfrageEntity> findAllByAngebot_Institution_Id(final UUID institutionId);

    @Query("SELECT a FROM AngebotAnfrage a WHERE a.angebot.institution.id = :institutionId AND a.status='OFFEN'")
    List<AngebotAnfrageEntity> findAllByStatusOffenAndInstitution_Id(@Param("institutionId") final UUID institutionId);

    List<AngebotAnfrageEntity> findAllByInstitution_Id(final UUID institutionId);

    @Query("SELECT a FROM AngebotAnfrage a WHERE a.angebot.id IN (:angebotIds) AND a.status='OFFEN'")
    List<AngebotAnfrageEntity> findAllByAngebot_IdInAndOffen(@Param("angebotIds") final List<UUID> angebotIds);

    @Query("SELECT a FROM AngebotAnfrage a WHERE a.angebot.id IN (:angebotIds)")
    List<AngebotAnfrageEntity> findAllByAngebot_IdIn(@Param("angebotIds") final List<UUID> angebotIds);

    default Optional<AngebotAnfrageEntity> findByAngebotIdAndAnfrageIdAndStatusOffen(
            final UUID angebotId, //
            final UUID anfrageId) {
        return findByAngebot_IdAndIdAndStatus(angebotId, anfrageId, AngebotAnfrageStatus.OFFEN);
    }

    Optional<AngebotAnfrageEntity> findByAngebot_IdAndIdAndStatus(
            final UUID angebotId, //
            final UUID anfrageId, //
            final AngebotAnfrageStatus status);

    @Modifying
    @Query("UPDATE AngebotAnfrage a SET a.status=:statusNeu WHERE a.angebot.id = :angebotId AND a.status=:statusAlt")
    void updateStatus(//
                      @Param("angebotId") UUID angebotId, //
                      @Param("statusAlt") AngebotAnfrageStatus statusAlt, //
                      @Param("statusNeu") AngebotAnfrageStatus statusNeu);
}
