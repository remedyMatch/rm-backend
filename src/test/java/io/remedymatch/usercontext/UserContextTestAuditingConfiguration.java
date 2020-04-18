package io.remedymatch.usercontext;

import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@Profile("test")
class UserContextTestAuditingConfiguration {

	@Bean
	AuditorAware<UUID> auditorProvider() {
		return () -> Optional.ofNullable(UUID.randomUUID());
	}
}