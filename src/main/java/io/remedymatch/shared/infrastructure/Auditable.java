package io.remedymatch.shared.infrastructure;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter(value = AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@SuperBuilder
@MappedSuperclass
@EntityListeners(MyAuditingEntityListener.class)
public class Auditable {

	@Getter(value = AccessLevel.PUBLIC)
	@Type(type = "uuid-char")
	@Column(name = "CREATED_BY", nullable = true, updatable = false, length = 36)
	private UUID createdBy;

	@Getter(value = AccessLevel.PUBLIC)
	@Column(name = "CREATED_DATE", nullable = false, updatable = false)
	private LocalDateTime createdDate;

	@Getter(value = AccessLevel.PUBLIC)
	@Type(type = "uuid-char")
	@Column(name = "LAST_MODIFIED_BY", nullable = true, updatable = true, length = 36)
	private UUID lastModifiedBy;

	@Getter(value = AccessLevel.PUBLIC)
	@Column(name = "LAST_MODIFIED_DATE", nullable = true, updatable = true)
	private LocalDateTime lastModifiedDate;
}