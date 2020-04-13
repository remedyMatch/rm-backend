package io.remedymatch.user.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional(readOnly = true)
	public Person getContextUser()
	{
		 return personRepository.findByUsername(userProvider.getUserName()).get();
	}

	@Transactional(readOnly = true)
	public Institution getContextInstitution()
	{
		 return getContextUser().getInstitution();
	}

	@Transactional(readOnly = true)
	public InstitutionId getContextInstitutionId()
	{
		 return getContextInstitution().getId();
	}

	@Transactional(readOnly = true)
	public boolean isUserContextInstitution(final InstitutionId institutionId)
	{
		 return getContextInstitution().getId().equals(institutionId);
	}
}
