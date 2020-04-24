package io.remedymatch.artikel.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import io.remedymatch.shared.infrastructure.Auditable;
import lombok.AccessLevel;
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
@Entity(name = "Artikel")
@Table(name = "RM_ARTIKEL")
public class ArtikelEntity extends Auditable {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Type(type = "uuid-char")
	@Column(name = "UUID", unique = true, nullable = false, updatable = false, length = 36)
	private UUID id;

	@Type(type = "uuid-char")
	@Column(name = "ARTIKEL_KATEGORIE_UUID", nullable = false, updatable = false, length = 36)
	private UUID artikelKategorie;

	@Column(name = "NAME", nullable = false, updatable = true, length = 64)
	private String name;

	@Column(name = "BESCHREIBUNG", nullable = false, updatable = true, length = 1024)
	private String beschreibung;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "artikel")
	@Builder.Default
	@Setter(AccessLevel.PACKAGE)
	private List<ArtikelVarianteEntity> varianten = new ArrayList<>();

}
