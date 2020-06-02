package io.remedymatch.institution.infrastructure;

import io.remedymatch.shared.infrastructure.Auditable;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity(name = "InstitutionStandort")
@Table(name = "RM_INSTITUTION_STANDORT")
public class InstitutionStandortEntity extends Auditable {
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

    @Column(name = "OEFFENTLICH", nullable = false, updatable = true)
    private boolean oeffentlich;

    @Column(name = "LONGITUDE", nullable = true, updatable = true)
    private BigDecimal longitude;

    @Column(name = "LATITUDE", nullable = true, updatable = true)
    private BigDecimal latitude;
}
