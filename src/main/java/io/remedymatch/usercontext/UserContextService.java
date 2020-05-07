package io.remedymatch.usercontext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.person.domain.model.Person;
import io.remedymatch.person.domain.model.PersonId;
import io.remedymatch.person.domain.model.PersonStandort;
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
	public PersonStandort getContextStandort() {
		return getContextUser().getAktuellesStandort();
	}
	
	@Transactional(readOnly = true)
	public InstitutionId getContextInstitutionId() {
		return getContextStandort().getInstitution().getId();
	}
	
	@Transactional(readOnly = true)
	public boolean isUserContextInstitution(final InstitutionId institutionId) {
		return getContextInstitutionId().equals(institutionId);
	}
}
