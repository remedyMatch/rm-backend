package io.remedymatch.geodaten.geocoding;


import io.remedymatch.TestApplication;
import io.remedymatch.WithMockJWT;
import io.remedymatch.geodaten.api.StandortService;
import io.remedymatch.geodaten.geocoding.domain.Adresse;
import io.remedymatch.geodaten.geocoding.domain.Point;
import io.remedymatch.properties.RmBackendProperties;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Tag("InMemory")
@Tag("SpringBoot")
public class StandortServiceIntegrationTest {

    @Autowired
    private StandortService standortService;

    @Autowired
    private RmBackendProperties properties;

    private boolean serviceConfigured = true;

    // sleep after each service request (otherwise 429 Too Many Requests: [{"error":"Rate Limited )
    private static final long SLEEP_MS = 1000;

    @BeforeEach
    public void setup() {
        @NotNull @NotBlank String geocoderServiceApiKey = properties.getGeocoderServiceApiKey();
        serviceConfigured = StringUtils.isNotEmpty(geocoderServiceApiKey) &&
                !(geocoderServiceApiKey.equals("NOT_CONFIGURED"));
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        if (serviceConfigured) {
            Thread.sleep(SLEEP_MS);
        }
    }

    @Test
    @WithMockJWT(groupsClaim = {"testgroup"}, subClaim = "testUser")
    public void unstrukturierteAdressSucheSollteErgebnisseLiefern() throws InterruptedException {
        if (!(serviceConfigured)) {
            assertThat(true);
            return;
        }
        final String queryString = "Hüttenhospital Dortmund";

        final List<Point> gefundeneKoordinaten = standortService.findePointsByAdressString(queryString);

        assertThat(gefundeneKoordinaten != null && !(gefundeneKoordinaten.isEmpty()));
    }

    @Test
    @WithMockJWT(groupsClaim = {"testgroup"}, subClaim = "testUser")
    public void strukturierteAdressSucheSollteErgebnisseLiefern() {
        if (!(serviceConfigured)) {
            assertThat(true);
            return;
        }
        final Adresse adresse = new Adresse();
        adresse.setPlz("44229");
        adresse.setStrasse("Schneiderstraße");

        final List<Point> gefundeneKoordinaten = standortService.findePointsByAdresse(adresse);

        assertThat(gefundeneKoordinaten != null && !(gefundeneKoordinaten.isEmpty()));
    }

    @Test
    @WithMockJWT(groupsClaim = {"testgroup"}, subClaim = "testUser")
    public void sucheNachVorschlaegenSollteErgebnisseLiefern() {
        if (!(serviceConfigured)) {
            assertThat(true);
            return;
        }
        final String standort = "Hüttenhospital Dortmund";

        final List<String> vorschlaege = standortService.findeAdressVorschlaegeByAdressString(standort);

        assertThat(vorschlaege != null && !(vorschlaege.isEmpty()));
    }

    @Test
    @WithMockJWT(groupsClaim = {"testgroup"}, subClaim = "testUser")
    public void sucheNachAdresseViaKoordinatenSollteEinErgebnisLiefern() {
        if (!(serviceConfigured)) {
            assertThat(true);
            return;
        }
        final Point point = new Point(51.4807647, 7.50986959582075);

        final String adressString = standortService.findeAdresseByPoint(point);

        assertThat(StringUtils.isNotEmpty(adressString));
        assertThat(adressString.contains("Hüttenhospital"));
    }
}
