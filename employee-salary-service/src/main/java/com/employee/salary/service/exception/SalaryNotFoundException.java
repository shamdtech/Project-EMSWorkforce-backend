package com.employee.salary.service.exception;

public class SalaryNotFoundException extends RuntimeException {
	
	public SalaryNotFoundException() {
		super("Salary not found in database");
	}
	
	public SalaryNotFoundException(String message) {
		super(message);
	}

}
