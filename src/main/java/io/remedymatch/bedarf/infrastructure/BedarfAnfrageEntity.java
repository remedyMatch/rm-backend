package io.remedymatch.bedarf.infrastructure;

import io.remedymatch.bedarf.domain.model.BedarfAnfrageStatus;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
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
@Entity(name = "BedarfAnfrage")
@Table(name = "RM_BEDARF_ANFRAGE")
public class BedarfAnfrageEntity extends Auditable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    @Column(name = "UUID", unique = true, nullable = false, updatable = false, length = 36)
    private UUID id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "BEDARF_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
    private BedarfEntity bedarf;

    @Type(type = "uuid-char")
    @Column(name = "ANGEBOT_UUID", nullable = false, updatable = false, length = 36)
    private UUID angebotId;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "INSTITUTION_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
    private InstitutionEntity institution;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "STANDORT_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
    private InstitutionStandortEntity standort;

    @Column(name = "ANZAHL", nullable = false, updatable = false)
    private BigDecimal anzahl;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, updatable = true, length = 64)
    private BedarfAnfrageStatus status;
}
