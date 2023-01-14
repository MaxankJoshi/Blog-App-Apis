package com.blog.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.repositories.CategoryRepository;
import com.blog.repositories.PostRepository;
import com.blog.repositories.UserRepository;
import com.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository; 
		
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		
		post.setImageName("default.png");
		post.setAddedDate(new Date().toLocaleString());
		post.setCategory(category);
		post.setUser(user);
		
		Post savedPost = this.postRepository.save(post);
		
		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		
		post.setPostTitle(postDto.getPostTitle());
		post.setPostContent(postDto.getPostContent());
		post.setImageName("default.png");
		post.setAddedDate(new Date().toLocaleString());
				
		Post updatedPost = this.postRepository.save(post);
		
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		
		this.postRepository.delete(post);
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		Sort sort = null;
		
		if(sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}
		
		else {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> pagePosts = this.postRepository.findAll(p);
		List<Post> posts = pagePosts.getContent();
		
		List<PostDto> postDto = new ArrayList<>();
		
		for(Post post:posts) {
			postDto.add(this.modelMapper.map(post, PostDto.class));
		}
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDto);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastPage(pagePosts.isLast());
		
		return postResponse;
	}

	@Override
	public PostResponse getAllPostsByCategory(Integer categoryId,Integer pageNumber, Integer pageSize) {
		
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		Sort sort = Sort.by("category");
		
		Pageable p = PageRequest.of(pageNumber,pageSize,sort);

		Page<Post> pagePosts = this.postRepository.findAll(p);
		
		List<Post> posts = pagePosts.getContent();
		
		List<PostDto> postDto = new ArrayList<>();
		
		for(Post post:posts) {
			if(post.getCategory().getCategoryId() == categoryId) {
				postDto.add(this.modelMapper.map(post, PostDto.class));
			}
			
			else {
				continue;
			}
		}
		
		if(postDto.isEmpty()) {
			throw new ResourceNotFoundException("Post", "Category Id", categoryId);
		}
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDto);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastPage(pagePosts.isLast());
		
		return postResponse;
	}

	@Override
	public PostResponse getAllPostsByUser(Integer userId, Integer pageNumber, Integer pageSize) {
		User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
		Sort sort = Sort.by("user");
		
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> pagePosts = this.postRepository.findAll(p);
		
		List<Post> posts = pagePosts.getContent();
		
		List<PostDto> postDto = new ArrayList<>();
		
		for(Post post:posts) {
			if(post.getUser().getUserId() == userId) {
				postDto.add(this.modelMapper.map(post, PostDto.class));
			}
			
			else {
				continue;
			}
		}
		
		if(postDto.isEmpty()) {
			throw new ResourceNotFoundException("Post", "User Id", userId);
		}
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDto);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastPage(pagePosts.isLast());
		
		return postResponse;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepository.findByPostTitleContaining(keyword);
		
		List<PostDto> postDto = new ArrayList<>();
		
		for(Post post:posts) {
			postDto.add(this.modelMapper.map(post, PostDto.class));
		}
		
		return postDto;
	}
}
