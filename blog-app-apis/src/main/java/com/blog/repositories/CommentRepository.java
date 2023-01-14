package com.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.entities.User;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
	public List<Comment> findByCommentUser(User commentUser);
	public List<Comment> findByPost(Post post);
}
