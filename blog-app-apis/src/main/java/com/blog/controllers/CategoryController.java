package com.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategoryDto;
import com.blog.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
//	Create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto createdCategoryDto = this.categoryService.createCategory(categoryDto);
		
		return new ResponseEntity<CategoryDto>(createdCategoryDto,HttpStatus.CREATED);
	}
	
	
//	Update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("categoryId") Integer categoryId) {
		CategoryDto updatedCategoryDto = this.categoryService.updateCategory(categoryDto,categoryId);
		
		return new ResponseEntity<CategoryDto>(updatedCategoryDto,HttpStatus.OK);
	}
	
	
//	Delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
		this.categoryService.deleteCategory(categoryId);
		ApiResponse apiResponse = new ApiResponse("Category has deleted successfully !!", false);
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
	}

	
//	Get 
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryId") Integer categoryId) {
		CategoryDto categoryDto = this.categoryService.getCategoryById(categoryId);
		
		return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
	}
	
//	Get All
	@GetMapping()
	public ResponseEntity<List<CategoryDto>> getAllCategories() {
		List<CategoryDto> categoriesDto = this.categoryService.getAllCategories();
		
		return new ResponseEntity<List<CategoryDto>>(categoriesDto,HttpStatus.OK);
	}
	
}
