package io.remedymatch.match.domain;

import io.remedymatch.institution.domain.InstitutionEntity;
import io.remedymatch.person.domain.PersonEntity;
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
public class MatchEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column
    private String kommentar;

    @ManyToOne(fetch = FetchType.EAGER)
    private InstitutionEntity institutionVon;

    @Column
    private String adresseVon;

    @ManyToOne(fetch = FetchType.EAGER)
    private InstitutionEntity institutionAn;

    @Column
    private String adresseZu;

    @ManyToOne(fetch = FetchType.EAGER)
    private PersonEntity lieferant;

    @Column
    private boolean bedient;

    @Column
    private UUID anfrageId;
}
