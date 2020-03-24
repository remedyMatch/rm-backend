package io.remedymatch.anfrage.domain;

import io.remedymatch.angebot.domain.AngebotEntity;
import io.remedymatch.bedarf.domain.BedarfEntity;
import io.remedymatch.institution.domain.InstitutionEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class AnfrageEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column
    private String kommentar;

    @ManyToOne(fetch = FetchType.EAGER)
    private InstitutionEntity anfrager;

    @ManyToOne(fetch = FetchType.EAGER)
    private BedarfEntity bedarf;

    @ManyToOne(fetch = FetchType.EAGER)
    private AngebotEntity angebot;

    @Column
    private String prozessInstanzId;

    @Column
    private boolean storniert;

    @Column
    private boolean angenommen;
}
