package com.cos.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security1.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	// findBy 까지는 규칙이고, Username 은 문법
	// select * from user where username = ? 가 호출되고, 물음표에 파라미터가 들어감.
	// jpa query method 검색!
	User findByUsername(String username);

}
