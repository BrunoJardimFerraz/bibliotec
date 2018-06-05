package com.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BibliotecaEtecApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaEtecApplication.class, args);
		//System.out.println(new BCryptPasswordEncoder().encode("1111"));
	}
}
