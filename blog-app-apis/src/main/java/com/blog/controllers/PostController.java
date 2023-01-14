package com.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.config.AppConstants;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.services.FileService;
import com.blog.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
//	Create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable("userId") Integer userId, @PathVariable("categoryId") Integer categoryId) {
		PostDto createdPostDto = this.postService.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<PostDto>(createdPostDto,HttpStatus.CREATED);
	}
	
	
//	Update
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable("postId") Integer postId) {
		PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
		
		return ResponseEntity.ok(updatedPostDto);
	}
	
	@DeleteMapping("/posts/{postId}") 
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId) {
		this.postService.deletePost(postId);
		ApiResponse apiResponse = new ApiResponse("Post has deleted successfully !!", false);
		
		return ResponseEntity.ok(apiResponse);
	}
	
	
//	Get
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer postId) {
		PostDto postDto = this.postService.getPostById(postId);
		
		return ResponseEntity.ok(postDto);
	}
	
	
//	Get All
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value="pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value="sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value="sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
		PostResponse postResponses = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
		
		return ResponseEntity.ok(postResponses);
	}
	
	
//	Get All Posts By User
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getAllPostsByUser(@PathVariable("userId") Integer userId, 
			@RequestParam(value="pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value="pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize) {
		PostResponse postResponse = this.postService.getAllPostsByUser(userId, pageNumber, pageSize);
		
		return ResponseEntity.ok(postResponse);
	}
	
	
//	Get All Posts By Categories
	@GetMapping("category/{categoryId}/posts")
	public ResponseEntity<PostResponse> getAllPostsByCategory(@PathVariable(value="categoryId") Integer categoryId,
			@RequestParam(value="pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value="pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize) {
		PostResponse postResponse = this.postService.getAllPostsByCategory(categoryId, pageNumber, pageSize);
		
		return ResponseEntity.ok(postResponse);
	}
	
	
//	Search
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords) {
		List<PostDto> searchPosts = this.postService.searchPosts(keywords);
		
		return ResponseEntity.ok(searchPosts);  
	}
	
	
//	Upload Image
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@PathVariable("postId") Integer postId,
			@RequestParam("imageName") MultipartFile image) throws IOException{
		PostDto postDto = this.postService.getPostById(postId);
		
		String fileName = this.fileService.uploadImage(path, image);
		
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		
		return ResponseEntity.ok(updatePost);
	}
	
	
//	Method to serve files
	@GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName,
			HttpServletResponse response) throws IOException {
		InputStream resource = this.fileService.getResource(path, imageName);
		
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
