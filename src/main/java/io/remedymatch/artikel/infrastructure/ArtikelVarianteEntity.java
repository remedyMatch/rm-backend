package io.remedymatch.artikel.infrastructure;

import io.remedymatch.shared.infrastructure.Auditable;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity(name = "ArtikelVariante")
@Table(name = "RM_ARTIKEL_VARIANTE", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"ARTIKEL_UUID", "VARIANTE"}),
        @UniqueConstraint(columnNames = {"ARTIKEL_UUID", "SORT"})})
public class ArtikelVarianteEntity extends Auditable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    @Column(name = "UUID", unique = true, nullable = false, updatable = false, length = 36)
    private UUID id;

    @Type(type = "uuid-char")
    @Column(name = "ARTIKEL_UUID", nullable = false, updatable = false, length = 36)
    private UUID artikel;

    @Column(name = "SORT", nullable = true, updatable = true)
    private Integer sort;

    @Column(name = "VARIANTE", nullable = false, updatable = true, length = 64)
    private String variante;

    @Column(name = "NORM", nullable = true, updatable = true, length = 1024)
    private String norm;

    @Column(name = "BESCHREIBUNG", nullable = false, updatable = true, length = 1024)
    private String beschreibung;

    @Column(name = "MEDIZINISCH_AUSWAEHLBAR", nullable = false, updatable = true)
    private boolean medizinischAuswaehlbar;
}
