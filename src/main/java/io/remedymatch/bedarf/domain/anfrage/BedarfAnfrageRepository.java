package io.remedymatch.bedarf.domain.anfrage;


import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.remedymatch.institution.infrastructure.InstitutionEntity;

@Repository
public interface BedarfAnfrageRepository extends CrudRepository<BedarfAnfrageEntity, UUID> {

    List<BedarfAnfrageEntity> findAllByInstitutionAn(InstitutionEntity an);

    List<BedarfAnfrageEntity> findAllByInstitutionVon(InstitutionEntity von);

}


