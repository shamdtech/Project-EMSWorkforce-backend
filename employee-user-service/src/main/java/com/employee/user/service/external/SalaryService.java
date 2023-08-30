package com.employee.user.service.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.employee.user.service.dto.SalaryDto;

@Component
@FeignClient(name = "employee-salary-service")
public interface SalaryService {
	
	@GetMapping("/salary/user/fetch")
	SalaryDto getSalaryByUsedId(@RequestParam("userId") int  userId);
	
}
