package com.blog.payloads;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	private Integer categoryId;
	
	@NotNull
	@Size(min = 4, message = "Min size of category title is 4")
	private String categoryTitle;
	
	@NotNull
	@Size(min = 10, message = "Min size of category description is 10")
	private String categoryDescription;
}
