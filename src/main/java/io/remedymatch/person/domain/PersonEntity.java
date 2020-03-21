package io.remedymatch.person.domain;

import io.remedymatch.institution.domain.InstitutionEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

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
