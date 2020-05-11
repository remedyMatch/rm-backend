package io.remedymatch.institution.infrastructure;

import io.remedymatch.institution.domain.model.InstitutionTyp;
import io.remedymatch.shared.infrastructure.Auditable;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity(name = "Institution")
@Table(name = "RM_INSTITUTION")
public class InstitutionEntity extends Auditable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    @Column(name = "UUID", unique = true, nullable = false, updatable = false, length = 36)
    private UUID id;

    @Column(name = "NAME", nullable = true, updatable = true, length = 64)
    private String name;

    @Column(name = "INSTITUTION_KEY", nullable = false, updatable = true, length = 64)
    private String institutionKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYP", nullable = true, updatable = true, length = 64)
    private InstitutionTyp typ;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "RM_INSTITUTION_2_STANDORT", //
            joinColumns = @JoinColumn(name = "INSTITUTION_UUID"), //
            inverseJoinColumns = @JoinColumn(name = "INSTITUTION_STANDORT_UUID"))
    @Builder.Default
    private List<InstitutionStandortEntity> standorte = new ArrayList<>();

    public Optional<InstitutionStandortEntity> findStandort(final UUID standortId) {
        Assert.notNull(standortId, "StandortId ist null.");

        return standorte.stream().filter(standort -> standortId.equals(standort.getId())).findAny();
    }

    public void addStandort(final InstitutionStandortEntity standort) {
        this.standorte.add(standort);
    }
}
