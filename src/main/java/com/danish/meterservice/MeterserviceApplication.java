package com.danish.meterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MeterserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeterserviceApplication.class, args);
	}

}
