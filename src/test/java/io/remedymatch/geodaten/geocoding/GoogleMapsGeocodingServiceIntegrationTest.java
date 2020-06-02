package io.remedymatch.geodaten.geocoding;

import io.remedymatch.TestApplication;
import io.remedymatch.WithMockJWT;
import io.remedymatch.geodaten.domain.GeocodingService;
import io.remedymatch.geodaten.geocoding.domain.Adresse;
import io.remedymatch.geodaten.geocoding.domain.Point;
import io.remedymatch.institution.domain.model.InstitutionStandort;
import io.remedymatch.usercontext.UserContextService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static io.remedymatch.shared.DefaultDistanzBerechnungServiceShould.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Disabled(value = "Only for manual execution! Make sure to set io.remedymatch.geodaten.* - properties in " +
        "application-geo.yaml.")
@ActiveProfiles(profiles = {"test", "disableexternaltasks", "geo"})
@Tag("InMemory")
@Tag("SpringBoot")
public class GoogleMapsGeocodingServiceIntegrationTest {

    @Autowired
    private GeocodingService geocodingService;

    @MockBean(answer = Answers.RETURNS_DEEP_STUBS)
    private UserContextService userService;

    @Test
    @WithMockJWT(groupsClaim = {"testgroup"}, usernameClaim = "testUser")
    public void unstrukturierteAdressSucheSollteErgebnisseLiefern() throws InterruptedException {

        final String queryString = "Hüttenhospital Dortmund";

        final List<Point> gefundeneKoordinaten = geocodingService.findePointsByAdressString(queryString);
        Thread.sleep(2000);

        assertThat(gefundeneKoordinaten).isNotNull();
        assertThat(gefundeneKoordinaten).isNotEmpty();
    }

    @Test
    @WithMockJWT(groupsClaim = {"testgroup"}, usernameClaim = "testUser")
    public void strukturierteAdressSucheSollteErgebnisseLiefern() throws InterruptedException {

        final Adresse adresse = new Adresse();
        adresse.setPlz("44229");
        adresse.setStrasse("Schneiderstraße");

        final List<Point> gefundeneKoordinaten = geocodingService.findePointsByAdresse(adresse);
        Thread.sleep(2000);

        assertThat(gefundeneKoordinaten).isNotNull();
        assertThat(gefundeneKoordinaten).isNotEmpty();
    }

    @Test
    @WithMockJWT(groupsClaim = {"testgroup"}, usernameClaim = "testUser")
    public void sucheNachVorschlaegenSollteErgebnisseLiefern() throws InterruptedException {

        final String standort = "Hüttenhospital Dortmund";

        final List<String> vorschlaege = geocodingService.findeAdressVorschlaegeByAdressString(standort);
        Thread.sleep(2000);

        assertThat(vorschlaege).isNotNull();
        assertThat(vorschlaege).isNotEmpty();
    }

    @Test
    @WithMockJWT(groupsClaim = {"testgroup"}, usernameClaim = "testUser")
    public void sucheNachAdresseViaKoordinatenSollteEinErgebnisLiefern() {

        // Hüttenhospital, Am Marksbach 28, 44269 Dortmund, Germany
        final Point point = new Point(51.4807647, 7.50986959582075);

        final String adressString = geocodingService.findeAdresseByPoint(point);

        assertThat(adressString).isNotEmpty();
        assertThat(adressString).contains("Am Marksbach");
        assertThat(adressString).contains("44269");
    }

    @Test
    @DisplayName("Kilometer via Institutions-Standorten berechnen")
    void sollteKmViaInstitutionStandortenBerechnen() {

        InstitutionStandort instiStandortDortmund = InstitutionStandort.builder()
                .latitude(BigDecimal.valueOf(HUETTENHOSPITAL_DORTMUND_LAT))
                .longitude(BigDecimal.valueOf(HUETTENHOSPITAL_DORTMUND_LON))
                .build();
        InstitutionStandort instiStandortMenden = InstitutionStandort.builder()
                .latitude(BigDecimal.valueOf(BERUFSKOLLEG_MENDEN_LAT))
                .longitude(BigDecimal.valueOf(BERUFSKOLLEG_MENDEN_LON))
                .build();

        BigDecimal distanz = geocodingService.berechneDistanzInKilometer(instiStandortDortmund, instiStandortMenden);

        assertThat(distanz).isGreaterThan(BigDecimal.valueOf(28.0d)); // shortest driving track: 28.2 km
        assertThat(distanz).isLessThan(BigDecimal.valueOf(36.0d));// longest driving track: 35.8 km
    }

    @Test
    @DisplayName("Kilometer fuer User Distanz berechnen")
    void sollteKmViafuerUserDistanzBerechnen() {

        InstitutionStandort instiStandortDortmund = InstitutionStandort.builder()
                .latitude(BigDecimal.valueOf(HUETTENHOSPITAL_DORTMUND_LAT))
                .longitude(BigDecimal.valueOf(HUETTENHOSPITAL_DORTMUND_LON))
                .build();
        InstitutionStandort hauptstandort = InstitutionStandort.builder()
                .latitude(BigDecimal.valueOf(BERUFSKOLLEG_MENDEN_LAT))
                .longitude(BigDecimal.valueOf(BERUFSKOLLEG_MENDEN_LON))
                .build();
        when(userService.getContextStandort().getStandort()).thenReturn(hauptstandort);

        BigDecimal distanz = geocodingService.berechneUserDistanzInKilometer(instiStandortDortmund);

        assertThat(distanz).isGreaterThan(BigDecimal.valueOf(28.0d)); // shortest driving track: 28.2 km
        assertThat(distanz).isLessThan(BigDecimal.valueOf(36.0d));// longest driving track: 35.8 km
    }
}
