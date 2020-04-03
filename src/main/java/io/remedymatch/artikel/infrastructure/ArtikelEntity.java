package io.remedymatch.artikel.infrastructure;

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
@Entity(name = "Artikel")
@Table(name = "RM_ARTIKEL")
public class ArtikelEntity {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Type(type = "uuid-char")
	@Column(name = "UUID", unique = true, nullable = false, updatable = false, length = 36)
	private UUID id;

	@Column(name = "EAN", nullable = false, updatable = false, length = 34)
	private String ean;

	@Column(name = "NAME", nullable = false, updatable = true, length = 128)
	private String name;

	@Column(name = "description", nullable = false, updatable = true, length = 256)
	private String beschreibung;

	@Column(name = "manufacturer", nullable = false, updatable = true, length = 128)
	private String hersteller;

	@ManyToOne
	@JoinColumn(name = "ARTIKEL_KATEGORIE_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
	private ArtikelKategorieEntity artikelKategorie;
}