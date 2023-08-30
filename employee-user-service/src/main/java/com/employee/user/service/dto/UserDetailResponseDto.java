package com.employee.user.service.dto;

import java.util.List;

import com.employee.user.service.entity.User;

import lombok.Data;

@Data
public class UserDetailResponseDto extends CommonApiResponse {

	private User user;

	private List<Salary> salaryList;

	private List<Department> department;

}
