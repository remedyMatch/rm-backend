package io.remedymatch.angebot.domain;

import io.remedymatch.artikel.domain.ArtikelEntity;
import io.remedymatch.institution.domain.InstitutionEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @ManyToOne(fetch = FetchType.EAGER)
    private ArtikelEntity artikel;

    @ManyToOne(fetch = FetchType.EAGER)
    private InstitutionEntity institution;

    @Column
    private String standort;

    @Column
    private LocalDateTime gueltigkeit;

    @Column
    private boolean steril;

    @Column
    private boolean originalverpackt;

    @Column
    private boolean medizinisch;

}
