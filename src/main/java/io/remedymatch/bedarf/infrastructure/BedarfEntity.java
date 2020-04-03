package io.remedymatch.bedarf.infrastructure;

import io.remedymatch.artikel.infrastructure.ArtikelEntity;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity(name = "Bedarf")
@Table(name = "RM_BEDARF")
public class BedarfEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    @Column(name = "UUID", unique = true, nullable = false, updatable = false, length = 36)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "ARTIKEL_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
    private ArtikelEntity artikel;

    @ManyToOne
    @JoinColumn(name = "INSTITUTION_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
    private InstitutionEntity institution;

    @ManyToOne
    @JoinColumn(name = "STANDORT_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
    private InstitutionStandortEntity standort;

    @Column(name = "ANZAHL", nullable = false, updatable = false)
    private BigDecimal anzahl;

    @Column(name = "REST", nullable = false, updatable = true)
    private BigDecimal rest;

    @Column(name = "STERIL", nullable = false, updatable = false)
    private boolean steril;

    @Column(name = "ORIGINALVERPACKT", nullable = false, updatable = false)
    private boolean originalverpackt;

    @Column(name = "MEDIZINISCH", nullable = false, updatable = false)
    private boolean medizinisch;

    @Column(name = "KOMMENTAR", nullable = false, updatable = false, length = 256)
    private String kommentar;

    @Column(name = "BEDIENT", nullable = false, updatable = true)
    private boolean bedient;

    @Column(name = "DELETED", nullable = true, updatable = true)
    private boolean deleted;

    @OneToMany(fetch = FetchType.LAZY)
    private List<BedarfAnfrageEntity> anfragen;
}
