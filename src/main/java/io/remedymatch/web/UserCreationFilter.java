package io.remedymatch.web;


import io.remedymatch.institutions.InstitutionEntity;
import io.remedymatch.institutions.InstitutionRepository;
import io.remedymatch.persons.PersonEntity;
import io.remedymatch.persons.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Optional;

@Order(1)
@AllArgsConstructor
@Component
public class UserCreationFilter implements Filter {

    private final PersonRepository personRepository;
    private final InstitutionRepository institutionRepository;

    private final UserNameProvider userNameProvider;
    private final InstitutionKeyProvider institutionKeyProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        val person = Optional.ofNullable(personRepository.findByUserName(userNameProvider.getUserName()));

        if(person.isEmpty()){

            var institution =  Optional.ofNullable(institutionRepository.findByInstitutionKey(institutionKeyProvider.getInstitutionKey()));

            if(institution.isEmpty()){
                val newInstitution = new InstitutionEntity();
                newInstitution.setInstitutionKey(institutionKeyProvider.getInstitutionKey());
                institution = Optional.ofNullable(institutionRepository.save(newInstitution));
            }

            val newPerson = new PersonEntity();
            newPerson.setInstitution(institution.get());
            newPerson.setUserName(userNameProvider.getUserName());
            personRepository.save(newPerson);
        }

        chain.doFilter(request, response);

    }
}
