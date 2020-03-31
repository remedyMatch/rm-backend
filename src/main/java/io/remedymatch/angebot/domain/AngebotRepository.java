package io.remedymatch.angebot.domain;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import io.remedymatch.angebot.infrastructure.AngebotJpaRepository;
import io.remedymatch.institution.domain.InstitutionId;

@Repository
public class AngebotRepository {

	@Autowired
	private AngebotJpaRepository jpaRepository;

	public List<Angebot> getAlleNichtBedienteAngebote() {
		return jpaRepository.findAllByBedientFalse().stream().map(AngebotEntityConverter::convert)
				.collect(Collectors.toList());
	}

	public List<Angebot> getAngeboteVonInstitution(final InstitutionId institutionId) {
		Assert.notNull(institutionId, "InstitutionId ist null");
		Assert.notNull(institutionId.getValue(), "InstitutionId ist null");

		return jpaRepository.findAllByInstitution_Id(institutionId.getValue()).stream()//
				.map(AngebotEntityConverter::convert)//
				.collect(Collectors.toList());
	}

	public Optional<Angebot> get(final AngebotId angebotId) {
		Assert.notNull(angebotId, "AngebotId ist null");
		Assert.notNull(angebotId.getValue(), "AngebotId ist null");

		return jpaRepository.findById(angebotId.getValue()).map(AngebotEntityConverter::convert);
	}

	public Angebot add(final Angebot angebot) {
		Assert.notNull(angebot, "Angebot ist null");

		return AngebotEntityConverter.convert(jpaRepository.save(AngebotEntityConverter.convert(angebot)));
	}

	public Angebot update(final Angebot angebot) {
		Assert.notNull(angebot, "Angebot ist null");

		return AngebotEntityConverter.convert(jpaRepository.save(AngebotEntityConverter.convert(angebot)));
	}

	public void delete(final AngebotId angebotId) {
		Assert.notNull(angebotId, "AngebotId ist null");
		Assert.notNull(angebotId.getValue(), "AngebotId ist null");

		jpaRepository.deleteById(angebotId.getValue());
	}
}
