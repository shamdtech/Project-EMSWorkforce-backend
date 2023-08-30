package com.employee.department.service.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.employee.department.service.constants.DatabaseConstants.DepartmentStatus;
import com.employee.department.service.dto.CommonApiResponse;
import com.employee.department.service.dto.DepartmentResponseDto;
import com.employee.department.service.entity.Department;
import com.employee.department.service.services.DepartmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DepartmentResource {

	@Autowired
	private DepartmentService departmentService;

	ObjectMapper objectMapper = new ObjectMapper();

	public ResponseEntity<CommonApiResponse> addDepartment(Department department) {

		CommonApiResponse response = new CommonApiResponse();

		if (department == null) {
			response.setResponseMessage("department is null");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		department.setStatus(DepartmentStatus.ACTIVE.value());
		
		Department addedDepartment = this.departmentService.addDepartment(department);

		if (addedDepartment == null) {
			response.setResponseMessage("Failed to add Department");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		response.setResponseMessage("Department added Successfully");
		response.setSuccess(true);

		// Convert the object to a JSON string
		String jsonString = null;
		try {
			jsonString = objectMapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(jsonString);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);

	}

	public ResponseEntity<DepartmentResponseDto> getDepartmentById(int departmentId) {

		DepartmentResponseDto response = new DepartmentResponseDto();

		if (departmentId == 0) {
			response.setResponseMessage("department id is null");
			response.setSuccess(true);

			return new ResponseEntity<DepartmentResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		Department addedDepartment = this.departmentService.getDepartmentById(departmentId);

		if (addedDepartment == null) {
			response.setResponseMessage("Failed to get the Department");
			response.setSuccess(true);

			return new ResponseEntity<DepartmentResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		response.setDepartment(Arrays.asList(addedDepartment));
		response.setResponseMessage("Department added Successfully");
		response.setSuccess(true);

		// Convert the object to a JSON string
		String jsonString = null;
		try {
			jsonString = objectMapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(jsonString);

		return new ResponseEntity<DepartmentResponseDto>(response, HttpStatus.OK);

	}

	public ResponseEntity<DepartmentResponseDto> getAllDepartment() {

		DepartmentResponseDto response = new DepartmentResponseDto();

		List<Department> departments = new ArrayList<>();
		departments = this.departmentService.getAllActiveDepartment(DepartmentStatus.ACTIVE.value());

		response.setDepartment(departments);
		response.setResponseMessage("Departments fetched Successfully");
		response.setSuccess(true);

		// Convert the object to a JSON string
		String jsonString = null;
		try {
			jsonString = objectMapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(jsonString);

		return new ResponseEntity<DepartmentResponseDto>(response, HttpStatus.OK);

	}

	public ResponseEntity<CommonApiResponse> deleteDepartmentById(int departmentId) {

		CommonApiResponse response = new CommonApiResponse();

		if (departmentId == 0) {
			response.setResponseMessage("department id is null");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Department department = this.departmentService.getDepartmentById(departmentId);

		if (department == null) {
			response.setResponseMessage("Department not found");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		department.setStatus(DepartmentStatus.NOT_ACTIVE.value());

		Department updatedDepartment = this.departmentService.udpateDepartment(department);

		if (updatedDepartment == null) {
			response.setResponseMessage("Failed to delete the Department");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		response.setResponseMessage("Department Deleted Successfully");
		response.setSuccess(true);

		// Convert the object to a JSON string
		String jsonString = null;
		try {
			jsonString = objectMapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(jsonString);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);

	}

	public ResponseEntity<CommonApiResponse> updateDepartment(Department department) {

		CommonApiResponse response = new CommonApiResponse();

		if (department == null) {
			response.setResponseMessage("department is null");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		Department existingDepartment = this.departmentService.getDepartmentById(department.getId());
		
		if (existingDepartment == null) {
			response.setResponseMessage("department not found");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(department.getName() != null) {
			existingDepartment.setName(department.getName());
		}
		
		if(department.getDescription() != null) {
			existingDepartment.setDescription(department.getDescription());
		}
		
		Department updatedDepartment = this.departmentService.udpateDepartment(existingDepartment);

		if (updatedDepartment == null) {
			response.setResponseMessage("Failed to update Department");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		response.setResponseMessage("Department updated Successfully");
		response.setSuccess(true);

		// Convert the object to a JSON string
		String jsonString = null;
		try {
			jsonString = objectMapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(jsonString);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);

	}

}
