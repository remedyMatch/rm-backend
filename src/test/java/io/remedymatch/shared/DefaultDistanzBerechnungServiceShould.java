package io.remedymatch.shared;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
@DisplayName("GeoCalc soll")
public class DefaultDistanzBerechnungServiceShould {

	public static double HUETTENHOSPITAL_DORTMUND_LAT = 51.480729d;
	public static double HUETTENHOSPITAL_DORTMUND_LON = 7.5078063d;
	public static double BERUFSKOLLEG_MENDEN_LAT =51.4425964d;
	public static double BERUFSKOLLEG_MENDEN_LON = 7.7849144;

	private final DefaultDistanzBerechnungService service = new DefaultDistanzBerechnungService();

	@Test
	@DisplayName("Kilometer via Lat/Lon berechnen")
	void km_via_lat_lon_berechnen() {

		double distanz = service.distanzBerechnen(HUETTENHOSPITAL_DORTMUND_LAT, HUETTENHOSPITAL_DORTMUND_LON,
				BERUFSKOLLEG_MENDEN_LAT, BERUFSKOLLEG_MENDEN_LON, DistanzTyp.Kilometer);

		assertThat(distanz, Matchers.greaterThan(19.0d));
		assertThat(distanz, Matchers.lessThan(20.0d));
	}

	@Test
	@DisplayName("Kilometer via gleichen Lat/Lon berechnen")
	void km_via_gleichen_lat_lon_berechnen() {

		double distanz = service.distanzBerechnen(HUETTENHOSPITAL_DORTMUND_LAT, HUETTENHOSPITAL_DORTMUND_LON,
				HUETTENHOSPITAL_DORTMUND_LAT, HUETTENHOSPITAL_DORTMUND_LON, DistanzTyp.Kilometer);

		assertThat(distanz, Matchers.is(0.0d));
	}


}
