package io.remedymatch.match.domain;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.remedymatch.angebot.domain.AngebotAnfrage;
import io.remedymatch.bedarf.domain.BedarfAnfrage;
import io.remedymatch.geodaten.geocoding.domain.GeoCalcService;
import io.remedymatch.user.domain.UserService;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Component
public class MatchService {

	private final MatchRepository matchRepository;
	private final MatchStandortRepository matchStandortRepository;
	private final UserService userService;
	private final GeoCalcService geoCalcService;

	@Transactional
	public Match matchAusBedarfErstellen(BedarfAnfrage bedarfAnfrage) {
		val match = new Match();

		match.setInstitutionAn(bedarfAnfrage.getInstitutionVon());
		match.setStandortAn(MatchStandortMapper.mapToMatchStandort(bedarfAnfrage.getStandortVon()));

		match.setInstitutionVon(bedarfAnfrage.getBedarf().getInstitution());
		match.setStandortVon(MatchStandortMapper.mapToMatchStandort(bedarfAnfrage.getBedarf().getStandort()));

		matchStandortRepository.add(match.getStandortVon());
		matchStandortRepository.add(match.getStandortAn());

		match.setStatus(MatchStatus.Offen);
		return matchRepository.add(match);
	}

	@Transactional
	public Match matchAusAngebotErstellen(AngebotAnfrage angebotAnfrage) {
		val match = new Match();

		match.setInstitutionAn(angebotAnfrage.getInstitutionVon());
		match.setStandortAn(MatchStandortMapper.mapToMatchStandort(angebotAnfrage.getStandortVon()));

		match.setInstitutionVon(angebotAnfrage.getAngebot().getInstitution());
		match.setStandortVon(MatchStandortMapper.mapToMatchStandort(angebotAnfrage.getAngebot().getStandort()));

		matchStandortRepository.add(match.getStandortVon());
		matchStandortRepository.add(match.getStandortAn());

		match.setStatus(MatchStatus.Offen);
		return matchRepository.add(match);
	}

	@Transactional
	public List<Match> beteiligteMatches() {

		val userInstitutionId = userService.getContextInstitution().getId();

		val matches = matchRepository.getMatchesFuerInstitutionAn(userInstitutionId);
		matches.addAll(matchRepository.getMatchesFuerInstitutionAn(userInstitutionId));

		return mitEntfernung(matches);
	}

	private List<Match> mitEntfernung(final List<Match> matches) {
		matches.forEach(match -> match.setEntfernung(geoCalcService.berechneDistanzInKilometer(//
				match.getStandortVon(), //
				match.getStandortAn())));

		return matches;
	}
}
