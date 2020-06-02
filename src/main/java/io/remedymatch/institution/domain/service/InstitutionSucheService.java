package io.remedymatch.institution.domain.service;

import static io.remedymatch.institution.domain.service.InstitutionEntityConverter.convertInstitution;

import java.util.function.Supplier;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.institution.domain.model.Institution;
import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Validated
@Service
@Log4j2
public class InstitutionSucheService {

	private static final String EXCEPTION_MSG_INSTITUTION_NICHT_GEFUNDEN = "Institution mit dieser Id nicht gefunden. (Id: %s)";

	private final InstitutionJpaRepository institutionRepository;

	@PersistenceContext
	private final EntityManager entityManager;

	/**
	 * In Vergleich zur findInstitution wirft eine ObjectNotFoundException wenn
	 * nicht gefunden
	 * 
	 * @param institutionId InstitutionId
	 * @return Institution
	 * @throws ObjectNotFoundException
	 */
	@Transactional(readOnly = true)
	public Institution getByInstitutionId(final @NotNull @Valid InstitutionId institutionId)
			throws ObjectNotFoundException {

		log.info("Get Institution: " + institutionId);

		try {
			return detachAndConvert(() -> institutionRepository.getOne(institutionId.getValue()));
		} catch (EntityNotFoundException e) {
			throw new ObjectNotFoundException(
					String.format(EXCEPTION_MSG_INSTITUTION_NICHT_GEFUNDEN, institutionId.getValue()));
		}
	}

	private Institution detachAndConvert(Supplier<InstitutionEntity> supplier) {
		InstitutionEntity institutionEntity = supplier.get();
		Institution converted = convertInstitution(institutionEntity);
		entityManager.detach(institutionEntity);

		return converted;
	}
}
