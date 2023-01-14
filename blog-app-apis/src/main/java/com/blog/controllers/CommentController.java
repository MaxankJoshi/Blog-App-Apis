package com.blog.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CommentDto;
import com.blog.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("user/{userId}/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto,
			@PathVariable("userId") Integer userId,
			@PathVariable("postId") Integer postId) {
		CommentDto createCommentDto = this.commentService.createComment(commentDto, userId, postId);
		
		return new ResponseEntity<CommentDto>(createCommentDto,HttpStatus.CREATED);
	}
	
	@DeleteMapping("comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment (@PathVariable("commentId") Integer commentId) {
		this.commentService.deleteComment(commentId);
		
		ApiResponse apiResponse = new ApiResponse("Comment has deleted successfully !!", false);
		
		return ResponseEntity.ok(apiResponse);
	}
}
