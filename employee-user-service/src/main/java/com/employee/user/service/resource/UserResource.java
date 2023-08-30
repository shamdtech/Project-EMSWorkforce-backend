package com.employee.user.service.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.employee.user.service.constants.DatabaseConstant.UserRole;
import com.employee.user.service.constants.DatabaseConstant.UserStatus;
import com.employee.user.service.dto.CommonApiResponse;
import com.employee.user.service.dto.Department;
import com.employee.user.service.dto.DepartmentDto;
import com.employee.user.service.dto.Salary;
import com.employee.user.service.dto.SalaryDto;
import com.employee.user.service.dto.UserDetailResponseDto;
import com.employee.user.service.dto.UserList;
import com.employee.user.service.dto.UserListResponseDto;
import com.employee.user.service.dto.UserLoginRequest;
import com.employee.user.service.dto.UserLoginResponse;
import com.employee.user.service.dto.UserRegisterDto;
import com.employee.user.service.entity.User;
import com.employee.user.service.external.DepartmentService;
import com.employee.user.service.external.SalaryService;
import com.employee.user.service.service.CustomUserDetailsService;
import com.employee.user.service.service.UserService;
import com.employee.user.service.utility.JwtUtil;
import com.employee.user.service.utility.StorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UserResource {

	@Autowired
	private UserService userService;

	@Autowired
	private StorageService storageService;

	@Autowired
	private SalaryService salaryService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	ObjectMapper objectMapper = new ObjectMapper();

	public ResponseEntity<CommonApiResponse> registerUser(UserRegisterDto registerRequest) {

		CommonApiResponse response = new CommonApiResponse();

		if (registerRequest == null) {
			response.setResponseMessage("user is null");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		User existingUser = this.userService.getUserByEmailAndRole(registerRequest.getEmailId(),
				registerRequest.getRole());

		if (existingUser != null) {
			response.setResponseMessage("User already register");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (registerRequest.getRole().equals(UserRole.MANAGER.value())
				|| registerRequest.getRole().equals(UserRole.EMPLOYEE.value())) {
			if (registerRequest.getDepartmentId() == 0) {
				response.setResponseMessage("department id is 0");
				response.setSuccess(true);

				return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
			}
		}

		User user = UserRegisterDto.toEntity(registerRequest);

		String image = storageService.store(registerRequest.getImage());

		user.setImage(image);
		user.setStatus(com.employee.user.service.constants.DatabaseConstant.UserStatus.ACTIVE.value());

		String encodedPassword = passwordEncoder.encode(user.getPassword());

		user.setPassword(encodedPassword);

		existingUser = this.userService.registerUser(user);

		if (existingUser == null) {
			response.setResponseMessage("failed to register user");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		response.setResponseMessage("User registered Successfully");
		response.setSuccess(true);

		// Convert the object to a JSON string
		String jsonString = null;
		try {
			jsonString = objectMapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(jsonString);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<UserLoginResponse> login(UserLoginRequest loginRequest) {

		UserLoginResponse response = new UserLoginResponse();

		if (loginRequest == null) {
			response.setResponseMessage("Missing Input");
			response.setSuccess(true);

			return new ResponseEntity<UserLoginResponse>(response, HttpStatus.BAD_REQUEST);
		}

		String jwtToken = null;
		User user = null;
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmailId(), loginRequest.getPassword()));
		} catch (Exception ex) {
			response.setResponseMessage("Invalid email or password.");
			response.setSuccess(true);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getEmailId());

		user = userService.getUserByEmail(loginRequest.getEmailId());

		if (user.getStatus() != UserStatus.ACTIVE.value()) {
			response.setResponseMessage("Failed to login");
			response.setSuccess(true);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		for (GrantedAuthority grantedAuthory : userDetails.getAuthorities()) {
			if (grantedAuthory.getAuthority().equals(loginRequest.getRole())) {
				jwtToken = jwtUtil.generateToken(userDetails.getUsername());
			}
		}

		// user is authenticated
		if (jwtToken != null) {
			response.setUser(user);

			response.setResponseMessage("Logged in sucessful");
			response.setSuccess(true);
			response.setJwtToken(jwtToken);
			return new ResponseEntity(response, HttpStatus.OK);

		}

		else {

			response.setResponseMessage("Failed to login");
			response.setSuccess(true);
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

	}

	public ResponseEntity<CommonApiResponse> registerAdmin(UserRegisterDto registerRequest) {

		CommonApiResponse response = new CommonApiResponse();

		if (registerRequest == null) {
			response.setResponseMessage("user is null");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (registerRequest.getEmailId() == null || registerRequest.getPassword() == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		User existingUser = this.userService.getUserByEmailAndRole(registerRequest.getEmailId(),
				registerRequest.getRole());

		if (existingUser != null) {
			response.setResponseMessage("User already register with this Email");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		User user = new User();
		user.setEmailId(registerRequest.getEmailId());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setRole(UserRole.ADMIN.value());
		user.setStatus(com.employee.user.service.constants.DatabaseConstant.UserStatus.ACTIVE.value());
		existingUser = this.userService.registerUser(user);

		if (existingUser == null) {
			response.setResponseMessage("failed to register admin");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		response.setResponseMessage("Admin registered Successfully");
		response.setSuccess(true);

		// Convert the object to a JSON string
		String jsonString = null;
		try {
			jsonString = objectMapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(jsonString);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<CommonApiResponse> deleteUser(int userId) {

		CommonApiResponse response = new CommonApiResponse();

		if (userId == 0) {
			response.setResponseMessage("User Id is 0");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		User user = this.userService.getUserById(userId);

		user.setStatus(com.employee.user.service.constants.DatabaseConstant.UserStatus.NOT_ACTIVE.value());

		User deletedUser = this.userService.updateUser(user);

		if (deletedUser == null) {
			response.setResponseMessage("Failed to delete the user");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		response.setResponseMessage("User deleted Successfully");
		response.setSuccess(true);

		// Convert the object to a JSON string
		String jsonString = null;
		try {
			jsonString = objectMapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(jsonString);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<CommonApiResponse> updateUser(UserRegisterDto registerRequest) {

		CommonApiResponse response = new CommonApiResponse();

		if (registerRequest == null) {
			response.setResponseMessage("Missing input");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		User userDetailToUpdate = new User();

		User existingUser = this.userService.getUserById(registerRequest.getId());

		if (existingUser == null) {
			response.setResponseMessage("User not found");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		userDetailToUpdate.setId(existingUser.getId());
		userDetailToUpdate.setDepartmentId(existingUser.getDepartmentId());
		userDetailToUpdate.setPassword(existingUser.getPassword());
		userDetailToUpdate.setEmailId(existingUser.getEmailId());
		userDetailToUpdate.setRole(existingUser.getRole());
		userDetailToUpdate.setStatus(existingUser.getStatus());

		if (registerRequest.getImage() != null) {
			String image = storageService.store(registerRequest.getImage());
			userDetailToUpdate.setImage(image);
		} else {
			userDetailToUpdate.setImage(existingUser.getImage());
		}

		userDetailToUpdate.setAge(registerRequest.getAge());
		userDetailToUpdate.setCity(registerRequest.getCity());
		userDetailToUpdate.setContact(registerRequest.getContact());
		userDetailToUpdate.setExperience(registerRequest.getExperience());
		userDetailToUpdate.setFirstName(registerRequest.getFirstName());
		userDetailToUpdate.setGender(registerRequest.getGender());
		userDetailToUpdate.setLastName(registerRequest.getLastName());
		userDetailToUpdate.setPincode(registerRequest.getPincode());
		userDetailToUpdate.setStreet(registerRequest.getStreet());

		User updatedUser = this.userService.updateUser(userDetailToUpdate);

		if (updatedUser == null) {
			response.setResponseMessage("Failed to update the user");
			response.setSuccess(true);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		response.setResponseMessage("User updated Successfully");
		response.setSuccess(true);

		// Convert the object to a JSON string
		String jsonString = null;
		try {
			jsonString = objectMapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(jsonString);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);
	}

	public ResponseEntity<UserDetailResponseDto> getUserDetailsById(int userId) {

		UserDetailResponseDto response = new UserDetailResponseDto();

		if (userId == 0) {
			response.setResponseMessage("User Id is 0");
			response.setSuccess(true);

			return new ResponseEntity<UserDetailResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		User user = this.userService.getUserById(userId);

		if (user == null) {
			response.setResponseMessage("User not found");
			response.setSuccess(true);

			return new ResponseEntity<UserDetailResponseDto>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.setUser(user);

		SalaryDto salaryResponse = this.salaryService.getSalaryByUsedId(userId);

		List<Salary> salaries = new ArrayList<>();

		if (salaryResponse != null) {
			salaries = salaryResponse.getSalary();
		}

		response.setSalaryList(salaries);

		DepartmentDto departmentResponse = this.departmentService.getDepartmentByUsedId(user.getDepartmentId());

		List<Department> departments = new ArrayList<>();

		if (departmentResponse != null) {
			departments = departmentResponse.getDepartment();
		}

		response.setDepartment(departments);

		response.setResponseMessage("User Fetched Successfully");
		response.setSuccess(true);

		// Convert the object to a JSON string
		String jsonString = null;
		try {
			jsonString = objectMapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(jsonString);

		return new ResponseEntity<UserDetailResponseDto>(response, HttpStatus.OK);
	}

	public ResponseEntity<UserListResponseDto> getAllUsers(String role) {

		UserListResponseDto response = new UserListResponseDto();

		List<UserList> users = new ArrayList<>();

		List<User> listOfUser = this.userService.getUsersByRoleAndStatus(role, UserStatus.ACTIVE.value());

		for (User user : listOfUser) {

			UserList userList = new UserList();
			userList.setUser(user);

			SalaryDto salaryResponse = this.salaryService.getSalaryByUsedId(user.getId());

			List<Salary> salaries = new ArrayList<>();

			if (salaryResponse != null) {
				salaries = salaryResponse.getSalary();
			}

			userList.setSalary(salaries);

			DepartmentDto departmentResponse = this.departmentService.getDepartmentByUsedId(user.getDepartmentId());

			List<Department> departments = new ArrayList<>();

			if (departmentResponse != null) {
				departments = departmentResponse.getDepartment();
			}

			userList.setDepartment(departments);

			users.add(userList);
		}

		response.setUsers(users);

		response.setResponseMessage("User Fetched Successfully");
		response.setSuccess(true);

		// Convert the object to a JSON string
		String jsonString = null;
		try {
			jsonString = objectMapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(jsonString);

		return new ResponseEntity<UserListResponseDto>(response, HttpStatus.OK);
	}
	
	public ResponseEntity<UserListResponseDto> getAllUserUsingDepartmentAndRole(String role, int departmentId) {

		UserListResponseDto response = new UserListResponseDto();

		List<UserList> users = new ArrayList<>();

		List<User> listOfUser = this.userService.getUsersByRoleAndStatusAndDepartmentId(role, UserStatus.ACTIVE.value(),departmentId);

		for (User user : listOfUser) {

			UserList userList = new UserList();
			userList.setUser(user);

			SalaryDto salaryResponse = this.salaryService.getSalaryByUsedId(user.getId());

			List<Salary> salaries = new ArrayList<>();

			if (salaryResponse != null) {
				salaries = salaryResponse.getSalary();
			}

			userList.setSalary(salaries);

			DepartmentDto departmentResponse = this.departmentService.getDepartmentByUsedId(user.getDepartmentId());

			List<Department> departments = new ArrayList<>();

			if (departmentResponse != null) {
				departments = departmentResponse.getDepartment();
			}

			userList.setDepartment(departments);

			users.add(userList);
		}

		response.setUsers(users);

		response.setResponseMessage("User Fetched Successfully");
		response.setSuccess(true);

		// Convert the object to a JSON string
		String jsonString = null;
		try {
			jsonString = objectMapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(jsonString);

		return new ResponseEntity<UserListResponseDto>(response, HttpStatus.OK);
	}

}
