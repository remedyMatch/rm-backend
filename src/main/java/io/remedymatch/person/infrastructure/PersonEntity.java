package io.remedymatch.person.infrastructure;

import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import io.remedymatch.shared.infrastructure.Auditable;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity(name = "Person")
@Table(name = "RM_PERSON")
public class PersonEntity extends Auditable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    @Column(name = "UUID", unique = true, nullable = false, updatable = false, length = 36)
    private UUID id;

    @Column(name = "USERNAME", nullable = false, updatable = false, length = 64)
    private String username;

    @Column(name = "VORNAME", nullable = true, updatable = true, length = 64)
    private String vorname;

    @Column(name = "NACHNAME", nullable = true, updatable = true, length = 64)
    private String nachname;

    @Column(name = "EMAIL", nullable = true, updatable = true, length = 64)
    private String email;

    @Column(name = "TELEFON", nullable = true, updatable = true, length = 32)
    private String telefon;

    @Type(type = "uuid-char")
    @Column(name = "INSTITUTION_UUID", nullable = true, updatable = true, length = 36)
    @Deprecated // wird entfernt
    private UUID institutionId;

    @Type(type = "uuid-char")
    @Column(name = "STANDORT_UUID", nullable = true, updatable = true, length = 36)
    @Deprecated // wird entfernt
    private UUID standortId;

    @OneToOne
    @JoinColumn(name = "AKTUELLE_INSTITUTION_UUID", referencedColumnName = "UUID", nullable = true, updatable = true)
    private PersonInstitutionEntity aktuelleInstitution;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    @Builder.Default
    private List<PersonInstitutionEntity> institutionen = new ArrayList<>();

    public void addNeueAktuelleInstitution(final InstitutionEntity institution,
                                           final InstitutionStandortEntity standort) {

        aktuelleInstitution = PersonInstitutionEntity.builder() //
                .person(this.id) //
                .institution(institution) //
                .standort(standort) //
                .build();
        institutionen.add(aktuelleInstitution);
    }

    public void addNeueInstitution(final InstitutionEntity institution) {
        institutionen.add(PersonInstitutionEntity.builder() //
                .person(this.id) //
                .institution(institution) //
                .standort(institution.getHauptstandort()) //
                .build());
    }

}
