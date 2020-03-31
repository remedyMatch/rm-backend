package io.remedymatch.angebot.infrastructure;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import io.remedymatch.artikel.domain.ArtikelEntity;
import io.remedymatch.institution.domain.InstitutionEntity;
import io.remedymatch.institution.domain.InstitutionStandortEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class AngebotEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @Column
    private BigDecimal anzahl;

    @Column
    private BigDecimal rest;

    @ManyToOne(fetch = FetchType.EAGER)
    private ArtikelEntity artikel;

    @ManyToOne(fetch = FetchType.EAGER)
    private InstitutionEntity institution;

    @ManyToOne(fetch = FetchType.EAGER)
    private InstitutionStandortEntity standort;

    @Column
    private LocalDateTime haltbarkeit;

    @Column
    private boolean steril;

    @Column
    private boolean originalverpackt;

    @Column
    private boolean medizinisch;

    @Column
    private String kommentar;

    @Column
    private boolean bedient;

    @OneToMany(fetch = FetchType.LAZY)
    private List<AngebotAnfrageEntity> anfragen;

}
