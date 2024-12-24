package com.ateaf.tanduritrial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TanduriTrialApplication {

	public static void main(String[] args) {
		SpringApplication.run(TanduriTrialApplication.class, args);
	}

}
