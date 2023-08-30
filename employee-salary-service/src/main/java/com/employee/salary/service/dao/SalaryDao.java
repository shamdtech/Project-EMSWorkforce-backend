package com.employee.salary.service.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.employee.salary.service.entity.Salary;

@Repository
public interface SalaryDao extends JpaRepository<Salary, Integer>{
	
	List<Salary> findByUserId(int userId);
	

}
