package com.example.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BookHouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookHouseApplication.class, args);
	}
}