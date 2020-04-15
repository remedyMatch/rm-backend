package io.remedymatch.usercontext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.remedymatch.institution.domain.model.Institution;
import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.person.domain.model.Person;
import io.remedymatch.person.domain.service.PersonSucheService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserContextService {
	private final UserProvider userProvider;
	private final PersonSucheService personSucheService;

	@Transactional(readOnly = true)
	public Person getContextUser() {
		return personSucheService.findByUsername(userProvider.getUserName()).get();
	}

	@Transactional(readOnly = true)
	public Institution getContextInstitution() {
		return getContextUser().getInstitution();
	}

	@Transactional(readOnly = true)
	public InstitutionId getContextInstitutionId() {
		return getContextInstitution().getId();
	}

	@Transactional(readOnly = true)
	public boolean isUserContextInstitution(final InstitutionId institutionId) {
		return getContextInstitution().getId().equals(institutionId);
	}
}
