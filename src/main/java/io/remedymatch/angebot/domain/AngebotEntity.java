package io.remedymatch.angebot.domain;

import io.remedymatch.angebot.domain.anfrage.AngebotAnfrageEntity;
import io.remedymatch.artikel.domain.ArtikelEntity;
import io.remedymatch.institution.domain.InstitutionEntity;
import io.remedymatch.institution.domain.InstitutionStandortEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AngebotEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @Column
    private double anzahl;

    @Column
    private double rest;

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
