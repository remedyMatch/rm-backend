package io.remedymatch.institution.domain;

import io.remedymatch.institution.infrastructure.InstitutionJpaRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

import static io.remedymatch.institution.domain.InstitutionEntityConverter.convert;

@Repository
@Transactional
@AllArgsConstructor
public class InstitutionRepository {

    private final InstitutionJpaRepository jpaRepository;

    public Optional<Institution> findByInstitutionKey(final String institutionKey) {
        Assert.isTrue(StringUtils.isNotBlank(institutionKey), "InstitutionKey ist blank");

        return jpaRepository.findByInstitutionKey(institutionKey).map(InstitutionEntityConverter::convert);
    }

    public Optional<Institution> get(final InstitutionId institutionId) {
        Assert.notNull(institutionId, "InstitutionId ist null");
        Assert.notNull(institutionId.getValue(), "InstitutionId ist null");

        return jpaRepository.findById(institutionId.getValue()).map(InstitutionEntityConverter::convert);
    }

    public Institution add(final Institution institution) {
        Assert.notNull(institution, "institution ist null");

        return convert(jpaRepository.save(convert(institution)));
    }

    public Institution update(final Institution institution) {
        Assert.notNull(institution, "institution ist null");

        return convert(jpaRepository.save(convert(institution)));
    }

    public void delete(final InstitutionId institutionId) {
        Assert.notNull(institutionId, "InstitutionId ist null");
        Assert.notNull(institutionId.getValue(), "InstitutionId ist null");

        jpaRepository.deleteById(institutionId.getValue());
    }
}
