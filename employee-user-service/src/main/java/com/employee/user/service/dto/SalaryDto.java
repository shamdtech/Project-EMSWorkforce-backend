package com.employee.user.service.dto;

import java.util.List;

import lombok.Data;

@Data
public class SalaryDto extends CommonApiResponse {
	
	List<Salary> salary;

}
