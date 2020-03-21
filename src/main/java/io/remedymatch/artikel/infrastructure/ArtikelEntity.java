package io.remedymatch.artikel.infrastructure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class ArtikelEntity {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "id", nullable = false, updatable = false)
	private UUID id;

	@Column(name = "EAN", nullable = false, updatable = false)
	private String ean;

	@Column(name = "NAME", nullable = false, updatable = true)
	private String name;

	@Column(name = "description", nullable = false, updatable = true)
	private String beschreibung;

	@Column(name = "manufacturer", nullable = false, updatable = true)
	private String hersteller;
}