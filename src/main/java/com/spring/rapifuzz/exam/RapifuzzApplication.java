package com.spring.rapifuzz.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration.class})
public class RapifuzzApplication {

	public static void main(String[] args) {
		SpringApplication.run(RapifuzzApplication.class, args);
	}

}
