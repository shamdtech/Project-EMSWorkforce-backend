package com.employee.user.service.dto;

import java.util.List;
import lombok.Data;

@Data
public class DepartmentDto extends CommonApiResponse {
	
	private List<Department> department;

}
