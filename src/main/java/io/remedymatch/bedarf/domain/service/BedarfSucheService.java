package io.remedymatch.bedarf.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.remedymatch.bedarf.domain.model.Bedarf;
import io.remedymatch.bedarf.infrastructure.BedarfEntity;
import io.remedymatch.bedarf.infrastructure.BedarfJpaRepository;
import io.remedymatch.geodaten.geocoding.domain.GeoCalcService;
import io.remedymatch.user.domain.UserService;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Validated
@Service
public class BedarfSucheService {

	private final BedarfJpaRepository bedarfRepository;
	
	private final UserService userService;
	private final GeoCalcService geoCalcService;

	@Transactional(readOnly = true)
	public List<Bedarf> findAlleNichtBedienteBedarfe() {
		return mitEntfernung(bedarfRepository.findAllByDeletedFalseAndBedientFalse());
	}

	@Transactional(readOnly = true)
	public List<Bedarf> findAlleNichtBedienteBedarfeDerUserInstitution() {
		val userInstitution = userService.getContextInstitution();
		return mitEntfernung(
				bedarfRepository.findAllByDeletedFalseAndBedientFalseAndInstitution_Id(userInstitution.getId().getValue()));
	}

	private List<Bedarf> mitEntfernung(final List<BedarfEntity> bedarfe) {
		return bedarfe.stream().map(this::mitEntfernung).collect(Collectors.toList());
	}

	private Bedarf mitEntfernung(final BedarfEntity bedarf) {
		val convertedBedarf = BedarfEntityConverter.convertBedarf(bedarf);
		convertedBedarf.setEntfernung(geoCalcService.berechneUserDistanzInKilometer(convertedBedarf.getStandort()));
		return convertedBedarf;
	}
}
