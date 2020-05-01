package io.remedymatch.institution.domain.service;

import static io.remedymatch.institution.domain.service.InstitutionAnfrageConverter.convertAngebotAnfragen;
import static io.remedymatch.institution.domain.service.InstitutionAnfrageConverter.convertBedarfAnfragen;

import java.util.ArrayList;
import java.util.List;

import io.remedymatch.geodaten.domain.GeocodingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.remedymatch.angebot.domain.service.AngebotAnfrageSucheService;
import io.remedymatch.bedarf.domain.service.BedarfAnfrageSucheService;
import io.remedymatch.institution.domain.model.InstitutionAnfrage;
import io.remedymatch.usercontext.UserContextService;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Validated
@Service
public class InstitutionAnfragenSucheService {

	private final UserContextService userService;
	private final AngebotAnfrageSucheService angebotAnfrageSucheService;
	private final BedarfAnfrageSucheService bedarfAnfrageSucheService;
	private final GeocodingService geocodingService;

	@Transactional(readOnly = true)
	public List<InstitutionAnfrage> findAlleGestellteUserInstitutionAnfragen() {
		val userInstitutionId = userService.getContextInstitutionId();

		List<InstitutionAnfrage> anfragen = new ArrayList<>();
		anfragen.addAll(
				convertAngebotAnfragen(angebotAnfrageSucheService.findAlleAnfragenDerInstitution(userInstitutionId)));
		anfragen.addAll(
				convertBedarfAnfragen(bedarfAnfrageSucheService.findAlleAnfragenDerInstitution(userInstitutionId)));

		return mitEntfernung(anfragen);
	}

	@Transactional(readOnly = true)
	public List<InstitutionAnfrage> findAlleErhalteneUserInstitutionAnfragen() {
		val userInstitutionId = userService.getContextInstitutionId();

		List<InstitutionAnfrage> anfragen = new ArrayList<>();
		anfragen.addAll(convertAngebotAnfragen(
				angebotAnfrageSucheService.findAlleAnfragenDerAngebotInstitution(userInstitutionId)));
		anfragen.addAll(convertBedarfAnfragen(
				bedarfAnfrageSucheService.findAlleAnfragenDerBedarfInstitution(userInstitutionId)));

		return mitEntfernung(anfragen);
	}

	private List<InstitutionAnfrage> mitEntfernung(final List<InstitutionAnfrage> anfragen) {
		anfragen.forEach(anfrage -> anfrage.setEntfernung(geocodingService.berechneDistanzInKilometer(//
				anfrage.getStandortVon(), //
				anfrage.getStandortAn())));

		return anfragen;
	}
}
