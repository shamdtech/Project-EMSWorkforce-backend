package com.employee.salary.service.services;

import java.util.List;

import com.employee.salary.service.entity.Salary;

public interface SalaryService {
	
	Salary addSalary(Salary salary);
	Salary getSalaryById(int salaryId);
	Salary updateSalary(Salary salary);
	List<Salary> getAllSalary();
	List<Salary> getSalaryByUserId(int userId);

}
