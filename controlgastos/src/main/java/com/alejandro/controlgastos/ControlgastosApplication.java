package com.alejandro.controlgastos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:messages.properties")
@SpringBootApplication
public class ControlgastosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControlgastosApplication.class, args);
	}

}
