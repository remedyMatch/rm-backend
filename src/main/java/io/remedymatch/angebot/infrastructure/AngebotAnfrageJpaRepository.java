package io.remedymatch.angebot.infrastructure;


import io.remedymatch.angebot.domain.anfrage.AngebotAnfrageEntity;
import io.remedymatch.institution.domain.InstitutionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AngebotAnfrageJpaRepository extends CrudRepository<AngebotAnfrageEntity, UUID> {

    List<AngebotAnfrageEntity> findAllByInstitutionAn(InstitutionEntity an);

    List<AngebotAnfrageEntity> findAllByInstitutionVon(InstitutionEntity von);

}


