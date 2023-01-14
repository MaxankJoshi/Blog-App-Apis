package com.blog.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.config.AppConstants;
import com.blog.entities.Role;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.UserDto;
import com.blog.repositories.RoleRepository;
import com.blog.repositories.UserRepository;
import com.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	private User userDtoToUser(UserDto userDto) {
//		User user = new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		
//		You can use this in the place of the above code
		User user = this.modelMapper.map(userDto,User.class);
		
		return user;
	}
	
	private UserDto userToUserDto(User user) {
//		UserDto userDto = new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		
//		You can use this in the place of the above code
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		
		return userDto;
	}
	
//	@Override
//	public UserDto createUser(UserDto userDto) {
//		User user = userDtoToUser(userDto);
//		user.setUserPassword(passwordEncoder.encode(user.getPassword()));
//		
//		User savedUser = this.userRepository.save(user);
//		
//		return userToUserDto(savedUser);
//	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));

		user.setUserNaame(userDto.getUserNaame());
		user.setUserEmail(userDto.getUserEmail());
		user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
		user.setUserAbout(userDto.getUserAbout());
		
		User savedUser = this.userRepository.save(user);
		
		return userToUserDto(savedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
		
		return userToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepository.findAll();
		 
		List<UserDto> usersDto = new ArrayList<>();
		
		for(User user:users) {
			usersDto.add(userToUserDto(user));
		}
		
		return usersDto;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));

		this.userRepository.delete(user);
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		
//		Encode the password
		user.setUserPassword(passwordEncoder.encode(user.getPassword()));
		
//		roles
		Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(role);
		
		User registerUser = this.userRepository.save(user);
		
		return this.modelMapper.map(registerUser, UserDto.class);
	}
}
