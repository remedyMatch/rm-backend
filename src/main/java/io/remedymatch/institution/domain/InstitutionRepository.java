package io.remedymatch.institution.domain;

import static io.remedymatch.institution.domain.InstitutionEntityConverter.convert;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import io.remedymatch.institution.infrastructure.InstitutionJpaRepository;

@Repository
public class InstitutionRepository {
	@Autowired
	private InstitutionJpaRepository jpaRepository;

	public Institution findByInstitutionKey(final String institutionKey)
	{
		Assert.isTrue(StringUtils.isNotBlank(institutionKey), "InstitutionKey ist blank");
		
		return convert(jpaRepository.findByInstitutionKey(institutionKey));
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
