package io.remedymatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.client.RestTemplate;

import io.remedymatch.properties.EngineProperties;
import io.remedymatch.properties.GeodatenProperties;
import io.remedymatch.properties.KeycloakProperties;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableConfigurationProperties({ EngineProperties.class, GeodatenProperties.class, KeycloakProperties.class })
@EnableScheduling
public class RmBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RmBeApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
