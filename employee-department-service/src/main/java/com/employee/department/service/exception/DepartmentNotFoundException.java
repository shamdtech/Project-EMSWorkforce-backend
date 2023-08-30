package com.employee.department.service.exception;

public class DepartmentNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DepartmentNotFoundException() {
		super("Department not found in database");
	}
	
	public DepartmentNotFoundException(String message) {
		super(message);
	}

}
