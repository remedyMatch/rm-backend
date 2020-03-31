package io.remedymatch.angebot.domain;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import io.remedymatch.angebot.infrastructure.AngebotJpaRepository;

@Repository
public class AngebotRepository {

	@Autowired
	private AngebotJpaRepository jpaRepository;
	
	public List<AngebotEntity> getAlleNichtBedienteAngebote() {
		return jpaRepository.findAllByBedientFalse();
	}

	public Optional<AngebotEntity> get(UUID angebotId) {
		Assert.notNull(angebotId, "AngebotId ist null");

		return jpaRepository.findById(angebotId);
	}

	public AngebotEntity add(AngebotEntity angebot) {
		Assert.notNull(angebot, "Angebot ist null");

		return jpaRepository.save(angebot);
	}
	
	public AngebotEntity update(AngebotEntity angebot) {
		Assert.notNull(angebot, "Angebot ist null");

		return jpaRepository.save(angebot);
	}
	
	public void delete(UUID angebotId) {
		Assert.notNull(angebotId, "AngebotId ist null");

		jpaRepository.deleteById(angebotId);
	}
}
