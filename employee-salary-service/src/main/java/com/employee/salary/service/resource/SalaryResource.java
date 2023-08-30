package com.employee.salary.service.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.employee.salary.service.dto.CommonApiResponse;
import com.employee.salary.service.dto.SalaryResponseDto;
import com.employee.salary.service.entity.Salary;
import com.employee.salary.service.services.SalaryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SalaryResource {

	@Autowired
	private SalaryService salaryService;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	public ResponseEntity<CommonApiResponse> addSalaryForUser(@RequestBody Salary salary) {

		CommonApiResponse response = new CommonApiResponse();

		if (salary == null) {
			response.setResponseMessage("Salary is null");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Salary addedSalary = this.salaryService.addSalary(salary);
		
		if(addedSalary == null) {
			response.setResponseMessage("Salary is null");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		response.setResponseMessage("Salary added Successfully");
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
	
	public ResponseEntity<CommonApiResponse> updateSalaryForUser(@RequestBody Salary salary) {

		CommonApiResponse response = new CommonApiResponse();

		if (salary == null) {
			response.setResponseMessage("Salary is null");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
	    Salary existingSalary = this.salaryService.getSalaryById(salary.getId());
	    
	    if (existingSalary == null) {
			response.setResponseMessage("Salary entry not found");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
	    if(salary.getBank() != null) {
	    	existingSalary.setBank(salary.getBank());
	    }
	    
	    if(salary.getBankAccount() != null) {
	    	existingSalary.setBankAccount(salary.getBankAccount());
	    }
	    
	    if(salary.getBankIfsc() != null) {
	    	existingSalary.setBankIfsc(salary.getBankIfsc());
	    }
	    
	    if(salary.getSalary() != null) {
	    	existingSalary.setSalary(salary.getSalary());
	    }
	    
	    if(salary.getFromDate() != null) {
	    	existingSalary.setFromDate(salary.getFromDate());
	    }
		
	    if(salary.getToDate() != null) {
	    	existingSalary.setToDate(salary.getToDate());
	    }
	    
	    if(salary.getPayCycle() != null) {
	    	existingSalary.setPayCycle(salary.getPayCycle());
	    }
	    
	    if(salary.getPaymentMode() != null) {
	    	existingSalary.setPaymentMode(salary.getPaymentMode());
	    }

		Salary addedSalary = this.salaryService.addSalary(existingSalary);
		
		if(addedSalary == null) {
			response.setResponseMessage("Failed to update the salary");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		response.setResponseMessage("Salary updated Successfully");
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

	public ResponseEntity<SalaryResponseDto> fetchSalaryDetailsUsingUserId(int userId) {

		SalaryResponseDto response = new SalaryResponseDto();

		if (userId == 0) {
			response.setResponseMessage("User Id can not be 0");
			response.setSuccess(true);

			return new ResponseEntity<SalaryResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		List<Salary> salary = new ArrayList<>();
		salary = salaryService.getSalaryByUserId(userId);

		response.setSalary(salary);
		response.setResponseMessage("Salary Fetched Successfully");
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

		return new ResponseEntity<SalaryResponseDto>(response, HttpStatus.OK);
	}
	
	public ResponseEntity<SalaryResponseDto> fetchSalaryDetailsUsingSalaryId(int salaryId) {

		SalaryResponseDto response = new SalaryResponseDto();

		if (salaryId == 0) {
			response.setResponseMessage("Salary Id can not be 0");
			response.setSuccess(true);

			return new ResponseEntity<SalaryResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		List<Salary> listOfSalary = new ArrayList<>();
		Salary salary = salaryService.getSalaryById(salaryId);
        
		if(salary != null) {
			listOfSalary.add(salary);
		}
		
		response.setSalary(listOfSalary);
		response.setResponseMessage("Salary Fetched Successfully");
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

		return new ResponseEntity<SalaryResponseDto>(response, HttpStatus.OK);
	}

}
