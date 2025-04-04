package it.uniroma3.siw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "it.uniroma3.siw")
@EntityScan(basePackages = "it.uniroma3.siw.model")
public class SiwGroupProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiwGroupProjectApplication.class, args);
		System.out.println("🚀 Spring Boot è partito!");
	}

}
