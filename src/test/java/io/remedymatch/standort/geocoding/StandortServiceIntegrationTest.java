package io.remedymatch.standort.geocoding;

import io.remedymatch.TestApplication;
import io.remedymatch.WithMockJWT;
import io.remedymatch.standort.api.StandortService;
import io.remedymatch.standort.geocoding.domain.Point;
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
@ActiveProfiles("test")
@Tag("InMemory")
@Tag("SpringBoot")
public class StandortServiceIntegrationTest {

    @Autowired
    private StandortService standortService;

    @Test
    @WithMockJWT(groupsClaim = {"testgroup"}, subClaim = "testUser")
    public void unstrukturierteSucheSollteErgebnisseLiefern() {
        final String queryString = "Hüttenhospital Dortmund";
        List<Point> gefundeneKoordinaten = standortService.findePointsByAdressString(queryString);
        assertThat(gefundeneKoordinaten != null && !(gefundeneKoordinaten.isEmpty()));
    }

}
