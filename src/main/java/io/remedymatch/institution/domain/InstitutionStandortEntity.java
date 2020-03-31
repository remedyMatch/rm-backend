package io.remedymatch.institution.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionStandortEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @Column
    private String plz;

    @Column
    private String ort;

    @Column
    private String strasse;

    @Column
    private String land;

    @Column
    private double longitude;

    @Column
    private double latitude;

}
