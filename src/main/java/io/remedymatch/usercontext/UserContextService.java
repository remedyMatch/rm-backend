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

	/**
	 * Liefert die Institution des Context Users.
	 * @param excludeHauptStandortFromStandorte Hauptstandort ist nicht in Standorte-Liste enthalten falls true,
	 *                                          ansonsten (falls false) ist er enthalten.
	 * @return Institution des ContextUsers.
	 */
	@Transactional(readOnly = true)
	public Institution getContextInstitution(boolean excludeHauptStandortFromStandorte) {
		Institution institution = getContextUser().getInstitution();
		if (excludeHauptStandortFromStandorte) {
			institution.setStandorte(institution.getStandorte().stream()
					.filter(standort -> !(standort.getId().equals(institution.getHauptstandort().getId())))
					.collect(Collectors.toList()));
		}
		return institution;
	}

	/**
	 * Ruft {@link UserContextService#getContextInstitution(boolean)} mit
	 * {@code false} auf.
	 */
	@Transactional(readOnly = true)
	public Institution getContextInstitution() {
		return getContextInstitution(false);
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
