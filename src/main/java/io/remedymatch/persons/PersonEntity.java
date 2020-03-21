package io.remedymatch.persons;

import io.remedymatch.institutions.InstitutionEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
public class PersonEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id = null;

    @Column(length = 60)
    private String userName;

    @Column(length = 60)
    private String firstName;

    @Column(length = 60)
    private String lastName;

    @Column(length = 20)
    private String telephoneNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    private InstitutionEntity institution;
}
