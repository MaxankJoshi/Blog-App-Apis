package com.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDto;
import com.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;
	
//	//POST = Create User
//	@PostMapping()
//	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
//		UserDto createdUserDto = this.userService.createUser(userDto);
//		
//		return new ResponseEntity<UserDto>(createdUserDto,HttpStatus.CREATED);
//	}
	
	
	//PUT = Update User
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer userId) {
		UserDto updatedUserDto = this.userService.updateUser(userDto, userId);
		
		return ResponseEntity.ok(updatedUserDto);
	}
	
	
	//DELETE = Delete User
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId) {
		this.userService.deleteUser(userId);
		ApiResponse apiResponse = new ApiResponse("User has deleted successfully !!",true);
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
	}
	
	
	//GET = Get All Users
	@GetMapping()
	public ResponseEntity<List<UserDto>> getAllUsers() {
		List<UserDto> allUsersDto = this.userService.getAllUsers();
		
		return ResponseEntity.ok(allUsersDto);
	}
	
	
	//GET = Get user
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getuser(@PathVariable("userId") Integer userId) {
		UserDto userDto = this.userService.getUserById(userId);
		
		return ResponseEntity.ok(userDto);
	}
}
