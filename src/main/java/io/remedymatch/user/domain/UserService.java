package io.remedymatch.user.domain;

import org.springframework.stereotype.Service;

import io.remedymatch.institution.domain.model.Institution;
import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.person.domain.Person;
import io.remedymatch.person.domain.PersonRepository;
import io.remedymatch.web.UserProvider;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {
	private final UserProvider userProvider;
	private final PersonRepository personRepository;

	public Person getContextUser()
	{
		 return personRepository.findByUsername(userProvider.getUserName()).get();
	}
	
	public Institution getContextInstitution()
	{
		 return getContextUser().getInstitution();
	}
	
	public boolean isUserContextInstitution(final InstitutionId institutionId)
	{
		 return getContextInstitution().getId().equals(institutionId);
	}
}
