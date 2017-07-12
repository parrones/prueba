package com.mytest.springboot.infrastructure.repository.jpa;

import org.springframework.data.repository.CrudRepository;

public interface SpringCrudUserRepository extends CrudRepository<User, Long> {
	
	User findByEmail(String email);
}
