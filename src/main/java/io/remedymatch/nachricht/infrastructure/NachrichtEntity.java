package io.remedymatch.nachricht.infrastructure;

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
@Entity(name = "Nachricht")
@Table(name = "RM_NACHRICHT")
public class NachrichtEntity extends Auditable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    @Column(name = "UUID", unique = true, nullable = false, updatable = false, length = 36)
    private UUID id;

    @Column(name = "NACHRICHT", nullable = false, updatable = true, length = 1024)
    private String nachricht;

    @Type(type = "uuid-char")
    @Column(name = "KONVERSATION_UUID", nullable = false, updatable = false, length = 36)
    private UUID konversation;

    @Type(type = "uuid-char")
    @Column(name = "INSTITUTION_UUID", nullable = false, updatable = false, length = 36)
    private UUID erstellerInstitution;

}
