package io.remedymatch.shared;

import io.remedymatch.institution.domain.model.InstitutionStandort;
import io.remedymatch.match.domain.MatchStandort;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
@DisplayName("GeoCalc soll")
public class GeoCalcShould {

	private double HUETTENHOSPITAL_DORTMUND_LAT = 51.480729d;
	private double HUETTENHOSPITAL_DORTMUND_LON = 7.5078063d;
	private double BERUFSKOLLEG_MENDEN_LAT =51.4425964d;
	private double BERUFSKOLLEG_MENDEN_LON = 7.7849144;

	@Test
	@DisplayName("Kilometer via Lat/Lon berechnen")
	void km_via_lat_lon_berechnen() {

		double distanz = GeoCalc.kilometerBerechnen(HUETTENHOSPITAL_DORTMUND_LAT, HUETTENHOSPITAL_DORTMUND_LON,
				BERUFSKOLLEG_MENDEN_LAT, BERUFSKOLLEG_MENDEN_LON);

		assertThat(distanz, Matchers.greaterThan(19.0d));
		assertThat(distanz, Matchers.lessThan(20.0d));
	}

	@Test
	@DisplayName("Kilometer via gleichen Lat/Lon berechnen")
	void km_via_gleichen_lat_lon_berechnen() {

		double distanz = GeoCalc.kilometerBerechnen(HUETTENHOSPITAL_DORTMUND_LAT, HUETTENHOSPITAL_DORTMUND_LON,
				HUETTENHOSPITAL_DORTMUND_LAT, HUETTENHOSPITAL_DORTMUND_LON);

		assertThat(distanz, Matchers.is(0.0d));
	}

	@Test
	@DisplayName("Kilometer via Match-Standorten berechnen")
	void km_via_match_standorten_berechnen() {

		MatchStandort matchStandortDortmund = MatchStandort.builder()
				.latitude(BigDecimal.valueOf(HUETTENHOSPITAL_DORTMUND_LAT))
				.longitude(BigDecimal.valueOf(HUETTENHOSPITAL_DORTMUND_LON))
				.build();
		MatchStandort matchStandortMenden = MatchStandort.builder()
				.latitude(BigDecimal.valueOf(BERUFSKOLLEG_MENDEN_LAT))
				.longitude(BigDecimal.valueOf(BERUFSKOLLEG_MENDEN_LON))
				.build();

		double distanz = GeoCalc.kilometerBerechnen(matchStandortDortmund, matchStandortMenden);

		assertThat(distanz, Matchers.greaterThan(19.0d));
		assertThat(distanz, Matchers.lessThan(20.0d));
	}

	@Test
	@DisplayName("Kilometer via Institutions-Standorten berechnen")
	void km_via_institution_standorten_berechnen() {

		InstitutionStandort institutionStandortDortmund = InstitutionStandort.builder()
				.latitude(BigDecimal.valueOf(HUETTENHOSPITAL_DORTMUND_LAT))
				.longitude(BigDecimal.valueOf(HUETTENHOSPITAL_DORTMUND_LON))
				.build();
		InstitutionStandort institutionStandortMenden = InstitutionStandort.builder()
				.latitude(BigDecimal.valueOf(BERUFSKOLLEG_MENDEN_LAT))
				.longitude(BigDecimal.valueOf(BERUFSKOLLEG_MENDEN_LON))
				.build();

		double distanz = GeoCalc.kilometerBerechnen(institutionStandortDortmund, institutionStandortMenden);

		assertThat(distanz, Matchers.greaterThan(19.0d));
		assertThat(distanz, Matchers.lessThan(20.0d));
	}

}
