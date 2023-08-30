package com.employee.department.service.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.employee.department.service.entity.Department;

@Repository
public interface DepartmentDao extends JpaRepository<Department, Integer> {
	
	List<Department> findByStatus(int status);

}
