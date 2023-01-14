package com.blog.payloads;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private Integer userId;
	
	@NotEmpty
	@Size(min = 4, message = "Username must be minimum of 4 characters !!")
	private String userNaame;
	
	@Email(message = "Email address is not valid !!")
	private String userEmail;
	
	@NotEmpty
	@Size(min = 3, max = 10, message = "Password must be minimum of 3 characters and maximum of 10 characters")
	private String userPassword;
	
	@NotEmpty
	private String userAbout;
	
	private List<CommentDto> comments = new ArrayList<>();
	
	private Set<RoleDto> roles = new HashSet<>();
}
