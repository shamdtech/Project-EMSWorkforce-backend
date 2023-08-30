package com.employee.salary.service.dto;

import java.util.List;
import org.springframework.beans.BeanUtils;
import com.employee.salary.service.entity.Salary;
import lombok.Data;

@Data
public class SalaryResponseDto extends CommonApiResponse {

	private List<Salary> salary;

}
