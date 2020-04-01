package io.remedymatch.institution.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import io.remedymatch.institution.domain.InstitutionTyp;
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
@Entity(name = "Institution2")
@Table(name = "RM_INSTITUTION")
public class InstitutionEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    @Column(name = "UUID", unique = true, nullable = false, updatable = false)
    private UUID id;

    @Column(name = "NAME", nullable = false, updatable = true, length = 64)
    private String name;

    @Column(name = "INSTITUTION_KEY", nullable = false, updatable = true, length = 64)
    private String institutionKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYP", nullable = false, updatable = true)
    private InstitutionTyp typ;

    @OneToOne
    @JoinColumn(name = "HAUPTSTANDORT_UUID", referencedColumnName = "UUID", nullable = true, updatable = true)
    private InstitutionStandortEntity hauptstandort;

    @OneToMany
    @Builder.Default
    private List<InstitutionStandortEntity> standorte = new ArrayList<>();
}
