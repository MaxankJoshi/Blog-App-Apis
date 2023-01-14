package com.blog.services;

import java.util.List;

import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

public interface PostService {
//	Create
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
//	Update
	public PostDto updatePost(PostDto postDto, Integer postId);
	
//	Delete
	public void deletePost(Integer postId);
	
//	Get
	public PostDto getPostById(Integer postId);
	
//	Get All
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
//	Get All Post By Category
	public PostResponse getAllPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize);
	
//	Get All Post By User
	public PostResponse getAllPostsByUser(Integer userId, Integer pageNumber, Integer pageSize);
	
//	Search
	public List<PostDto> searchPosts(String keyword);
	
}
