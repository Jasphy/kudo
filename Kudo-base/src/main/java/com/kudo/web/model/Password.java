package com.kudo.web.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;


public class Password {
	@NotNull(message = "Email must not be blank.")
	@Email
	private String email;
	

	@NotNull(message = "Please enter your password.")
	@Size(min=8,max=15 , message = "Password must be between 8 and 15 characters")
	private String newPassword;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	
	

}
