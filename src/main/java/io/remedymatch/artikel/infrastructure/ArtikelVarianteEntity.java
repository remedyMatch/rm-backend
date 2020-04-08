package io.remedymatch.artikel.infrastructure;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

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
@Entity(name = "ArtikelVariante")
@Table(name = "RM_ARTIKEL_VARIANTE")
public class ArtikelVarianteEntity {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Type(type = "uuid-char")
	@Column(name = "UUID", unique = true, nullable = false, updatable = false, length = 36)
	private UUID id;

	@Type(type = "uuid-char")
	@Column(name = "ARTIKEL_UUID", nullable = false, updatable = false, length = 36)
	private UUID artikel;

	@Column(name = "VARIANTE", nullable = false, updatable = true, length = 64)
	private String variante;

	@Column(name = "NORM", nullable = true, updatable = true, length = 1024)
	private String norm;

	@Column(name = "BESCHREIBUNG", nullable = false, updatable = true, length = 1024)
	private String beschreibung;

	@Column(name = "MEDIZINISCH_AUSWAEHLBAR", nullable = false, updatable = true)
	private boolean medizinischAuswaehlbar;
}
