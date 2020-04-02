package io.remedymatch.match.infrastructure;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.match.domain.MatchStatus;
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
@Entity(name = "Match")
@Table(name = "RM_MATCH")
public class MatchEntity {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Type(type = "uuid-char")
	@Column(name = "UUID", unique = true, nullable = false, updatable = false, length = 36)
	private UUID id;

	@Type(type = "uuid-char")
	@Column(name = "ANFRAGE_ID", nullable = false, updatable = false, length = 36)
	private UUID anfrageId;

	@ManyToOne
	@JoinColumn(name = "INSTITUTION_VON_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
	private InstitutionEntity institutionVon;

	@ManyToOne
	@JoinColumn(name = "STANDORT_VON_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
	private MatchStandortEntity standortVon;

	@ManyToOne
	@JoinColumn(name = "INSTITUTION_AN_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
	private InstitutionEntity institutionAn;

	@ManyToOne
	@JoinColumn(name = "STANDORT_AN_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
	private MatchStandortEntity standortAn;

	@Column(name = "KOMMENTAR", nullable = false, updatable = false, length = 256)
	private String kommentar;

	@Column(name = "PROZESS_INSTANZ_ID", nullable = true, updatable = true, length = 64)
	private String prozessInstanzId;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, updatable = true, length = 64)
	private MatchStatus status;
}
