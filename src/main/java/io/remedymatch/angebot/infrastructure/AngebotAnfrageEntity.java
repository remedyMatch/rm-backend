package io.remedymatch.angebot.infrastructure;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import io.remedymatch.angebot.domain.AngebotAnfrageStatus;
import io.remedymatch.institution.domain.InstitutionEntity;
import io.remedymatch.institution.domain.InstitutionStandortEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity(name = "AngebotAnfrage")
@Table(name = "RM_ANGEBOT_ANFRAGE")
public class AngebotAnfrageEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    @Column(name = "UUID", unique = true, nullable = false, updatable = false)
    private UUID id;
    
    @ManyToOne
    @JoinColumn(name = "ANGEBOT_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
    private AngebotEntity angebot;
    
    @ManyToOne
    @JoinColumn(name = "INSTITUTION_VON_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
    private InstitutionEntity institutionVon;
    
    @ManyToOne
    @JoinColumn(name = "STANDORT_VON_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
    private InstitutionStandortEntity standortVon;
    
    @Column(name = "ANZAHL", nullable = false, updatable = false)
    private BigDecimal anzahl;

    @Column(name = "KOMMENTAR", nullable = false, updatable = false)
    private String kommentar;

    @Column(name = "PROZESS_INSTANZ_ID", nullable = true, updatable = true)
    private String prozessInstanzId;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, updatable = true)
    private AngebotAnfrageStatus status;
}
