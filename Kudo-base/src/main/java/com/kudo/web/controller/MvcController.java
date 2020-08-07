package com.kudo.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;


import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.multipart.MultipartFile;


import com.kudo.web.entity.CommentEntity;
import com.kudo.web.entity.UserEntity;
import com.kudo.web.entity.User_comment;
import com.kudo.web.model.Email;
import com.kudo.web.service.UserKudoService;


@Controller
public class MvcController {
	
	@Autowired
	private UserKudoService uks;
	
	@Autowired
	private Environment environment;
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@GetMapping("/") // login page
	public String login() {

		return "login";
	}
	

	@GetMapping("/register") // Registration Form
	public String gotoregisterpage() {

		return "register";
	}
	
	
	@PostMapping("/adduser") // After submitting Registration Form
	public String addnewuser(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("email") String email, @RequestParam("gender") String gender,
			@RequestParam("country") String country, @RequestParam("image") MultipartFile filename) {

		InputStream ins = null;
		Blob blob = null;
		byte[] fileContent = new byte[(int) filename.getSize()];
		try {
			ins = filename.getInputStream();
			ins.read(fileContent);
			try {
				blob = new SerialBlob(fileContent);
			} catch (SerialException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UserEntity us = new UserEntity(username, password, email, gender, country, blob);
		try {
		uks.save(us);
		Email email1 = new Email();
		email1.setEmailMessage(environment.getProperty("RegistrationController.SUCCESSFUL_REGISTRATION"));
		email1.setSubject("Registration confirmation");
		email1.setToEmail(us.getEmail());
		jmsTemplate.convertAndSend("mailbox", email1);
		//modelAndView= new ModelAndView(register, command, user);
		//modelAndView.addObject("successMessage",environment.getProperty("RegistrationController.SUCCESSFUL_REGISTRATION"));
		}
		catch(Exception e) {
			
			return "error";
			
		}

		return "redirect:/login";

	}
	
	@Bean // Serialize message content to json using TextMessage
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
	
	@GetMapping("/modifyimage")
	public String modifyimage(HttpSession session ,Model model ) {
		
		UserEntity ue=(UserEntity)session.getAttribute("name");
		model.addAttribute("user",ue);
		return "image";
	}
	@PostMapping("/editimage")
	public String updateimage(@RequestParam("image") MultipartFile filename,Model model,HttpSession session ) {
		
		
		
	//	UserEntity ue = uks.findUserById(id);
		
		
		UserEntity ue=(UserEntity)session.getAttribute("name");
		
		InputStream ins = null;
		Blob blob = null;
		byte[] fileContent = new byte[(int) filename.getSize()];
		try {
			ins = filename.getInputStream();
			ins.read(fileContent);
			try {
				blob = new SerialBlob(fileContent);
			} catch (SerialException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		uks.updatephoto(ue.getId(),blob);
		List<CommentEntity> list=uks.findallcomments();
		
		uks.sort(list);
		
		
		model.addAttribute("user", ue);
		model.addAttribute("list",list);
		
		return "commentpage";
	}
	
	@RequestMapping(value = "/image/{name}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<?> getImage(@PathVariable("name") String name) throws IOException {

		UserEntity ue = uks.findUserEntityByName(name);

		InputStreamResource isr;

		HttpHeaders headers = new HttpHeaders();

		try {

			if (ue != null) {
				isr = new InputStreamResource(ue.getPhoto().getBinaryStream());
				return new ResponseEntity<>(isr, headers, HttpStatus.OK);
			}
			System.err.println("displayAttachment() :: Error displaying attachment for name: " + name);
			return new ResponseEntity<>(new com.kud.web.exceptions.ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
					"No Attachment found for " + name, "/download"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(headers, HttpStatus.OK);
		}

	}

	@PostMapping("/postcomment") // postkudo
	public String postcomment(@RequestParam("postkudo") String text,
			@RequestParam("username") String name,
			Model model) {
		
		UserEntity ue;
		ue = uks.findUserEntityByName(name);
	 Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		//Date date =new Date(System.currentTimeMillis());
		 
	
		
		CommentEntity ce=new CommentEntity(text,timestamp,ue,0);
		
		uks.savecomment(ce);
		
		
		List<CommentEntity> list=uks.findallcomments();
		
		uks.sort(list);
		
		
		model.addAttribute("user", ue);
		model.addAttribute("list",list);

		return "commentpage";
	}
	
	@GetMapping("/like/{id}") // likekudo
	public String likecomment(@PathVariable("id") Integer id,HttpSession session,
			
			Model model) {
		CommentEntity ce=uks.findcommentbyid(id);
		int count=0;
		
		if(session.getAttribute("name")==null) {
			model.addAttribute("sessioninactive", "Session expired !! Please login again..");
			return "error";
		}
	User_comment uc=uks.findusercommentmap((UserEntity)session.getAttribute("name"),ce);
	System.out.println(session.getMaxInactiveInterval());
	
	if(uc==null) {
		uks.usercommentmap((UserEntity)session.getAttribute("name"),ce);
		if(ce.getLike_count()==null) {
			count=1;
			}
			else {
			
			 count=ce.getLike_count().intValue()+1;
			}
		uks.updatelikecount(count,id);
	}
	else {
		int like_yes=uc.getLike().intValue();
		
		if(like_yes==1) {
			List<CommentEntity> list=uks.findallcomments();
			uks.sort(list);
			model.addAttribute("user", (UserEntity)session.getAttribute("name"));
			model.addAttribute("list",list);

				return "commentpage";
		}
		else {
			if(ce.getLike_count()==null) {
				count=1;
				}
				else {
				
				 count=ce.getLike_count().intValue()+1;
				}
			uks.updatelikecount(count,id);
		}
		
	}
	
List<CommentEntity> list=uks.findallcomments();
	uks.sort(list);
	model.addAttribute("user", (UserEntity)session.getAttribute("name"));
	model.addAttribute("list",list);

		return "commentpage";
	}
	
	@PostMapping("/delete") // delete comment
	public String delete(@RequestParam("id") String value,HttpSession session,
			
			Model model) {
	
		 int val=Integer.parseInt(value);
		
		 Integer id=new Integer(val);
		

		CommentEntity ce=uks.findcommentbyid(id);
		
		if(((UserEntity)session.getAttribute("name")).getId()==ce.getUser().getId()) {
		
		uks.deletecomment(id);
		}
		
		List<CommentEntity> list=uks.findallcomments();
		uks.sort(list);
		model.addAttribute("user", (UserEntity)session.getAttribute("name"));
		model.addAttribute("list",list);
		return "commentpage";

	}
	
	@GetMapping("/logout") // logout
	public String logout(HttpSession session) {

	session.invalidate();
		return "login";

	}
	
	

}
