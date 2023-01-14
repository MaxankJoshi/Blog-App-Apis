package com.blog.services;

import java.util.List;

import com.blog.payloads.CategoryDto;

public interface CategoryService {
//	Create
	public CategoryDto createCategory(CategoryDto categoryDto);
	
//	Update
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
//	Delete
	public void deleteCategory(Integer categoryId);
	
//	Get
	public CategoryDto getCategoryById(Integer categoryId);
	
//	Get All
	public List<CategoryDto> getAllCategories();
}
