package io.remedymatch.shared.infrastructure;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.context.annotation.Configuration;

import io.remedymatch.person.domain.model.Person;
import io.remedymatch.usercontext.UserContext;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Configuration
@Log4j2
public class MyAuditingEntityListener {

	@PrePersist
	public void touchForCreate(final Auditable auditable) {

		log.info("PrePersist: " + auditable);

		auditable.setCreatedBy(getUserContextId());
		auditable.setCreatedDate(LocalDateTime.now());
	}

	@PreUpdate
	public void touchForUpdate(final Auditable auditable) {

		log.info("PreUpdate: " + auditable);

		auditable.setLastModifiedBy(getUserContextId());
		auditable.setLastModifiedDate(LocalDateTime.now());
	}

	private UUID getUserContextId() {
		Person contextUser = UserContext.getContextUser();
		if (contextUser != null) {
			return contextUser.getId().getValue();
		}

		return null;
	}
}
