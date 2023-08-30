package com.employee.salary.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.employee.salary.service.dto.CommonApiResponse;
import com.employee.salary.service.dto.SalaryResponseDto;
import com.employee.salary.service.entity.Salary;
import com.employee.salary.service.resource.SalaryResource;

@RestController
@RequestMapping("salary/")
@CrossOrigin(origins = "http://localhost:3000")
public class SalaryController {

	@Autowired
	private SalaryResource salaryResource;

	@PostMapping("/add")
	public ResponseEntity<CommonApiResponse> addUserSalary(@RequestBody Salary salary) {

		// add the logger here
		System.out.println("request");

		return salaryResource.addSalaryForUser(salary);

	}

	@GetMapping("/user/fetch")
	public ResponseEntity<SalaryResponseDto> getUserSalaryDetails(@RequestParam("userId") int userId) {

		// add the logger here
		System.out.println("request");

		return salaryResource.fetchSalaryDetailsUsingUserId(userId);

	}
	
	@GetMapping("/id")
	public ResponseEntity<SalaryResponseDto> getSalaryDetailById(@RequestParam("salaryId") int salaryId) {

		// add the logger here
		System.out.println("request");

		return salaryResource.fetchSalaryDetailsUsingSalaryId(salaryId);

	}
	
	@PutMapping("/update")
	public ResponseEntity<CommonApiResponse> updateUserSalary(@RequestBody Salary salary) {

		// add the logger here
		System.out.println("request");

		return salaryResource.updateSalaryForUser(salary);

	}

}
