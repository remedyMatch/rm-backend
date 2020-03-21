package io.remedymatch.angebot.domain;

import io.remedymatch.artikel.domain.ArtikelEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private MengenTyp mengenTyp;

    @ManyToOne(fetch = FetchType.EAGER)
    private ArtikelEntity artikel;

}
