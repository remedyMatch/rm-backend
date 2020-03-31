package io.remedymatch.bedarf.domain;

import io.remedymatch.artikel.domain.ArtikelEntity;
import io.remedymatch.bedarf.domain.anfrage.BedarfAnfrageEntity;
import io.remedymatch.institution.domain.InstitutionEntity;
import io.remedymatch.institution.domain.InstitutionStandortEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BedarfEntity {
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
    private boolean steril;

    @Column
    private boolean originalverpackt;

    @Column
    private boolean medizinisch;

    @Column
    private String kommentar;

    @OneToMany(fetch = FetchType.LAZY)
    private List<BedarfAnfrageEntity> anfragen;

    @Column
    private boolean bedient;

}
