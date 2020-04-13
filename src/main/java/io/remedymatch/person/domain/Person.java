package io.remedymatch.person.domain;

import io.remedymatch.institution.domain.model.Institution;
import io.remedymatch.institution.domain.model.InstitutionStandort;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Person {
	private PersonId id;
	private String username;
	private String vorname;
	private String nachname;
	private String email;
	private String telefon;
	private Institution institution;
	private InstitutionStandort standort;
}
