package com.ateaf.tanduritrail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TanduriTrailApplication {

	public static void main(String[] args) {
		SpringApplication.run(TanduriTrailApplication.class, args);
	}

}
