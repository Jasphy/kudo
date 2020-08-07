package com.kudo.web.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kud.web.exceptions.InvalidCredentialException;
import com.kudo.web.entity.UserEntity;
import com.kudo.web.model.Login;
import com.kudo.web.repository.UserRepository;

@Service
public class LoginService  {
	
	@Autowired
	UserRepository usr;

	public UserEntity authenticateLogin( Login userLogin) throws InvalidCredentialException {
		// TODO Auto-generated method stub
		
		
		UserEntity user =usr.findUserByName(userLogin.getUserName());

		if (user == null) {

			throw new InvalidCredentialException("LoginService.INVALID_CREDENTIALS");
		} else if (!(user.getPassword().equals(userLogin.getPassword()))) {
			throw new InvalidCredentialException("LoginService.INVALID_CREDENTIALS" + ".");
		}
		return user;

	}
	

}
