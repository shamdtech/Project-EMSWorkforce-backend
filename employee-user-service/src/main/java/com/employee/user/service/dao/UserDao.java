package com.employee.user.service.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.user.service.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
	
	User findByEmailIdAndPassword(String emailId, String password); 
	User findByEmailIdAndPasswordAndRole(String emailId, String password, String role); 
	User findByEmailIdAndRole(String emailId, String role);
	User findByEmailId(String emailId);
	List<User> findByRoleAndStatus(String role, int status);
	List<User> findByRoleAndStatusAndDepartmentId(String role, int status, int departmentId);

}
