package io.remedymatch.geodaten.geocoding.domain;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import io.remedymatch.institution.domain.InstitutionStandort;
import io.remedymatch.shared.GeoCalc;

@Service
public class GeoCalcService {
	public BigDecimal berechneDistanzInKilometer(//
			final InstitutionStandort standortVon, //
			final InstitutionStandort standortNach) {
		return BigDecimal.valueOf(GeoCalc.kilometerBerechnen(standortVon, standortNach));
	}
}
