package com.blog.services;

import java.util.List;

import com.blog.payloads.UserDto;

public interface UserService {
//	Create
//	public UserDto createUser(UserDto userDto);
	
//	Update
	public UserDto updateUser(UserDto userDto,Integer userId);
	
//	Delete
	public void deleteUser(Integer userId);
	
//	Get
	public UserDto getUserById(Integer userId);
	
//	Get All
	public List<UserDto> getAllUsers();
	
//	Create Normal User
	public UserDto registerNewUser(UserDto userDto); 
}
