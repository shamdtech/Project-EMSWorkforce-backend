package com.employee.department.service.dto;

import java.util.List;
import com.employee.department.service.entity.Department;
import lombok.Data;

@Data
public class DepartmentResponseDto extends CommonApiResponse {

	private List<Department> department;

}
