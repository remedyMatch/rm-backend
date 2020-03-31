package io.remedymatch.angebot.infrastructure;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import io.remedymatch.angebot.domain.anfrage.AngebotAnfrageEntity;
import io.remedymatch.institution.domain.InstitutionEntity;

public interface AngebotAnfrageJpaRepository extends CrudRepository<AngebotAnfrageEntity, UUID> {
	List<AngebotAnfrageEntity> findAllByInstitutionAn(InstitutionEntity an);

	List<AngebotAnfrageEntity> findAllByInstitutionVon(InstitutionEntity von);
}
