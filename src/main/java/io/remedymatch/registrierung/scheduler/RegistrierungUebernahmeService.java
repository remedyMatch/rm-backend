package io.remedymatch.registrierung.scheduler;

import static org.apache.commons.lang3.StringUtils.lowerCase;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.remedymatch.institution.domain.model.InstitutionTyp;
import io.remedymatch.institution.domain.model.NeueInstitution;
import io.remedymatch.institution.domain.model.NeuerInstitutionStandort;
import io.remedymatch.institution.domain.service.InstitutionService;
import io.remedymatch.person.domain.model.NeuePerson;
import io.remedymatch.person.domain.service.PersonService;
import io.remedymatch.registrierung.keycloak.RegistrierterUser;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Validated
@Service
@Transactional
class RegistrierungUebernahmeService {

	private final InstitutionService institutionService;
	private final PersonService personService;

	void registrierungUebernehmen(final @NotNull @Valid RegistrierterUser registrierterUser) {
		val institution = institutionService.institutionAnlegen(createNeueInstitution(registrierterUser));
		personService.personAnlegen(NeuePerson.builder() //
				.username(registrierterUser.getUsername()) //
				.vorname(registrierterUser.getVorname()) //
				.nachname(registrierterUser.getNachname()) //
				.email(registrierterUser.getEmail()) //
				.telefon(registrierterUser.getTelefon()) //
				.institutionId(institution.getId()) //
				.standortId(institution.getHauptstandort().getId()) //
				.build());
	}

	private NeueInstitution createNeueInstitution(final RegistrierterUser registrierterUser) {
		val institutionTyp = convertToInstitutionTyp(registrierterUser.getInstitutionTyp());
		val institutionName = createInstitutionName(registrierterUser, institutionTyp);
		val institutionKey = createInstitutionKey(registrierterUser, institutionTyp);

		return NeueInstitution.builder() //
				.name(institutionName) //
				.institutionKey(institutionKey) //
				.typ(institutionTyp) //
				.hauptstandort(NeuerInstitutionStandort.builder() //
						.name(institutionName) //
						.strasse(registrierterUser.getStrasse()) //
						.hausnummer(registrierterUser.getHausnummer()) //
						.plz(registrierterUser.getPlz()) //
						.ort(registrierterUser.getOrt()) //
						.land(registrierterUser.getLand()) //
						.build()) //
				.build();
	}

	private String createInstitutionName( //
			final RegistrierterUser registrierterUser, //
			final InstitutionTyp institutionTyp) {
		if (InstitutionTyp.PRIVAT.equals(institutionTyp)) {
			return String.format("%s %s", //
					trimToEmpty(registrierterUser.getVorname()), //
					trimToEmpty(registrierterUser.getNachname()));
		}

		return trimToEmpty(registrierterUser.getInstitutionName());
	}

	private String createInstitutionKey( //
			final RegistrierterUser registrierterUser, //
			final InstitutionTyp institutionTyp) {
		if (InstitutionTyp.PRIVAT.equals(institutionTyp)) {
			return String.format("%s__%s", //
					lowerCase(institutionTyp.name()), //
					RegExUtils.replaceAll(lowerCase(trimToEmpty(registrierterUser.getUsername())), "\\s+", "_"));
		}

		return String.format("%s__%s", //
				lowerCase(institutionTyp.name()), //
				RegExUtils.replaceAll(lowerCase(trimToEmpty(registrierterUser.getInstitutionName())), "\\s+", "_"));
	}

	private InstitutionTyp convertToInstitutionTyp(final String institutionTyp) {
		if (StringUtils.isBlank(institutionTyp)) {
			return InstitutionTyp.PRIVAT;
		}

		switch (institutionTyp) {
		case "Arzt":
			return InstitutionTyp.ARZT;
		case "Gewerbe und Industrie":
			return InstitutionTyp.GEWERBE_UND_INDUSTRIE;
		case "Krankenhaus":
			return InstitutionTyp.KRANKENHAUS;
		case "Lieferant":
			return InstitutionTyp.LIEFERANT;
		case "Privat":
			return InstitutionTyp.PRIVAT;
		default:
			return InstitutionTyp.ANDERE;
		}
	}
}
