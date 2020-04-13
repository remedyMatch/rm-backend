package io.remedymatch.institution.domain.service;

import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.remedymatch.institution.domain.model.Institution;
import io.remedymatch.institution.infrastructure.InstitutionJpaRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Validated
@Service
public class InstitutionSucheService {

	private final InstitutionJpaRepository institutionRepository;

	@Transactional(readOnly = true)
	public Optional<Institution> findByInstitutionKey(final @NotBlank String institutionKey) {
		return institutionRepository.findByInstitutionKey(institutionKey)
				.map(InstitutionEntityConverter::convertInstitution);
	}
}
