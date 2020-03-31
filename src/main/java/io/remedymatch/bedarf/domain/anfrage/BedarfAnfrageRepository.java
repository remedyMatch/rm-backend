package io.remedymatch.bedarf.domain.anfrage;


import io.remedymatch.institution.domain.InstitutionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BedarfAnfrageRepository extends CrudRepository<BedarfAnfrageEntity, UUID> {

    List<BedarfAnfrageEntity> findAllByInstitutionAn(InstitutionEntity an);

    List<BedarfAnfrageEntity> findAllByInstitutionVon(InstitutionEntity von);

}


