package com.blog.payloads;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
	private Integer postId;
	
	@NotNull
	@Size(min = 4, message = "Min size of post title is 4")
	private String postTitle;
	
	@NotNull
	@Size(min = 10, message = "Min size of post content is 10")
	private String postContent;
	
	private String imageName;
	private String addedDate;
	private UserDto user;
	private CategoryDto category;
//	private Set<CommentDto> comments = new HashSet<>();
}
