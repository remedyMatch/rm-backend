package io.remedymatch.institution.domain.service;

import static io.remedymatch.institution.domain.service.InstitutionTestFixtures.beispielInstitution;
import static io.remedymatch.institution.domain.service.InstitutionTestFixtures.beispielInstitutionEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.institution.infrastructure.InstitutionJpaRepository;
import lombok.val;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { //
		InstitutionSucheService.class, //
		InstitutionJpaRepository.class //
})
@Tag("Spring")
@DisplayName("InstitutionSucheService soll")
class InstitutionSucheServiceShould {

	@Autowired
	private InstitutionSucheService institutionSucheService;

	@MockBean
	private InstitutionJpaRepository institutionRepository;

	@Test
	@DisplayName("gesuchte Institution finden")
	void gesuchte_Institution_finden() {

		val institutionEntity = beispielInstitutionEntity();
		val institutionKey = institutionEntity.getInstitutionKey();

		given(institutionRepository.findByInstitutionKey(institutionKey)).willReturn(Optional.of(institutionEntity));

		val expectedInstitution = beispielInstitution();

		assertEquals(Optional.of(expectedInstitution), institutionSucheService.findByInstitutionKey(institutionKey));

		then(institutionRepository).should().findByInstitutionKey(institutionKey);
		then(institutionRepository).shouldHaveNoMoreInteractions();
	}
}
