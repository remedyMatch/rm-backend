package io.remedymatch.nachricht.infrastructure;

import io.remedymatch.nachricht.domain.model.NachrichtReferenzTyp;
import io.remedymatch.shared.infrastructure.Auditable;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity(name = "Konversation")
@Table(name = "RM_KONVERSATION")
public class KonversationEntity extends Auditable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    @Column(name = "UUID", unique = true, nullable = false, updatable = false, length = 36)
    private UUID id;

    @Type(type = "uuid-char")
    @Column(name = "REFERENZ_ID", nullable = false, updatable = true, length = 36)
    private UUID referenzId;

    @Enumerated(EnumType.STRING)
    @Column(name = "REFERENZ_TYP", nullable = false, updatable = true, length = 64)
    private NachrichtReferenzTyp referenzTyp;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "konversation", orphanRemoval = true)
    @Builder.Default
    private List<Konversation2InstitutionEntity> beteiligte = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "konversation", fetch = FetchType.EAGER)
    @Builder.Default
    private List<NachrichtEntity> nachrichten = new ArrayList<>();
}
