package com.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	public Optional<User> findByUserEmail(String userEmail);
}
