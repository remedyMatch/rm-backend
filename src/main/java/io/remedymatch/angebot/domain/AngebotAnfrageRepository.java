package io.remedymatch.angebot.domain;

import static io.remedymatch.angebot.domain.AngebotAnfrageEntityConverter.convert;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import io.remedymatch.angebot.infrastructure.AngebotAnfrageJpaRepository;
import io.remedymatch.institution.domain.InstitutionId;

@Repository
public class AngebotAnfrageRepository {
	@Autowired
	private AngebotAnfrageJpaRepository jpaRepository;
	
	public List<AngebotAnfrage> getAngeboteFuerInstitutionVon(InstitutionId institutionId) {
		Assert.notNull(institutionId, "InstitutionId ist null");
		Assert.notNull(institutionId.getValue(), "InstitutionId ist null");

		return jpaRepository.findAllByInstitutionVon_Id(institutionId.getValue()).stream()//
				.map(AngebotAnfrageEntityConverter::convert)//
				.collect(Collectors.toList());
	}
	
	public List<AngebotAnfrage> getAngeboteFuerInstitutionAn(InstitutionId institutionId) {
		Assert.notNull(institutionId, "InstitutionId ist null");
		Assert.notNull(institutionId.getValue(), "InstitutionId ist null");

		return jpaRepository.findAllByInstitutionAn_Id(institutionId.getValue()).stream()//
				.map(AngebotAnfrageEntityConverter::convert)//
				.collect(Collectors.toList());
	}
	
	public Optional<AngebotAnfrage> get(UUID angebotAnfrageId) {
		Assert.notNull(angebotAnfrageId, "AngebotAnfrageId ist null");

		return jpaRepository.findById(angebotAnfrageId).map(AngebotAnfrageEntityConverter::convert);
	}

	public AngebotAnfrage update(AngebotAnfrage angebotAnfrage) {
		Assert.notNull(angebotAnfrage, "AngebotAnfrage ist null");

		return convert(jpaRepository.save(convert(angebotAnfrage)));
	}
}
