package com.example.Electrical.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.Electrical.store")
public class ElectricalStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectricalStoreApplication.class, args);
	}
}
