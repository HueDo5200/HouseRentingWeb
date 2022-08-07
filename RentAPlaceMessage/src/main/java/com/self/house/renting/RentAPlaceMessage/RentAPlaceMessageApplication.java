package com.self.house.renting.RentAPlaceMessage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class RentAPlaceMessageApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentAPlaceMessageApplication.class, args);
	}



}
