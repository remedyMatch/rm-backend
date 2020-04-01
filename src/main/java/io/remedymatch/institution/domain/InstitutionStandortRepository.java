package io.remedymatch.institution.domain;


import static io.remedymatch.institution.domain.InstitutionStandortEntityConverter.convert;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import io.remedymatch.angebot.domain.AngebotId;
import io.remedymatch.institution.domain.infrastructure.InstitutionStandortJpaRepository;

@Repository
public class InstitutionStandortRepository {
	@Autowired
	private InstitutionStandortJpaRepository jpaRepository;
	
	public Optional<InstitutionStandort> get(final InstitutionStandortId institutionStandortId) {
		Assert.notNull(institutionStandortId, "InstitutionStandortId ist null");
		Assert.notNull(institutionStandortId.getValue(), "InstitutionStandortId ist null");

		return jpaRepository.findById(institutionStandortId.getValue()).map(InstitutionStandortEntityConverter::convert);
	}

	public InstitutionStandort add(final InstitutionStandort institutionStandort) {
		Assert.notNull(institutionStandort, "institutionStandort ist null");

		return convert(jpaRepository.save(convert(institutionStandort)));
	}
	
	public InstitutionStandort update(final InstitutionStandort institutionStandort) {
		Assert.notNull(institutionStandort, "institutionStandort ist null");

		return convert(jpaRepository.save(convert(institutionStandort)));
	}
	
	public void delete(final InstitutionStandortId institutionStandortId) {
		Assert.notNull(institutionStandortId, "InstitutionStandortId ist null");
		Assert.notNull(institutionStandortId.getValue(), "InstitutionStandortId ist null");

		jpaRepository.deleteById(institutionStandortId.getValue());
	}
}
