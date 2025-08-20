package com.yugal.jwt_pgsql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JwtPgsqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtPgsqlApplication.class, args);
		System.out.println("Server running on port : 8080");
	}

}
