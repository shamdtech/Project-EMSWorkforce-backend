package com.employee.department.service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.department.service.dao.DepartmentDao;
import com.employee.department.service.entity.Department;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	
	@Autowired
	private DepartmentDao departmentDao;

	@Override
	public Department addDepartment(Department department) {
		return departmentDao.save(department);
	}

	@Override
	public Department getDepartmentById(int departmentId) {
		return departmentDao.findById(departmentId).get();
	}

	@Override
	public List<Department> getAllActiveDepartment(int status) {
		return departmentDao.findByStatus(status);
	}

	@Override
	public Department udpateDepartment(Department department) {
		return departmentDao.save(department);
	}

}
