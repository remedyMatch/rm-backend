package io.remedymatch.web;


import java.io.IOException;
import java.util.Optional;

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

import io.remedymatch.institution.domain.Institution;
import io.remedymatch.institution.domain.InstitutionEntityConverter;
import io.remedymatch.institution.domain.InstitutionRepository;
import io.remedymatch.person.domain.PersonEntity;
import io.remedymatch.person.domain.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.val;

@Order(1)
@AllArgsConstructor
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserCreationFilter implements Filter {

    private final PersonRepository personRepository;
    private final InstitutionRepository institutionRepository;

    private final UserProvider userNameProvider;
    private final InstitutionKeyProvider institutionKeyProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        this.insertNewUser();
        chain.doFilter(request, response);
    }

    @Transactional
    protected synchronized void insertNewUser() {
        val person = Optional.ofNullable(personRepository.findByUsername(userNameProvider.getUserName()));

        if (person.isEmpty()) {

            var institution = institutionRepository.findByInstitutionKey(institutionKeyProvider.getInstitutionKey());

            if (institution.isEmpty()) {
                val newInstitution = new Institution();
                newInstitution.setInstitutionKey(institutionKeyProvider.getInstitutionKey());
                institution = Optional.of(institutionRepository.add(newInstitution));
            }

            val newPerson = new PersonEntity();
            newPerson.setInstitution(InstitutionEntityConverter.convert(institution.get()));
            newPerson.setUsername(userNameProvider.getUserName());
            personRepository.save(newPerson);
        }
    }
}
