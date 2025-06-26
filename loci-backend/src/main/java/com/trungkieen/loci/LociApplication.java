package com.trungkieen.loci;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LociApplication {

	public static void main(String[] args) {
		SpringApplication.run(LociApplication.class, args);
	}

}
