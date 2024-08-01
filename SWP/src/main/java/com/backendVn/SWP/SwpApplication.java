package com.backendVn.SWP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SwpApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwpApplication.class, args);
	}

}
