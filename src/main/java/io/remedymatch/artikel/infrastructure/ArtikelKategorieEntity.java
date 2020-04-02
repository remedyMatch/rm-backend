package io.remedymatch.artikel.infrastructure;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity(name = "ArtikelKategorie")
@Table(name = "RM_ARTIKEL_KATEGORIE")
public class ArtikelKategorieEntity {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Type(type = "uuid-char")
	@Column(name = "UUID", unique = true, nullable = false, updatable = false)
	private UUID id;

	@Column(name = "NAME", nullable = false, updatable = true, length = 256)
	private String name;

	@OneToMany(mappedBy = "artikelKategorie")
	private List<ArtikelEntity> artikel;
}
