package io.remedymatch;

import io.remedymatch.properties.RmBackendProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableConfigurationProperties({RmBackendProperties.class})
public class RmBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(RmBeApplication.class, args);
    }

}
