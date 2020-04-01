package io.remedymatch.angebot.infrastructure;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class AngebotAnfrageEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private AngebotEntity angebot;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private InstitutionEntity institutionVon;
    
    @ManyToOne
    private InstitutionStandortEntity standortVon;
    
    @Column
    private BigDecimal anzahl;

    @Column
    private String kommentar;

    @Column
    private String prozessInstanzId;

    @Enumerated(EnumType.STRING)
    private AngebotAnfrageStatus status;
}
