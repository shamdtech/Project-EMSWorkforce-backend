package com.employee.salary.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableDiscoveryClient
@SpringBootApplication
public class EmployeeSalaryServiceApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeSalaryServiceApplication.class, args);
	}

}
