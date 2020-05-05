package io.remedymatch.bedarf.domain.service;

import io.remedymatch.engine.client.EngineClient;
import io.remedymatch.engine.domain.BusinessKey;
import io.remedymatch.engine.domain.ProzessInstanzId;
import io.remedymatch.institution.domain.service.InstitutionTestFixtures;
import io.remedymatch.person.domain.service.PersonTestFixtures;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { //
        BedarfProzessService.class, //
        EngineClient.class //
})
@Tag("Spring")
@DisplayName("BedarfAnfrageProzessService soll")
class BedarfProzessServiceShould {

    @Autowired
    private BedarfProzessService anfrageProzessService;

    @MockBean
    private EngineClient engineClient;

    @Test
    @DisplayName("Prozess starten koennen")
    void prozess_starten_koennen() {

        val bedarfId = BedarfTestFixtures.beispielBedarfId();
        val personId = PersonTestFixtures.beispielPersonId();
        val bedarfInstitutionId = InstitutionTestFixtures.beispielInstitutionId();

        val businessKey = new BusinessKey(bedarfId.getValue());
        val expectedProzessInstanzId = new ProzessInstanzId("egal");

        given(engineClient.prozessStarten(eq(BedarfProzessService.PROZESS_KEY), eq(businessKey), eq(personId), anyMap()))
                .willReturn(expectedProzessInstanzId);

        anfrageProzessService.prozessStarten(bedarfId, personId, bedarfInstitutionId);

        then(engineClient).should().prozessStarten(eq(BedarfProzessService.PROZESS_KEY), eq(businessKey), eq(personId), anyMap());
        then(engineClient).shouldHaveNoMoreInteractions();
    }
}
