package com.mytest.springboot.infrastructure.repository.jpa;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mytest.springboot.domain.ports.secondary.UserRepository;

@Transactional
public class JpaUserRepository implements UserRepository{

	@Autowired
	SpringCrudUserRepository crudRepository;
	
	@Override
	public Optional<com.mytest.springboot.domain.model.User> findByEmail(String email) {
		
		User repoUser = crudRepository.findByEmail(email);
		if(repoUser == null)
		{
			return Optional.empty();
		}
		
		return Optional.of(new com.mytest.springboot.domain.model.User(repoUser.getEmail(), repoUser.getName()));
	}

	@Override
	public void save(com.mytest.springboot.domain.model.User user) {
		
		User repoUser = crudRepository.findByEmail(user.getEmail());
		
		if(repoUser == null)
		{
			crudRepository.save(new User(user.getEmail(), user.getName()));
		}
		else
		{
			repoUser.setName(user.getName());
		}
	}

	@Override
	public void delete(long id) {
		crudRepository.delete(id);
	}

	@Override
	public boolean exist(long id) {
		User repoUser = crudRepository.findOne(id);
		if(repoUser == null)
		{
			return false;
		}
		return true;
	}
}
