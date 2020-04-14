package io.remedymatch.institution.domain.service;

import static io.remedymatch.institution.domain.service.InstitutionTestFixtures.beispielInstitution;
import static io.remedymatch.institution.domain.service.InstitutionTestFixtures.beispielInstitutionEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.Optional;

import org.aspectj.weaver.ast.IExprVisitor;
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
	@DisplayName("gesuchte Institution fuer InstitutionId finden")
	void gesuchte_Institution_fuer_InstitutionId_finden() {

		val institutionEntity = beispielInstitutionEntity();
		val institutionId = institutionEntity.getId();

		given(institutionRepository.findById(institutionId)).willReturn(Optional.of(institutionEntity));

		val expectedInstitution = beispielInstitution();

		assertEquals(Optional.of(expectedInstitution), institutionSucheService.findInstitution(expectedInstitution.getId()));

		then(institutionRepository).should().findById(institutionId);
		then(institutionRepository).shouldHaveNoMoreInteractions();
	}
	
	@Test
	@DisplayName("gesuchte Institution fuer InstitutionKey finden")
	void gesuchte_Institution_fuer_InstitutionKey_finden() {

		val institutionEntity = beispielInstitutionEntity();
		val institutionKey = institutionEntity.getInstitutionKey();

		given(institutionRepository.findByInstitutionKey(institutionKey)).willReturn(Optional.of(institutionEntity));

		val expectedInstitution = beispielInstitution();

		assertEquals(Optional.of(expectedInstitution), institutionSucheService.findByInstitutionKey(institutionKey));

		then(institutionRepository).should().findByInstitutionKey(institutionKey);
		then(institutionRepository).shouldHaveNoMoreInteractions();
	}
}
