package com.employee.user.service.dto;

import java.util.List;
import com.employee.user.service.entity.User;

import lombok.Data;

@Data
public class UserList {
	
	private User user;

	private List<Salary> salary;

	private List<Department> department;

}
