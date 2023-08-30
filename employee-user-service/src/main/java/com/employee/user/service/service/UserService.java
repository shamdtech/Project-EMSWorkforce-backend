package com.employee.user.service.service;

import java.util.List;
import com.employee.user.service.entity.User;

public interface UserService {
	
	User registerUser(User user);
	User updateUser(User user);
	User getUserById(int userId);
	User getUserByEmailAndPassword(String email, String password);
	User getUserByEmailAndPasswordAndRole(String email, String password, String role);
	User getUserByEmail(String email);
	User getUserByEmailAndRole(String email, String role);
	List<User> getUsersByRoleAndStatus(String role, int status);
	List<User> getUsersByRoleAndStatusAndDepartmentId(String role, int status, int departmentId);

}
