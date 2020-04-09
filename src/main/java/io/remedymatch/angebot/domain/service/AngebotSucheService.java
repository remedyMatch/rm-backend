package io.remedymatch.angebot.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.remedymatch.angebot.domain.model.Angebot;
import io.remedymatch.angebot.infrastructure.AngebotEntity;
import io.remedymatch.angebot.infrastructure.AngebotJpaRepository;
import io.remedymatch.geodaten.geocoding.domain.GeoCalcService;
import io.remedymatch.user.domain.UserService;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Validated
@Service
public class AngebotSucheService {

	private final AngebotJpaRepository angebotRepository;
	
	private final UserService userService;
	private final GeoCalcService geoCalcService;

	@Transactional(readOnly = true)
	public List<Angebot> findAlleNichtBedienteAngebote() {
		return mitEntfernung(angebotRepository.findAllByDeletedFalseAndBedientFalse());
	}

	@Transactional(readOnly = true)
	public List<Angebot> findAlleNichtBedienteAngeboteDerUserInstitution() {
		val userInstitution = userService.getContextInstitution();
		return mitEntfernung(
				angebotRepository.findAllByDeletedFalseAndBedientFalseAndInstitution_Id(userInstitution.getId().getValue()));
	}

	private List<Angebot> mitEntfernung(final List<AngebotEntity> angebote) {
		return angebote.stream().map(this::mitEntfernung).collect(Collectors.toList());
	}

	private Angebot mitEntfernung(final AngebotEntity angebot) {
		val convertedAngebot = AngebotEntityConverter.convertAngebot(angebot);
		convertedAngebot.setEntfernung(geoCalcService.berechneUserDistanzInKilometer(convertedAngebot.getStandort()));
		return convertedAngebot;
	}
}
