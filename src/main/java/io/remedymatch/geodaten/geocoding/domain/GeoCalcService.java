package io.remedymatch.geodaten.geocoding.domain;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import io.remedymatch.institution.domain.model.InstitutionStandort;
import io.remedymatch.match.domain.MatchStandort;
import io.remedymatch.shared.GeoCalc;
import io.remedymatch.user.domain.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Validated
public class GeoCalcService {

	private final UserService userService;

	public BigDecimal berechneUserDistanzInKilometer(//
			final InstitutionStandort standortNach) {
		return berechneDistanzInKilometer(userService.getContextInstitution().getHauptstandort(), standortNach);
	}

	public BigDecimal berechneDistanzInKilometer(//
			final InstitutionStandort standortVon, //
			final InstitutionStandort standortNach) {
		return BigDecimal.valueOf(GeoCalc.kilometerBerechnen(standortVon, standortNach));
	}

	public BigDecimal berechneDistanzInKilometer(//
			final MatchStandort standortVon, //
			final MatchStandort standortNach) {

		// Fixme - Standort als Interface umdefinieren
		return BigDecimal.valueOf(GeoCalc.kilometerBerechnen(standortVon, standortNach));
	}
}
