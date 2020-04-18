package io.remedymatch.angebot.infrastructure;

import java.math.BigDecimal;
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

import io.remedymatch.angebot.domain.model.AngebotAnfrageStatus;
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
@Entity(name = "AngebotAnfrage")
@Table(name = "RM_ANGEBOT_ANFRAGE")
public class AngebotAnfrageEntity extends Auditable {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Type(type = "uuid-char")
	@Column(name = "UUID", unique = true, nullable = false, updatable = false, length = 36)
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "ANGEBOT_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
	private AngebotEntity angebot;

	@ManyToOne
	@JoinColumn(name = "INSTITUTION_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
	private InstitutionEntity institution;

	@ManyToOne
	@JoinColumn(name = "STANDORT_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
	private InstitutionStandortEntity standort;

	@Column(name = "ANZAHL", nullable = false, updatable = false)
	private BigDecimal anzahl;

	@Column(name = "KOMMENTAR", nullable = false, updatable = false, length = 256)
	private String kommentar;

	@Column(name = "PROZESS_INSTANZ_ID", nullable = true, updatable = true, length = 64)
	private String prozessInstanzId;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, updatable = true, length = 64)
	private AngebotAnfrageStatus status;
}
