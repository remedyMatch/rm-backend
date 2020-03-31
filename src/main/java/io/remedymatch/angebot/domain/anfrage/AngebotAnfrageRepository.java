package io.remedymatch.angebot.domain.anfrage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import io.remedymatch.angebot.infrastructure.AngebotAnfrageJpaRepository;

@Repository
public class AngebotAnfrageRepository {
	@Autowired
	private AngebotAnfrageJpaRepository jpaRepository;

	public Optional<AngebotAnfrageEntity> get(UUID angebotId) {
		Assert.notNull(angebotId, "AngebotId ist null");

		return jpaRepository.findById(angebotId);
	}

	public AngebotAnfrageEntity update(AngebotAnfrageEntity angebotAnfrage) {
		Assert.notNull(angebotAnfrage, "AngebotAnfrage ist null");

		return jpaRepository.save(angebotAnfrage);
	}
	
	public List<AngebotAnfrageEntity> getAngeboteFuerInstitutionVon(UUID institutionId) {
		Assert.notNull(institutionId, "InstitutionId ist null");

		return jpaRepository.findAllByInstitutionVon_Id(institutionId);
	}
	
	public List<AngebotAnfrageEntity> getAngeboteFuerInstitutionAn(UUID institutionId) {
		Assert.notNull(institutionId, "InstitutionId ist null");

		return jpaRepository.findAllByInstitutionAn_Id(institutionId);
	}
}
