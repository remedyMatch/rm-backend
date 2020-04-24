package io.remedymatch.institution.domain.service;

import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.remedymatch.institution.domain.model.Institution;
import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.institution.infrastructure.InstitutionJpaRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Validated
@Service
public class InstitutionSucheService {

	private final InstitutionJpaRepository institutionRepository;

	@Transactional(readOnly = true)
	public Optional<Institution> findInstitution(final @NotNull @Valid InstitutionId institutionId) {
		return institutionRepository.findById(institutionId.getValue())
				.map(InstitutionEntityConverter::convertInstitution);
	}
}
