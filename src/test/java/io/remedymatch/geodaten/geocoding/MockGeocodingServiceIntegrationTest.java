package io.remedymatch.geodaten.geocoding;

import io.remedymatch.TestApplication;
import io.remedymatch.WithMockJWT;
import io.remedymatch.geodaten.domain.GeocodingService;
import io.remedymatch.geodaten.geocoding.domain.Adresse;
import io.remedymatch.geodaten.geocoding.domain.Point;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"test", "disableexternaltasks"})
@Tag("InMemory")
@Tag("SpringBoot")
public class MockGeocodingServiceIntegrationTest {

    @Autowired
    private GeocodingService geocodingService;

    @Test
    @WithMockJWT(groupsClaim = {"testgroup"}, usernameClaim = "testUser")
    public void unstrukturierteAdressSucheSollteErgebnisseLiefern() {

        final String queryString = "Hüttenhospital Dortmund";

        final List<Point> gefundeneKoordinaten = geocodingService.findePointsByAdressString(queryString);

        assertThat(gefundeneKoordinaten).isNotNull();
        assertThat(gefundeneKoordinaten).isNotEmpty();
    }

    @Test
    @WithMockJWT(groupsClaim = {"testgroup"}, usernameClaim = "testUser")
    public void strukturierteAdressSucheSollteErgebnisseLiefern() {

        final Adresse adresse = new Adresse();
        adresse.setPlz("44229");
        adresse.setStrasse("Schneiderstraße");

        final List<Point> gefundeneKoordinaten = geocodingService.findePointsByAdresse(adresse);

        assertThat(gefundeneKoordinaten).isNotNull();
        assertThat(gefundeneKoordinaten).isNotEmpty();
    }

    @Test
    @WithMockJWT(groupsClaim = {"testgroup"}, usernameClaim = "testUser")
    public void sucheNachVorschlaegenSollteErgebnisseLiefern() {

        final String standort = "Hüttenhospital Dortmund";

        final List<String> vorschlaege = geocodingService.findeAdressVorschlaegeByAdressString(standort);

        assertThat(vorschlaege).isNotNull();
    }

    @Test
    @WithMockJWT(groupsClaim = {"testgroup"}, usernameClaim = "testUser")
    public void sucheNachAdresseViaKoordinatenSollteEinErgebnisLiefern() {

        final Point point = new Point(51.4807647, 7.50986959582075);

        final String adressString = geocodingService.findeAdresseByPoint(point);

        assertThat(adressString).isNotEmpty();
        assertThat(adressString).contains("Hüttenhospital");
    }
}
