package com.self.house.renting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class HouseRentingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HouseRentingApplication.class, args);
	}

}
