package com.blog.payloads;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CommentDto {
	private Integer commentId;
	
	@NotNull
	@Size(min = 10, message = "Min size of comment content is 10")
	private String commentContent;
}
