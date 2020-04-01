package io.remedymatch.person.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import io.remedymatch.institution.infrastructure.InstitutionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @Column(length = 60)
    private String username;

    @Column(length = 60)
    private String vorname;

    @Column(length = 60)
    private String nachname;

    @Column(length = 20)
    private String telefon;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private InstitutionEntity institution;
}
