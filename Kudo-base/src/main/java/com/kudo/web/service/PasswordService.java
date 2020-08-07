package com.kudo.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kud.web.exceptions.UserNotFoundException;
import com.kudo.web.repository.UserRepository;



@Service
public class PasswordService {

	@Autowired
	private UserRepository userRepository;
	public int updatePassword(String email, String password) throws UserNotFoundException{
		int rowCount=0;
        

		
		
         rowCount= userRepository.updatePassword(email,password);
		if(rowCount!=1)
			throw new UserNotFoundException("PasswordService.USER_NOT_FOUND");
		return rowCount;
        
  }
}
