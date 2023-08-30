package com.employee.service.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EmployeeServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeServiceRegistryApplication.class, args);
	}

}
