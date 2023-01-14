package com.blog.services;

import com.blog.payloads.CommentDto;

public interface CommentService {
	public CommentDto createComment(CommentDto commentDto, Integer userId, Integer postId);
	public void deleteComment(Integer commentId);
}
