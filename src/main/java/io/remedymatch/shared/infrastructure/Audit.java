package io.remedymatch.shared.infrastructure;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@SuperBuilder
@MappedSuperclass
//@EntityListeners(AuditListener.class)
public class Audit {

	@Type(type = "uuid-char")
	@Column(name = "CREATED_BY", nullable = true, updatable = false, length = 36)
	private String createdBy;

	@Column(name = "CREATED_DATE", nullable = false, updatable = false)
	private LocalDateTime createdDate;

	@Type(type = "uuid-char")
	@Column(name = "LAST_MODIFIED_BY", nullable = true, updatable = true, length = 36)
	private UUID lastModifiedBy;
	
	@Column(name = "LAST_MODIFIED_DATE", nullable = true, updatable = true)
	private LocalDateTime lastModifiedDate;

	@PrePersist
	public void prePersist() {
//		createdBy = LoggedUser.get();
		createdDate = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
//		updatedOn = LocalDateTime.now();
//		updatedBy = LoggedUser.get();
	}

	// Getters and setters omitted for brevity
}