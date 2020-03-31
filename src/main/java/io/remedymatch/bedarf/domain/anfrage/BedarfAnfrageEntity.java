package io.remedymatch.bedarf.domain.anfrage;

import io.remedymatch.bedarf.domain.BedarfEntity;
import io.remedymatch.institution.domain.InstitutionEntity;
import io.remedymatch.institution.domain.InstitutionStandortEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
public class BedarfAnfrageEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column
    private String kommentar;

    @ManyToOne(fetch = FetchType.EAGER)
    private InstitutionEntity institutionVon;

    @ManyToOne(fetch = FetchType.EAGER)
    private InstitutionEntity institutionAn;

    @ManyToOne(fetch = FetchType.EAGER)
    private BedarfEntity bedarf;

    @ManyToOne
    private InstitutionStandortEntity standortVon;

    @ManyToOne
    private InstitutionStandortEntity standortAn;

    @Column
    private String prozessInstanzId;

    @Column
    private double anzahl;

    @Enumerated(EnumType.STRING)
    private BedarfAnfrageStatus status;

}
