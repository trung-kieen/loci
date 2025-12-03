package com.loci.loci_backend;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication
public class LociApplication {

	public static void main(String[] args) {
		SpringApplication.run(LociApplication.class, args);
	}

}
