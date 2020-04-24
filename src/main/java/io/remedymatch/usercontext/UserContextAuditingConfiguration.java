package io.remedymatch.usercontext;

//import java.util.Optional;
//import java.util.UUID;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;

//@AllArgsConstructor
//@Configuration
//@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
//@Profile("!testauditing")
//@Slf4j
class UserContextAuditingConfiguration {

//	private final UserContextService userContextService;
//
//	@Bean
//	AuditorAware<UUID> auditorProvider() {
//		return () -> {
//
//			// falls nicht authenticated, dann System ...
//			if (SecurityContextHolder.getContext().getAuthentication() == null) {
//				return Optional.empty();
//			}
//
//			try {
//				return Optional.ofNullable(userContextService.getContextUserId().getValue());
//			} catch (Exception e) {
//				log.error("Unable to find contextUser");
//				return Optional.empty();
//			}
//		};
//	}
}