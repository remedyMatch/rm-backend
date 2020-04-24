package io.remedymatch.match.infrastructure;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.CascadeType;
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
@Entity(name = "Match")
@Table(name = "RM_MATCH")
public class MatchEntity extends Auditable {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Type(type = "uuid-char")
	@Column(name = "UUID", unique = true, nullable = false, updatable = false, length = 36)
	private UUID id;

	@Type(type = "uuid-char")
	@Column(name = "ANFRAGE_ID", nullable = false, updatable = false, length = 36)
	private UUID anfrageId;

	@Type(type = "uuid-char")
	@Column(name = "ARTIKEL_ID", nullable = false, updatable = false, length = 36)
	private UUID artikelId;

	@Type(type = "uuid-char")
	@Column(name = "ARTIKEL_VARIANTE_ID", nullable = true, updatable = false, length = 36)
	private UUID artikelVarianteId;

	@Column(name = "ANZAHL", nullable = false, updatable = false)
	private BigDecimal anzahl;

	@Column(name = "ANFRAGE_TYP", nullable = true, updatable = true, length = 64)
	private String anfrageTyp;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "INSTITUTION_VON_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
	private InstitutionEntity institutionVon;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "STANDORT_VON_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
	private MatchStandortEntity standortVon;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "INSTITUTION_AN_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
	private InstitutionEntity institutionAn;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "STANDORT_AN_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
	private MatchStandortEntity standortAn;

	@Column(name = "KOMMENTAR", nullable = true, updatable = false, length = 256)
	private String kommentar;

	@Column(name = "PROZESS_INSTANZ_ID", nullable = true, updatable = true, length = 64)
	private String prozessInstanzId;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, updatable = true, length = 64)
	private MatchStatus status;

}
