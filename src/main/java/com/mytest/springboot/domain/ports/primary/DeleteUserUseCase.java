package com.mytest.springboot.domain.ports.primary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mytest.springboot.domain.ports.secondary.UserRepository;

@Component
public class DeleteUserUseCase {

	@Autowired
	private UserRepository userRepository;
	
	public boolean execute(long id)
	{
		boolean existUser = userRepository.exist(id);
		if(existUser)
		{
			userRepository.delete(id);
			return true;
		}
		return false;
	}
}
