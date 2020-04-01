package io.remedymatch.artikel.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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
    @Type(type = "uuid-char")
    @Column(name = "UUID", unique = true, nullable = false, updatable = false)
    private UUID id;

    @Column(name = "EAN", nullable = false, updatable = false)
    private String ean;

    @Column(name = "NAME", nullable = false, updatable = true)
    private String name;

    @Column(name = "description", nullable = false, updatable = true)
    private String beschreibung;

    @Column(name = "manufacturer", nullable = false, updatable = true)
    private String hersteller;

    @ManyToOne
    private ArtikelKategorieEntity artikelKategorie;
}