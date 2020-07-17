package com.kudo.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.kudo.web.service.UserKudoService;




@Controller
public class MvcController {
	
	@Autowired
	private UserKudoService uks;
	
	
	@GetMapping("/login") // login page
	public String login() {

		return "login";
	}
	
	
	@PostMapping("/commentpage") // HomePage
	@SessionScope
	public String homepage(@RequestParam("username") String username, @RequestParam("password") String password,
			Model model, HttpSession session) {

		String user = username.toString();
		session.setAttribute("username", user);
		//session.setMaxInactiveInterval(60);

		UserEntity ue;

		if (uks.findUserEntityByName(user) == null) {

			return "redirect:/login";

		}

		ue = uks.findUserEntityByName(user);

		if (username.equals(ue.getName())) {

			if (password.equals(ue.getPassword())) {

				
				model.addAttribute("user", ue);
				return "commentpage";
			}
		}
		return "redirect:/login";

	}

	

	@GetMapping("/register") // Registration Form
	public String gotoregisterpage() {

		return "register";
	}
	
	
	@PostMapping("/adduser") // After submitting Registration Form
	public String addnewregister(@RequestParam("username") String username, @RequestParam("password") String password,
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
		uks.save(us);

		return "redirect:/login";

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
		
		CommentEntity ce=new CommentEntity(text,timestamp,ue);
		
		uks.savecomment(ce);
		
		List<CommentEntity> list=uks.findallcomments();
		Comparator<CommentEntity> co=(e1, e2) -> e1.getInsert_ts().compareTo(e2.getInsert_ts());
	
		
		Collections.reverseOrder(co);
		
		Collections.sort(list,co);	
		Collections.reverse(list);
		
		System.out.println(list.get(0).getInsert_ts());
		model.addAttribute("user", ue);
		model.addAttribute("list",list);

		return "commentpage";
	}
	
	

}
