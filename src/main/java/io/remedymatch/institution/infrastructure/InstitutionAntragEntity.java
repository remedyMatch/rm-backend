package io.remedymatch.institution.infrastructure;

import io.remedymatch.institution.domain.model.InstitutionAntragStatus;
import io.remedymatch.institution.domain.model.InstitutionRolle;
import io.remedymatch.institution.domain.model.InstitutionTyp;
import io.remedymatch.shared.infrastructure.Auditable;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity(name = "InstitutionAntrag")
@Table(name = "RM_INSTITUTION_ANTRAG")
public class InstitutionAntragEntity extends Auditable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    @Column(name = "UUID", unique = true, nullable = false, updatable = false, length = 36)
    private UUID id;

    @Column(name = "NAME", nullable = false, updatable = true, length = 64)
    private String name;

    @Column(name = "STRASSE", nullable = false, updatable = true, length = 64)
    private String strasse;

    @Column(name = "HAUSNUMMER", nullable = false, updatable = true, length = 16)
    private String hausnummer;

    @Column(name = "PLZ", nullable = false, updatable = true, length = 8)
    private String plz;

    @Column(name = "ORT", nullable = false, updatable = true, length = 64)
    private String ort;

    @Column(name = "LAND", nullable = false, updatable = true, length = 64)
    private String land;

    @Column(name = "WEBSEITE", nullable = true, updatable = true, length = 128)
    private String webseite;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "INSTITUTIONTYP", nullable = false, length = 64)
    private InstitutionTyp institutionTyp;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "ROLLE", nullable = false, updatable = false, length = 64)
    private InstitutionRolle rolle;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "STATUS", nullable = false, updatable = false, length = 64)
    private InstitutionAntragStatus status;

    @Column(name = "PERSON_UUID", nullable = false, updatable = false, length = 64)
    @Type(type = "uuid-char")
    private UUID antragsteller;

}
