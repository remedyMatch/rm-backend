package io.remedymatch.user.domain;

import org.springframework.stereotype.Service;

import io.remedymatch.institution.domain.InstitutionEntity;
import io.remedymatch.institution.domain.InstitutionId;
import io.remedymatch.person.domain.PersonEntity;
import io.remedymatch.person.domain.PersonRepository;
import io.remedymatch.web.UserProvider;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {
	private final UserProvider userProvider;
	private final PersonRepository personRepository;

	public PersonEntity getContextUser()
	{
		 return personRepository.findByUsername(userProvider.getUserName());
	}
	
	public InstitutionEntity getContextInstitution()
	{
		 return getContextUser().getInstitution();
	}
	
	public boolean isUserContextInstitution(final InstitutionId institutionId)
	{
		 return getContextInstitution().getId().equals(institutionId.getValue());
	}
}
