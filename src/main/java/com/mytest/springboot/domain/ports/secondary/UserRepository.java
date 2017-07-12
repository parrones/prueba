package com.mytest.springboot.domain.ports.secondary;

import java.util.Optional;

import com.mytest.springboot.domain.model.User;


public interface UserRepository {

	Optional<User> findByEmail(String email);

	void save(User user);
	
	void delete(long id);
	
	boolean exist(long id);
}
