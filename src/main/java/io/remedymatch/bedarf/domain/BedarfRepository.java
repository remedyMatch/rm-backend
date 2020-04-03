package io.remedymatch.bedarf.domain;

import io.remedymatch.bedarf.infrastructure.BedarfJpaRepository;
import io.remedymatch.institution.domain.InstitutionId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.remedymatch.bedarf.domain.BedarfEntityConverter.convert;

@Repository
@AllArgsConstructor
@Transactional
public class BedarfRepository {

    private final BedarfJpaRepository jpaRepository;

    public List<Bedarf> getAlleNichtBedienteBedarfe() {
        return jpaRepository.findAllByBedientFalse().stream().map(BedarfEntityConverter::convert)
                .collect(Collectors.toList());
    }

    public List<Bedarf> getBedarfeVonInstitution(final InstitutionId institutionId) {
        Assert.notNull(institutionId, "InstitutionId ist null");
        Assert.notNull(institutionId.getValue(), "InstitutionId ist null");

        return jpaRepository.findAllByInstitution_Id(institutionId.getValue()).stream()//
                .map(BedarfEntityConverter::convert)//
                .collect(Collectors.toList());
    }

    public Optional<Bedarf> get(final BedarfId bedarfId) {
        Assert.notNull(bedarfId, "BedarfId ist null");
        Assert.notNull(bedarfId.getValue(), "BedarfId ist null");

        return jpaRepository.findById(bedarfId.getValue()).map(BedarfEntityConverter::convert);
    }

    public boolean has(final BedarfId bedarfId) {
        Assert.notNull(bedarfId, "BedarfId ist null");
        Assert.notNull(bedarfId.getValue(), "BedarfId ist null");

        return jpaRepository.existsById(bedarfId.getValue());
    }

    public Bedarf add(final Bedarf bedarf) {
        Assert.notNull(bedarf, "Bedarf ist null");

        return convert(jpaRepository.save(convert(bedarf)));
    }

    public Bedarf update(final Bedarf bedarf) {
        Assert.notNull(bedarf, "Bedarf ist null");

        return convert(jpaRepository.save(convert(bedarf)));
    }

    public void delete(final BedarfId bedarfId) {
        Assert.notNull(bedarfId, "BedarfId ist null");
        Assert.notNull(bedarfId.getValue(), "BedarfId ist null");

        jpaRepository.deleteById(bedarfId.getValue());
    }
}
