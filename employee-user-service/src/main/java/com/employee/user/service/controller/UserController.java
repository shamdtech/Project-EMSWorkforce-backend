package com.employee.user.service.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.user.service.dto.CommonApiResponse;
import com.employee.user.service.dto.UserDetailResponseDto;
import com.employee.user.service.dto.UserListResponseDto;
import com.employee.user.service.dto.UserLoginRequest;
import com.employee.user.service.dto.UserLoginResponse;
import com.employee.user.service.dto.UserRegisterDto;
import com.employee.user.service.resource.UserResource;
import com.employee.user.service.utility.StorageService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("user/")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	@Autowired
	private UserResource userResource;
	
	@Autowired
	private StorageService storageService;

	@PostMapping("/register")
	public ResponseEntity<CommonApiResponse> registerUser(UserRegisterDto userRegisterDto) {
		return userResource.registerUser(userRegisterDto);
	}
	
	@PostMapping("login")	
	public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
		return userResource.login(userLoginRequest);
	}
	
	@GetMapping("/fetch")
	public ResponseEntity<UserDetailResponseDto> fetchUser(@RequestParam("userId") int userId) {
		return userResource.getUserDetailsById(userId);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<CommonApiResponse> deleteUser(@RequestParam("userId") int userId) {
		return userResource.deleteUser(userId);
	}
	
	// UserRegisterDto, we will set only email, password & role  from UI
	@PostMapping("/admin/register")
	public ResponseEntity<CommonApiResponse> registerAdmin(@RequestBody UserRegisterDto userRegisterDto) {
		return userResource.registerAdmin(userRegisterDto);
	}
	
	@PutMapping("/update")
	public ResponseEntity<CommonApiResponse> updateUser(UserRegisterDto userRegisterDto) {
		return userResource.updateUser(userRegisterDto);
	}
	
	@GetMapping("/fetch/all")
	public ResponseEntity<UserListResponseDto> fetchAllUsers(@RequestParam("role") String role) {
		return userResource.getAllUsers(role);
	}
	
	@GetMapping("/fetch/department/employee")
	public ResponseEntity<UserListResponseDto> fetchAllEmployeeDepartentWise(@RequestParam("role") String role, @RequestParam("departmentId") int departmentId) {
		return userResource.getAllUserUsingDepartmentAndRole(role, departmentId);
	}
	
	@GetMapping(value = "image/{image}", produces = "image/*")
	public void fetchProductImage(@PathVariable("image") String image, HttpServletResponse resp) {
		
		Resource resource = storageService.load(image);
		if (resource != null) {
			try (InputStream in = resource.getInputStream()) {
				ServletOutputStream out = resp.getOutputStream();
				FileCopyUtils.copy(in, out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
