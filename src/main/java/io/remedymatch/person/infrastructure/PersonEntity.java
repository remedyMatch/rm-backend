package io.remedymatch.person.infrastructure;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import io.remedymatch.shared.infrastructure.Auditable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity(name = "Person")
@Table(name = "RM_PERSON")
public class PersonEntity extends Auditable {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Type(type = "uuid-char")
	@Column(name = "UUID", unique = true, nullable = false, updatable = false, length = 36)
	private UUID id;

	@Column(name = "USERNAME", nullable = false, updatable = false, length = 64)
	private String username;

	@Column(name = "VORNAME", nullable = true, updatable = true, length = 64)
	private String vorname;

	@Column(name = "NACHNAME", nullable = true, updatable = true, length = 64)
	private String nachname;

	@Column(name = "EMAIL", nullable = true, updatable = true, length = 64)
	private String email;

	@Column(name = "TELEFON", nullable = true, updatable = true, length = 32)
	private String telefon;

	@ManyToOne
	@Type(type = "uuid-char")
	@JoinColumn(name = "INSTITUTION_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
	private InstitutionEntity institution;

	@ManyToOne
	@Type(type = "uuid-char")
	@JoinColumn(name = "STANDORT_UUID", referencedColumnName = "UUID", nullable = true, updatable = true)
	private InstitutionStandortEntity standort;
}
