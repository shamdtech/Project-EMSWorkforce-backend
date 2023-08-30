package com.employee.user.service.dto;

import com.employee.user.service.entity.User;
import lombok.Data;

@Data
public class UserLoginResponse extends CommonApiResponse {

	private User user;
	
	private String jwtToken;

}
