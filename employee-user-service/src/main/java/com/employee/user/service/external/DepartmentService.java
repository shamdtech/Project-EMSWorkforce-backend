package com.employee.user.service.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.employee.user.service.dto.DepartmentDto;

@Component
@FeignClient(name = "employee-department-service")
public interface DepartmentService {
	
	@GetMapping("/department/fetch")
	DepartmentDto getDepartmentByUsedId(@RequestParam("departmentId") int  departmentId);

}
