package io.remedymatch.usercontext;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.remedymatch.institution.domain.model.Institution;
import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.person.domain.model.Person;
import io.remedymatch.person.domain.model.PersonId;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserContextService {

	public Person getContextUser() {
		return UserContext.getContextUser();
	}

	@Transactional(readOnly = true)
	public PersonId getContextUserId() {
		return getContextUser().getId();
	}

	@Transactional(readOnly = true)
	public Institution getContextInstitution() {
		return getContextUser().getAktuelleInstitution().getInstitution();
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
