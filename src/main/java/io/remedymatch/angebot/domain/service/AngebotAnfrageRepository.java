package io.remedymatch.angebot.domain.service;

import static io.remedymatch.angebot.domain.service.AngebotAnfrageEntityConverter.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import io.remedymatch.angebot.domain.model.AngebotAnfrage;
import io.remedymatch.angebot.domain.model.AngebotAnfrageId;
import io.remedymatch.angebot.domain.model.AngebotAnfrageStatus;
import io.remedymatch.angebot.domain.model.AngebotId;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageJpaRepository;
import io.remedymatch.institution.domain.InstitutionId;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
@Transactional
public class AngebotAnfrageRepository {

	private AngebotAnfrageJpaRepository jpaRepository;

	public List<AngebotAnfrage> getAnfragenFuerInstitutionVon(final InstitutionId institutionId) {
		Assert.notNull(institutionId, "InstitutionId ist null");
		Assert.notNull(institutionId.getValue(), "InstitutionId ist null");

		return jpaRepository.findAllByInstitutionVon_Id(institutionId.getValue()).stream()//
				.map(AngebotAnfrageEntityConverter::convertAnfrage)//
				.collect(Collectors.toList());
	}

	public List<AngebotAnfrage> getAnfragenFuerInstitutionAn(final InstitutionId institutionId) {
		Assert.notNull(institutionId, "InstitutionId ist null");
		Assert.notNull(institutionId.getValue(), "InstitutionId ist null");

		return jpaRepository.findAllByAngebot_Institution_Id(institutionId.getValue()).stream()//
				.map(AngebotAnfrageEntityConverter::convertAnfrage)//
				.collect(Collectors.toList());
	}

	public void storniereAlleOffeneAnfragen(final AngebotId angebotId) {
		Assert.notNull(angebotId, "AngebotId ist null");
		Assert.notNull(angebotId.getValue(), "AngebotId ist null");

		jpaRepository.updateStatus(angebotId.getValue(), AngebotAnfrageStatus.Offen, AngebotAnfrageStatus.Storniert);
	}

	public Optional<AngebotAnfrage> get(final AngebotAnfrageId angebotAnfrageId) {
		Assert.notNull(angebotAnfrageId, "AngebotAnfrageId ist null");
		Assert.notNull(angebotAnfrageId.getValue(), "AngebotAnfrageId ist null");

		return jpaRepository.findById(angebotAnfrageId.getValue()).map(AngebotAnfrageEntityConverter::convertAnfrage);
	}

	public AngebotAnfrage add(final AngebotAnfrage angebotAnfrage) {
		Assert.notNull(angebotAnfrage, "AngebotAnfrage ist null");

		return convertAnfrage(jpaRepository.save(convert(angebotAnfrage)));
	}

	public AngebotAnfrage update(final AngebotAnfrage angebotAnfrage) {
		Assert.notNull(angebotAnfrage, "AngebotAnfrage ist null");

		return convertAnfrage(jpaRepository.save(convert(angebotAnfrage)));
	}
}
