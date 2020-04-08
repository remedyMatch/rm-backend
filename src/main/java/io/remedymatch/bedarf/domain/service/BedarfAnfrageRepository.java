package io.remedymatch.bedarf.domain.service;

import static io.remedymatch.bedarf.domain.service.BedarfAnfrageEntityConverter.convert;
import static io.remedymatch.bedarf.domain.service.BedarfAnfrageEntityConverter.convertAnfrage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import io.remedymatch.bedarf.domain.model.BedarfAnfrage;
import io.remedymatch.bedarf.domain.model.BedarfAnfrageId;
import io.remedymatch.bedarf.domain.model.BedarfAnfrageStatus;
import io.remedymatch.bedarf.domain.model.BedarfId;
import io.remedymatch.bedarf.infrastructure.BedarfAnfrageJpaRepository;
import io.remedymatch.institution.domain.InstitutionId;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
@Transactional
public class BedarfAnfrageRepository {


    private final BedarfAnfrageJpaRepository jpaRepository;

    public List<BedarfAnfrage> getAnfragenFuerInstitutionVon(final InstitutionId institutionId) {
        Assert.notNull(institutionId, "InstitutionId ist null");
        Assert.notNull(institutionId.getValue(), "InstitutionId ist null");

        return jpaRepository.findAllByInstitutionVon_Id(institutionId.getValue()).stream()//
                .map(BedarfAnfrageEntityConverter::convertAnfrage)//
                .collect(Collectors.toList());
    }

    public List<BedarfAnfrage> getAnfragenFuerInstitutionAn(final InstitutionId institutionId) {
        Assert.notNull(institutionId, "InstitutionId ist null");
        Assert.notNull(institutionId.getValue(), "InstitutionId ist null");

        return jpaRepository.findAllByBedarf_Institution_Id(institutionId.getValue()).stream()//
                .map(BedarfAnfrageEntityConverter::convertAnfrage)//
                .collect(Collectors.toList());
    }

    public void storniereAlleOffeneAnfragen(final BedarfId bedarfId) {
        Assert.notNull(bedarfId, "BedarfId ist null");
        Assert.notNull(bedarfId.getValue(), "BedarfId ist null");

        jpaRepository.updateStatus(bedarfId.getValue(), BedarfAnfrageStatus.Offen, BedarfAnfrageStatus.Storniert);
    }

    public Optional<BedarfAnfrage> get(final BedarfAnfrageId bedarfAnfrageId) {
        Assert.notNull(bedarfAnfrageId, "BedarfAnfrageId ist null");
        Assert.notNull(bedarfAnfrageId.getValue(), "BedarfAnfrageId ist null");

        return jpaRepository.findById(bedarfAnfrageId.getValue()).map(BedarfAnfrageEntityConverter::convertAnfrage);
    }

    public BedarfAnfrage add(final BedarfAnfrage bedarfAnfrage) {
        Assert.notNull(bedarfAnfrage, "BedarfAnfrage ist null");

        return convertAnfrage(jpaRepository.save(convert(bedarfAnfrage)));
    }

    public BedarfAnfrage update(final BedarfAnfrage bedarfAnfrage) {
        Assert.notNull(bedarfAnfrage, "BedarfAnfrage ist null");

        return convertAnfrage(jpaRepository.save(convert(bedarfAnfrage)));
    }
}
