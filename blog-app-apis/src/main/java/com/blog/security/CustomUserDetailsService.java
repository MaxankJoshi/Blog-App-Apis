package com.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.entities.User;
import com.blog.exceptions.UserNotFoundExceptionByEmail;
import com.blog.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		// Loading user from database by username
		
		User user = this.userRepository.findByUserEmail(username).orElseThrow(()-> new UserNotFoundExceptionByEmail("User", "User Email", username));
		
		return user;
	}
	
}
