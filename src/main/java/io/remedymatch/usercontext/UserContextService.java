package io.remedymatch.usercontext;

import io.remedymatch.institution.domain.model.Institution;
import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.person.domain.model.Person;
import io.remedymatch.person.domain.model.PersonId;
import io.remedymatch.person.domain.service.PersonSucheService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserContextService {
	private final UserContextProvider userProvider;
	private final PersonSucheService personSucheService;

	@Transactional(readOnly = true)
	public Person getContextUser() {
		return personSucheService.findByUsername(userProvider.getUserName()).get();
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
	 * Ruft {@link UserContextService#getContextInstitution(boolean)} mit {@code false} auf.
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
