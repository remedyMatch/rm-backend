package io.remedymatch.bedarf.infrastructure;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import io.remedymatch.artikel.domain.ArtikelEntity;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
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
@Entity(name = "Bedarf")
@Table(name = "RM_BEDARF")
public class BedarfEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    @Column(name = "UUID", unique = true, nullable = false, updatable = false)
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
    
    @Column(name = "HALTBARKEIT", nullable = false, updatable = false)
    private LocalDateTime haltbarkeit;

    @Column(name = "STERIL", nullable = false, updatable = false)
    private boolean steril;

    @Column(name = "ORIGINALVERPACKT", nullable = false, updatable = false)
    private boolean originalverpackt;

    @Column(name = "MEDIZINISCH", nullable = false, updatable = false)
    private boolean medizinisch;

    @Column(name = "KOMMENTAR", nullable = false, updatable = false)
    private String kommentar;

    @Column(name = "BEDIENT", nullable = false, updatable = true)
    private boolean bedient;
    
    @OneToMany(fetch = FetchType.LAZY)
    private List<BedarfAnfrageEntity> anfragen;
}
