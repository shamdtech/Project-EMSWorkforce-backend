package com.employee.department.service.services;

import java.util.List;

import com.employee.department.service.entity.Department;

public interface DepartmentService {
	
	Department addDepartment(Department department);
	
	Department getDepartmentById(int department);
	
	List<Department> getAllActiveDepartment(int status);

	Department udpateDepartment(Department department);
	
}
