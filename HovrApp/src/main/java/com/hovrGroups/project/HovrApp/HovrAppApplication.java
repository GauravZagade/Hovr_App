package com.hovrGroups.project.HovrApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HovrAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(HovrAppApplication.class, args);
	}

}
