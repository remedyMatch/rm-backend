package io.remedymatch.institution.domain;

import io.remedymatch.angebot.domain.AngebotEntity;
import io.remedymatch.bedarf.domain.BedarfEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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

    @Column
    private String standort;
}
