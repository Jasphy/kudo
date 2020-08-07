package com.kudo.web.util;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.kud.web.exceptions.LoginFailedException;
import com.kud.web.exceptions.UserNotFoundException;
import com.kudo.web.entity.UserEntity;
import com.kudo.web.service.UserKudoService;

@Aspect
@Component  
public class LoginAspect {
	@Autowired
	private UserKudoService uks;
	
	//@Before("execution(* com.kudo.web.controller.MvcController.*(..)) and args(username,password,model,session)")  
	public void beforeAdvice(JoinPoint joinPoint, String username, String password,Model model,HttpSession session) {  
	

		String user = username.toString();
		UserEntity ue;

		if (uks.findUserEntityByName(user) == null) {

			try {
				throw new UserNotFoundException(user);
			} catch (UserNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		ue = uks.findUserEntityByName(user);

		if (username.equals(ue.getName())) {

			if (!password.equals(ue.getPassword())) {

				/*
				 * try { throw new LoginFailedException(); } catch (LoginFailedException e) { //
				 * TODO Auto-generated catch block e.printStackTrace(); }
				 */
				
				
			
				
			}
		}
		
		else
		{
			
			/*
			 * try { throw new UserNotFoundException(); } catch (UserNotFoundException e) {
			 * // TODO Auto-generated catch block e.printStackTrace(); }
			 */
			
		}
		
	
	}  
	
	
	

}
