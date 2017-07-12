package com.mytest.springboot.domain.ports.primary;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.mytest.springboot.domain.model.User;
import com.mytest.springboot.domain.ports.secondary.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class CreateUserUseCaseTest {

	@Mock
	private UserRepository userRepository;
	@InjectMocks
	private CreateUserUseCase useCase;

	@Test
	public void createUserSuccess()
	{
		useCase.execute("anyEmail", "anyName");
		
		verify(userRepository).save(any(User.class));
	}
}
