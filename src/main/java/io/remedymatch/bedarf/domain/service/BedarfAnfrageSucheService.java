package io.remedymatch.bedarf.domain.service;

import static io.remedymatch.bedarf.domain.service.BedarfAnfrageEntityConverter.convertAnfragen;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.remedymatch.bedarf.domain.model.BedarfAnfrage;
import io.remedymatch.bedarf.domain.model.BedarfAnfrageId;
import io.remedymatch.bedarf.infrastructure.BedarfAnfrageJpaRepository;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.institution.domain.InstitutionId;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Validated
@Service
public class BedarfAnfrageSucheService {

	private static final String EXCEPTION_MSG_BEDARF_ANFRAGE_NICHT_GEFUNDEN = "BedarfAnfrage mit diesem Id nicht gefunden. (Id: %s)";

	private final BedarfAnfrageJpaRepository anfrageRepository;

	@Transactional(readOnly = true)
	public List<BedarfAnfrage> findAlleAnfragenDerInstitution(final @NotNull @Valid InstitutionId institutionId) {
		return convertAnfragen(anfrageRepository.findAllByInstitution_Id(institutionId.getValue()));
	}

	@Transactional(readOnly = true)
	public List<BedarfAnfrage> findAlleAnfragenDerBedarfInstitution(
			final @NotNull @Valid InstitutionId institutionId) {
		return convertAnfragen(anfrageRepository.findAllByBedarf_Institution_Id(institutionId.getValue()));
	}

	/**
	 * In Vergleich zur findAnfrage wirft eine ObjectNotFoundException wenn nicht
	 * gefunden
	 * 
	 * @param anfrageId BedarfAnfrageId
	 * @return BedarfAnfrage 
	 * @throws ObjectNotFoundException
	 */
	@Transactional(readOnly = true)
	public BedarfAnfrage getAnfrageOrElseThrow(final @NotNull @Valid BedarfAnfrageId anfrageId)
			throws ObjectNotFoundException {
		return findAnfrage(anfrageId).orElseThrow(() -> new ObjectNotFoundException(
				String.format(EXCEPTION_MSG_BEDARF_ANFRAGE_NICHT_GEFUNDEN, anfrageId.getValue())));
	}

	@Transactional(readOnly = true)
	public Optional<BedarfAnfrage> findAnfrage(final @NotNull @Valid BedarfAnfrageId anfrageId) {
		return anfrageRepository.findById(anfrageId.getValue()).map(BedarfAnfrageEntityConverter::convertAnfrage);
	}
}
