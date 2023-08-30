package com.employee.department.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EmployeeDepartmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeDepartmentServiceApplication.class, args);
	}

}
