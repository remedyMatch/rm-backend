package io.remedymatch.anfrage.domain;


import io.remedymatch.institution.domain.InstitutionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnfrageRepository extends CrudRepository<AnfrageEntity, UUID> {

    AnfrageEntity findByProzessInstanzId(String prozessInstanzId);

    List<AnfrageEntity> findAllByInstitutionAn(InstitutionEntity an);

    List<AnfrageEntity> findAllByInstitutionVon(InstitutionEntity von);

}


