package io.remedymatch.angebot.infrastructure;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import io.remedymatch.artikel.infrastructure.ArtikelVarianteEntity;
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
@Entity(name = "Angebot")
@Table(name = "RM_ANGEBOT")
public class AngebotEntity extends Auditable {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Type(type = "uuid-char")
	@Column(name = "UUID", unique = true, nullable = false, updatable = false, length = 36)
	private UUID id;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "ARTIKEL_VARIANTE_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
	private ArtikelVarianteEntity artikelVariante;

	@Column(name = "ANZAHL", nullable = false, updatable = false)
	private BigDecimal anzahl;

	@Column(name = "REST", nullable = false, updatable = true)
	private BigDecimal rest;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "INSTITUTION_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
	private InstitutionEntity institution;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "STANDORT_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
	private InstitutionStandortEntity standort;

	@Column(name = "HALTBARKEIT", nullable = false, updatable = false)
	private LocalDateTime haltbarkeit;

	@Column(name = "STERIL", nullable = false, updatable = false)
	private boolean steril;

	@Column(name = "ORIGINALVERPACKT", nullable = false, updatable = false)
	private boolean originalverpackt;

	@Column(name = "MEDIZINISCH", nullable = false, updatable = false)
	private boolean medizinisch;

	@Column(name = "KOMMENTAR", nullable = false, updatable = false, length = 1024)
	private String kommentar;

	@Column(name = "BEDIENT", nullable = false, updatable = true)
	private boolean bedient;

	@Column(name = "DELETED", nullable = true, updatable = true)
	private boolean deleted;
}
