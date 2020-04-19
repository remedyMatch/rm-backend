package io.remedymatch.bedarf.infrastructure;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.remedymatch.bedarf.domain.model.BedarfAnfrageStatus;

public interface BedarfAnfrageJpaRepository extends JpaRepository<BedarfAnfrageEntity, UUID> {

	List<BedarfAnfrageEntity> findAllByBedarf_Institution_Id(final UUID id);

	List<BedarfAnfrageEntity> findAllByInstitution_Id(final UUID id);

	default Optional<BedarfAnfrageEntity> findByBedarfIdAndAnfrageIdAndStatusOffen(//
			final UUID bedarfId, //
			final UUID anfrageId) {
		return findByBedarf_IdAndIdAndStatus(bedarfId, anfrageId, BedarfAnfrageStatus.OFFEN);
	}

	Optional<BedarfAnfrageEntity> findByBedarf_IdAndIdAndStatus(//
			final UUID bedarfId, //
			final UUID anfrageId, //
			final BedarfAnfrageStatus status);

	@Modifying
	@Query("UPDATE BedarfAnfrage a SET a.status=:statusNeu WHERE a.bedarf.id = :bedarfId AND a.status=:statusAlt")
	void updateStatus(//
			@Param("bedarfId") UUID bedarfId, //
			@Param("statusAlt") BedarfAnfrageStatus statusAlt, //
			@Param("statusNeu") BedarfAnfrageStatus statusNeu);
}
