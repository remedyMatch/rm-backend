package io.remedymatch.institution.domain;

import io.remedymatch.angebot.infrastructure.AngebotEntity;
import io.remedymatch.bedarf.domain.BedarfEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    @Column(name = "UUID", unique = true, nullable = false, updatable = false)
    private UUID id;

    @Column(length = 60)
    private String name;

    @Column(length = 60)
    private String institutionKey;

    @Enumerated(EnumType.STRING)
    private InstitutionTyp typ;

    @OneToMany(fetch = FetchType.LAZY)
    private List<BedarfEntity> bedarfe;

    @OneToMany(fetch = FetchType.LAZY)
    private List<AngebotEntity> angebote;

    @OneToOne(fetch = FetchType.EAGER)
    private InstitutionStandortEntity hauptstandort;

    @OneToMany(fetch = FetchType.EAGER)
    private List<InstitutionStandortEntity> standorte;
}
