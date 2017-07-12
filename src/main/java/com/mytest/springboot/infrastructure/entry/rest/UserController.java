package com.mytest.springboot.infrastructure.entry.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mytest.springboot.domain.ports.primary.CreateUserUseCase;
import com.mytest.springboot.domain.ports.primary.DeleteUserUseCase;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private CreateUserUseCase createUseCase;
	@Autowired
	private DeleteUserUseCase deleteUseCase;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "name", required = true) String name) {
		try {
			createUseCase.execute(email, name);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.ok().build();
	}
	 
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity deleteUser(@PathVariable(value = "id",required=true) long id)
	{
		boolean deleted = deleteUseCase.execute(id);
		
		if (deleted)
		{
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}
