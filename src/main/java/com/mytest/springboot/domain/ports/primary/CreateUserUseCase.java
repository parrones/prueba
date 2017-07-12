package com.mytest.springboot.domain.ports.primary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mytest.springboot.domain.model.User;
import com.mytest.springboot.domain.ports.secondary.UserRepository;

@Component
public class CreateUserUseCase {

	@Autowired
	private UserRepository userRepository;
	
	public void execute(String email, String name)
	{
		userRepository.save(new User(email, name));
	}
}
