package io.remedymatch.match.infrastructure;

import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.match.domain.MatchStatus;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity(name = "Match")
@Table(name = "RM_MATCH")
public class MatchEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    @Column(name = "UUID", unique = true, nullable = false, updatable = false)
    private UUID id;

    @Column(name = "ANFRAGE_ID", nullable = false, updatable = false)
    private UUID anfrageId;

    @ManyToOne
    @JoinColumn(name = "INSTITUTION_VON_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
    private InstitutionEntity institutionVon;

    @ManyToOne
    @JoinColumn(name = "STANDORT_VON_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
    private MatchStandortEntity standortVon;

    @ManyToOne
    @JoinColumn(name = "INSTITUTION_AN_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
    private InstitutionEntity institutionAn;

    @ManyToOne
    @JoinColumn(name = "STANDORT_AN_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
    private MatchStandortEntity standortAn;

    @Column(name = "KOMMENTAR", nullable = true, updatable = false)
    private String kommentar;

    @Column(name = "PROZESS_INSTANZ_ID", nullable = true, updatable = true)
    private String prozessInstanzId;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, updatable = true)
    private MatchStatus status;
}
