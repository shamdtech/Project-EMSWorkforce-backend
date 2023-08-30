package com.employee.salary.service.services;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.employee.salary.service.dao.SalaryDao;
import com.employee.salary.service.entity.Salary;

@Service
public class SalaryServiceImpl implements SalaryService {
	
	@Autowired
	private SalaryDao salaryDao;

	@Override
	public Salary addSalary(Salary salary) {
		return salaryDao.save(salary);
	}

	@Override
	public Salary getSalaryById(int salaryId) {
		return salaryDao.findById(salaryId).orElseThrow(() -> new EntityNotFoundException("Salry with ID " + salaryId + " not found"));
	}

	@Override
	public Salary updateSalary(Salary salary) {
		return salaryDao.save(salary);
	}

	@Override
	public List<Salary> getAllSalary() {
		return salaryDao.findAll();
	}

	@Override
	public List<Salary> getSalaryByUserId(int userId) {
		return salaryDao.findByUserId(userId);
	}

}
