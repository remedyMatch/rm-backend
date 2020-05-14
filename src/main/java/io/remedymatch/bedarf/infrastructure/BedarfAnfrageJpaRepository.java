package io.remedymatch.bedarf.infrastructure;

import io.remedymatch.bedarf.domain.model.BedarfAnfrageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BedarfAnfrageJpaRepository extends JpaRepository<BedarfAnfrageEntity, UUID> {

    List<BedarfAnfrageEntity> findAllByBedarf_Institution_Id(final UUID id);

    List<BedarfAnfrageEntity> findAllByInstitution_Id(final UUID id);

    @Query("SELECT a FROM BedarfAnfrage a WHERE a.bedarf.institution.id = :institutionId AND a.status='OFFEN'")
    List<BedarfAnfrageEntity> findAllByStatusOffenAndBedarfInstitution_Id(@Param("institutionId") final UUID institutionId);

    @Query("SELECT a FROM BedarfAnfrage a WHERE (a.bedarf.institution.id = :institutionId or a.institution.id = :institutionId) AND a.status='MATCHED'")
    List<BedarfAnfrageEntity> findAllByStatusMatchedAndInstitution_Id(@Param("institutionId") final UUID institutionId);

    @Query("SELECT a FROM BedarfAnfrage a WHERE a.bedarf.id IN (:bedarfIds) AND a.status='OFFEN'")
    List<BedarfAnfrageEntity> findAllByAngebot_IdIn(@Param("bedarfIds") final List<UUID> bedarfIds);

    default Optional<BedarfAnfrageEntity> findByBedarfIdAndAnfrageIdAndStatusOffen(
            final UUID bedarfId, //
            final UUID anfrageId) {
        return findByBedarf_IdAndIdAndStatus(bedarfId, anfrageId, BedarfAnfrageStatus.OFFEN);
    }

    Optional<BedarfAnfrageEntity> findByBedarf_IdAndIdAndStatus(
            final UUID bedarfId, //
            final UUID anfrageId, //
            final BedarfAnfrageStatus status);

    @Modifying
    @Query("UPDATE BedarfAnfrage a SET a.status=:statusNeu WHERE a.bedarf.id = :bedarfId AND a.status=:statusAlt")
    void updateStatus(
            @Param("bedarfId") UUID bedarfId, //
            @Param("statusAlt") BedarfAnfrageStatus statusAlt, //
            @Param("statusNeu") BedarfAnfrageStatus statusNeu);
}
