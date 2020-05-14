package io.remedymatch.nachricht.infrastructure;

import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.shared.infrastructure.Auditable;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity(name = "Konversation2Institution")
@Table(name = "RM_KONVERSATION_2_INSTITUTION")
public class Konversation2InstitutionEntity extends Auditable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    @Column(name = "UUID", unique = true, nullable = false, updatable = false, length = 36)
    private UUID id;

    @Type(type = "uuid-char")
    @Column(name = "KONVERSATION_UUID", nullable = false, updatable = false, length = 36)
    private UUID konversation;

    @ManyToOne
    @JoinColumn(name = "INSTITUTION_UUID", referencedColumnName = "UUID", nullable = false, updatable = false)
    private InstitutionEntity institution;

}
