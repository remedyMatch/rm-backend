package io.remedymatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
public class RmBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RmBeApplication.class, args);
	}

}
