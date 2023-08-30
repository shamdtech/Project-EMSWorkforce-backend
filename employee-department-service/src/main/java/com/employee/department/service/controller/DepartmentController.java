package com.employee.department.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.department.service.constants.DatabaseConstants.DepartmentStatus;
import com.employee.department.service.dto.CommonApiResponse;
import com.employee.department.service.dto.DepartmentResponseDto;
import com.employee.department.service.entity.Department;
import com.employee.department.service.resource.DepartmentResource;

@RestController
@RequestMapping("/department/")
@CrossOrigin(origins = "http://localhost:3000")
public class DepartmentController {
	
	@Autowired
	private DepartmentResource departmentResource;
	
	@PostMapping("/add")
	public ResponseEntity<CommonApiResponse> addUserDepartment(@RequestBody Department department) {

		// add the logger here
		System.out.println("request");

		return departmentResource.addDepartment(department);

	}

	@GetMapping("/fetch")
	public ResponseEntity<DepartmentResponseDto> getDepartmentById(@RequestParam("departmentId") int departmentId) {

		// add the logger here
		System.out.println("request");

		return departmentResource.getDepartmentById(departmentId);

	}
	
	@GetMapping("/all")
	public ResponseEntity<DepartmentResponseDto> getAllDepartment() {

		// add the logger here
		System.out.println("request");

		return departmentResource.getAllDepartment();

	}
	
	@PutMapping("/update")
	public ResponseEntity<CommonApiResponse> updateDepartment(@RequestBody Department department) {

		// add the logger here
		System.out.println("request");

		return departmentResource.updateDepartment(department);

	}

	@DeleteMapping("/delete")
	public ResponseEntity<CommonApiResponse> deleteDepartment(@RequestParam("departmentId") int departmentId) {

		// add the logger here
		System.out.println("request");

		return departmentResource.deleteDepartmentById(departmentId);

	}
	

}
