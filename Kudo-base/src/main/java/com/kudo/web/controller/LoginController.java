package com.kudo.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import com.kud.web.exceptions.InvalidCredentialException;
import com.kudo.web.entity.CommentEntity;
import com.kudo.web.entity.UserEntity;
import com.kudo.web.service.LoginService;
import com.kudo.web.service.UserKudoService;

import com.kudo.web.model.Login;

@Controller
public class LoginController {
	
	@Autowired
	private UserKudoService uks;
	
	@Autowired
	private LoginService logser;
	@Autowired
	private Environment environment;
	
	/*
	 * @PostMapping("/commentpage") // HomePage
	 * 
	 * @SessionScope
	 * 
	 * @Pointcut public String getcommentpage(@RequestParam("username") String
	 * username, @RequestParam("password") String password, Model model, HttpSession
	 * session) {
	 * 
	 * String user = username.toString(); session.setAttribute("username", user);
	 * session.setMaxInactiveInterval(60);
	 * 
	 * UserEntity ue;
	 * 
	 * 
	 * if (uks.findUserEntityByName(user) == null) {
	 * 
	 * return "redirect:/login";
	 * 
	 * }
	 * 
	 * 
	 * ue = uks.findUserEntityByName(user);
	 * 
	 * if (username.equals(ue.getName())) {
	 * 
	 * if (password.equals(ue.getPassword())) {
	 * 
	 * session.setAttribute("name", ue); List<CommentEntity>
	 * list=uks.findallcomments();
	 * 
	 * uks.sort(list);
	 * 
	 * 
	 * model.addAttribute("user", ue); model.addAttribute("list",list);
	 * 
	 * return "commentpage";
	 * 
	 * } }
	 * 
	 * return "redirect:/login";
	 * 
	 * }
	 */	
	@PostMapping(value = "/authenticateLogin")
	public ModelAndView authenticateLogin(@Valid @ModelAttribute("command") Login userLogin, BindingResult result,
			ModelMap model, SessionStatus status,HttpSession session) {		

		ModelAndView modelAndView = new ModelAndView("error");
		status.setComplete();

		try {
			if (result.hasErrors()) {

				System.out.println(userLogin.getUserName());
				modelAndView= new ModelAndView("login", "command", userLogin);

			}
			else{

				UserEntity ue = logser.authenticateLogin(userLogin);

				
				session.setAttribute("name", ue); 
				List<CommentEntity>
				 list=uks.findallcomments();
				
				 uks.sort(list);
				 model.addAttribute("user", ue); 
					model.addAttribute("list",list);

			modelAndView = new ModelAndView("commentpage", "command", new CommentEntity());	}
				

			
		}
		catch (InvalidCredentialException e) {
			
			if (e.getMessage().contains("LoginService")) {
				
				modelAndView = new ModelAndView("login"); 
				modelAndView.addObject("check",true);
				modelAndView.addObject("loginName", userLogin.getUserName());
			}

			modelAndView.addObject("message", environment.getProperty(e.getMessage()));
		}
		return modelAndView;

	}


}
