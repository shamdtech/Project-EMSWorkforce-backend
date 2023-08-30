package com.employee.user.service.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserListResponseDto extends CommonApiResponse {
	
	private List<UserList> users;

}
