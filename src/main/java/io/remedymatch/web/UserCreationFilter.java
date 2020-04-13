package io.remedymatch.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import io.remedymatch.institution.domain.service.InstitutionSucheService;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionJpaRepository;
import io.remedymatch.institution.infrastructure.InstitutionStandortJpaRepository;
import io.remedymatch.person.domain.Person;
import io.remedymatch.person.domain.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.val;

@Order(1)
@AllArgsConstructor
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserCreationFilter implements Filter {

	private final PersonRepository personRepository;
	private final InstitutionSucheService institutionSucheService;
	private final InstitutionJpaRepository institutionJpaRepository;

	private final UserProvider userNameProvider;
	private final InstitutionKeyProvider institutionKeyProvider;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		this.insertNewUser();
		chain.doFilter(request, response);
	}

	@Transactional
	protected synchronized void insertNewUser() {
		val person = personRepository.findByUsername(userNameProvider.getUserName());

		if (person.isEmpty()) {
			// XXX sollte weg gehen nachdem der RegistrierungFreigabe Prozess fertig ist
			var institution = institutionSucheService.findByInstitutionKey(institutionKeyProvider.getInstitutionKey());

			if (institution.isEmpty()) {
				val newInstitution = new InstitutionEntity();
				newInstitution.setInstitutionKey(institutionKeyProvider.getInstitutionKey());
				institutionJpaRepository.save(newInstitution);
				institution = institutionSucheService.findByInstitutionKey(institutionKeyProvider.getInstitutionKey());
			}

			val newPerson = new Person();
			newPerson.setInstitution(institution.get());
			newPerson.setUsername(userNameProvider.getUserName());
			personRepository.add(newPerson);
		}
	}
}
