package io.remedymatch.geodaten.geocoding;


import io.remedymatch.TestApplication;
import io.remedymatch.WithMockJWT;
import io.remedymatch.geodaten.domain.StandortService;
import io.remedymatch.geodaten.geocoding.domain.Adresse;
import io.remedymatch.geodaten.geocoding.domain.Point;
import org.apache.commons.lang3.StringUtils;
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
public class StandortServiceIntegrationTest {

    @Autowired
    private StandortService standortService;

    @Test
    @WithMockJWT(groupsClaim = {"testgroup"}, usernameClaim = "testUser")
    public void unstrukturierteAdressSucheSollteErgebnisseLiefern() {

        final String queryString = "Hüttenhospital Dortmund";

        final List<Point> gefundeneKoordinaten = standortService.findePointsByAdressString(queryString);

        assertThat(gefundeneKoordinaten != null && !(gefundeneKoordinaten.isEmpty()));
    }

    @Test
    @WithMockJWT(groupsClaim = {"testgroup"}, usernameClaim = "testUser")
    public void strukturierteAdressSucheSollteErgebnisseLiefern() {

        final Adresse adresse = new Adresse();
        adresse.setPlz("44229");
        adresse.setStrasse("Schneiderstraße");

        final List<Point> gefundeneKoordinaten = standortService.findePointsByAdresse(adresse);

        assertThat(gefundeneKoordinaten != null && !(gefundeneKoordinaten.isEmpty()));
    }

    @Test
    @WithMockJWT(groupsClaim = {"testgroup"}, usernameClaim = "testUser")
    public void sucheNachVorschlaegenSollteErgebnisseLiefern() {

        final String standort = "Hüttenhospital Dortmund";

        final List<String> vorschlaege = standortService.findeAdressVorschlaegeByAdressString(standort);

        assertThat(vorschlaege != null && !(vorschlaege.isEmpty()));
    }

    @Test
    @WithMockJWT(groupsClaim = {"testgroup"}, usernameClaim = "testUser")
    public void sucheNachAdresseViaKoordinatenSollteEinErgebnisLiefern() {

        final Point point = new Point(51.4807647, 7.50986959582075);

        final String adressString = standortService.findeAdresseByPoint(point);

        assertThat(StringUtils.isNotEmpty(adressString));
        assertThat(adressString.contains("Hüttenhospital"));
    }
}
