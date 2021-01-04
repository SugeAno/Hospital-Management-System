package com.example.ehospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EhospitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(EhospitalApplication.class, args);
	}

}
