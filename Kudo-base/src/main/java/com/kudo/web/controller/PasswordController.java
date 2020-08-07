package com.kudo.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.servlet.ModelAndView;

import com.kud.web.exceptions.UserNotFoundException;
import com.kudo.web.model.Password;
import com.kudo.web.service.PasswordService;



@Controller
public class PasswordController {
	@Autowired
	private PasswordService passwordService;
	@Autowired
	private Environment environment;
	private String forgotPasswrd="forgotPassword";
    @GetMapping("/forgotPassword")
    public ModelAndView initializeForm()
    {
    	
    	
          return new ModelAndView(forgotPasswrd,"fp",new Password());
    }
    
    @PostMapping(value="/resetPassword")
    public ModelAndView updatePassword(@Valid @ModelAttribute("fp")Password fp,BindingResult bindingResult,ModelMap model)
    {
        
         
          if (bindingResult.hasErrors()) {
        	  model.addAttribute("check",false);
				ModelAndView modelAndView = new ModelAndView("forgotPassword");
				modelAndView.addObject("message", "Password must be between 8 and 15 characters");
                return new ModelAndView(forgotPasswrd, "fp", fp);

          } 
        
              
                String email=fp.getEmail();
                
                String password=fp.getNewPassword();
              
               
                int rowUpdate;
               
                try {
                	rowUpdate=passwordService.updatePassword(email,password);
                	if(rowUpdate==1)
                		model.addAttribute("check",true);
                		model.addAttribute("successMessage",environment.getProperty("PasswordController.SUCCESSFUL_RESET"));
		             return new ModelAndView(forgotPasswrd, "fp", fp);
				} catch (UserNotFoundException e) {
					model.addAttribute("check",false);
					ModelAndView modelAndView = new ModelAndView("forgotPassword");
					modelAndView.addObject("message", environment.getProperty(e.getMessage()));
					return modelAndView;				
					}
                            
        
       
                                        
   
    }



}
