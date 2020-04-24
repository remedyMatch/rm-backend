package io.remedymatch.institution.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.util.Assert;

import io.remedymatch.institution.domain.model.InstitutionTyp;
import io.remedymatch.shared.infrastructure.Auditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Entity(name = "Institution")
@Table(name = "RM_INSTITUTION")
public class InstitutionEntity extends Auditable {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Type(type = "uuid-char")
	@Column(name = "UUID", unique = true, nullable = false, updatable = false, length = 36)
	private UUID id;

	@Column(name = "NAME", nullable = true, updatable = true, length = 64)
	private String name;

	@Column(name = "INSTITUTION_KEY", nullable = false, updatable = true, length = 64)
	private String institutionKey;

	@Enumerated(EnumType.STRING)
	@Column(name = "TYP", nullable = true, updatable = true, length = 64)
	private InstitutionTyp typ;

	@OneToOne
	@JoinColumn(name = "HAUPTSTANDORT_UUID", referencedColumnName = "UUID", nullable = true, updatable = true)
	private InstitutionStandortEntity hauptstandort;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "RM_INSTITUTION_2_STANDORT", //
			joinColumns = @JoinColumn(name = "INSTITUTION_UUID"), //
			inverseJoinColumns = @JoinColumn(name = "INSTITUTION_STANDORT_UUID"))
	@Builder.Default
	private List<InstitutionStandortEntity> standorte = new ArrayList<>();

	public Optional<InstitutionStandortEntity> findStandort(final UUID standortId) {
		Assert.notNull(standortId, "StandortId ist null.");

		if (standortId.equals(hauptstandort.getId())) {
			return Optional.of(hauptstandort);
		}

		return standorte.stream().filter(standort -> standortId.equals(standort.getId())).findAny();
	}

	public void addStandort(final InstitutionStandortEntity standort) {
		this.standorte.add(standort);
	}
}
