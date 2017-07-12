package com.mytest.springboot.infrastructure.repository.jpa;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.mytest.springboot.domain.model.User;
import com.mytest.springboot.domain.ports.secondary.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class JpaUserRepositoryIT 
{
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void findUserSucessfully()
	{
		Optional<User> user = userRepository.findByEmail("prueba@domain.com");
		
		assertTrue(user.isPresent());
	}
	
	@Test
	public void findEmptyUserBecauseDoesntExist()
	{
		Optional<User> user = userRepository.findByEmail("invented@domain.com");
		
		assertFalse(user.isPresent());
	}
	
	@Test
	public void saveSuccessfully()
	{
		User user = new User("yo@domain.com","Yo");
		
		assertFalse(userRepository.findByEmail("yo@domain.com").isPresent());
		
		userRepository.save(user);
		
		assertTrue(userRepository.findByEmail("yo@domain.com").isPresent());
	}
}
