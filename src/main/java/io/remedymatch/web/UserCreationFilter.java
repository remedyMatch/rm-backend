package io.remedymatch.web;


import io.remedymatch.institution.domain.InstitutionEntity;
import io.remedymatch.institution.domain.InstitutionRepository;
import io.remedymatch.person.domain.PersonEntity;
import io.remedymatch.person.domain.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

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

            var institution = Optional.ofNullable(institutionRepository.findByInstitutionKey(institutionKeyProvider.getInstitutionKey()));

            if (institution.isEmpty()) {
                val newInstitution = new InstitutionEntity();
                newInstitution.setInstitutionKey(institutionKeyProvider.getInstitutionKey());
                institution = Optional.of(institutionRepository.save(newInstitution));
            }

            val newPerson = new PersonEntity();
            newPerson.setInstitution(institution.get());
            newPerson.setUsername(userNameProvider.getUserName());
            personRepository.save(newPerson);
        }
    }
}
