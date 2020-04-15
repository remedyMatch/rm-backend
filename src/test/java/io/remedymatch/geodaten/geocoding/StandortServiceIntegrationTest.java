package io.remedymatch.geodaten.geocoding;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.remedymatch.TestApplication;
import io.remedymatch.WithMockJWT;
import io.remedymatch.geodaten.domain.StandortService;
import io.remedymatch.geodaten.geocoding.domain.Adresse;
import io.remedymatch.geodaten.geocoding.domain.Point;
import io.remedymatch.geodaten.geocoding.impl.locationiq.domain.Response;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"test", "disableexternaltasks"})
@Tag("InMemory")
@Tag("SpringBoot")
public class StandortServiceIntegrationTest {

    @Autowired
    private StandortService standortService;

    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() throws JsonProcessingException {
        final ResponseEntity MOCK_RESPONSE_ENTITY = Mockito.mock(ResponseEntity.class, Answers.RETURNS_DEEP_STUBS);
        final String MOCK_RESPONSE_JSON = "[{\"place_id\":\"331602623200\",\"osm_type\":\"way\",\"osm_id\":\"34139339\",\"licence\":\"https://locationiq.com/attribution\",\"lat\":\"51.480804\",\"lon\":\"7.509966\",\"display_name\":\"Hüttenhospital, 28, Am Marksbach, Dortmund, Dortmund , Nordrhein-Westfalen, Germany, 44269\",\"boundingbox\":[\"51.480503\",\"51.4809758\",\"7.5091999\",\"7.5105388\"],\"importance\":0.25},{\"place_id\":\"334179159910\",\"licence\":\"https://locationiq.com/attribution\",\"lat\":\"51.478064\",\"lon\":\"7.509654\",\"display_name\":\"Hüttenhospitalstraße, Dortmund, Dortmund , Nordrhein-Westfalen, Germany\",\"boundingbox\":[\"51.477191\",\"51.478908\",\"7.509075\",\"7.510106\"],\"importance\":0.25}]";
        final Response[] responses = new ObjectMapper().readValue(MOCK_RESPONSE_JSON, Response[].class);
        when(MOCK_RESPONSE_ENTITY.getBody()).thenReturn(responses);
        when(MOCK_RESPONSE_ENTITY.getStatusCode()).thenReturn(HttpStatus.OK);
        when(restTemplate.getMessageConverters()).thenReturn(new ArrayList<>());
        when(restTemplate.getForEntity(any(), any())).thenReturn(MOCK_RESPONSE_ENTITY);
    }

    @Test
    @WithMockJWT(groupsClaim = {"testgroup"}, subClaim = "testUser")
    public void unstrukturierteAdressSucheSollteErgebnisseLiefern() {

        final String queryString = "Hüttenhospital Dortmund";

        final List<Point> gefundeneKoordinaten = standortService.findePointsByAdressString(queryString);

        assertThat(gefundeneKoordinaten != null && !(gefundeneKoordinaten.isEmpty()));
    }

    @Test
    @WithMockJWT(groupsClaim = {"testgroup"}, subClaim = "testUser")
    public void strukturierteAdressSucheSollteErgebnisseLiefern() {

        final Adresse adresse = new Adresse();
        adresse.setPlz("44229");
        adresse.setStrasse("Schneiderstraße");

        final List<Point> gefundeneKoordinaten = standortService.findePointsByAdresse(adresse);

        assertThat(gefundeneKoordinaten != null && !(gefundeneKoordinaten.isEmpty()));
    }

    @Test
    @WithMockJWT(groupsClaim = {"testgroup"}, subClaim = "testUser")
    public void sucheNachVorschlaegenSollteErgebnisseLiefern() {

        final String standort = "Hüttenhospital Dortmund";

        final List<String> vorschlaege = standortService.findeAdressVorschlaegeByAdressString(standort);

        assertThat(vorschlaege != null && !(vorschlaege.isEmpty()));
    }

    @Test
    @WithMockJWT(groupsClaim = {"testgroup"}, subClaim = "testUser")
    public void sucheNachAdresseViaKoordinatenSollteEinErgebnisLiefern() {

        final Point point = new Point(51.4807647, 7.50986959582075);

        final String adressString = standortService.findeAdresseByPoint(point);

        assertThat(StringUtils.isNotEmpty(adressString));
        assertThat(adressString.contains("Hüttenhospital"));
    }
}
