package com.kudo.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kudo.web.entity.CommentEntity;
import com.kudo.web.entity.UserEntity;
import com.kudo.web.repository.KudoRepository;
import com.kudo.web.repository.UserRepository;

@Service
public class UserKudoService {
	
	@Autowired
	UserRepository usr;
	
	@Autowired
	KudoRepository kr;

	public void save(UserEntity us) {
		// TODO Auto-generated method stub
		
		usr.save(us);
		
	}

	public UserEntity findUserEntityByName(String user) {
		// TODO Auto-generated method stub
		
		UserEntity ue=usr.findUserByName(user);
		return ue;
	}

	public void savecomment(CommentEntity ce) {
		// TODO Auto-generated method stub
		
		kr.save(ce);
		
	}

	public List<CommentEntity> findallcomments() {
		// TODO Auto-generated method stub
		
		
		List<CommentEntity> ls=kr.findAll();
		return ls;
		
	}

}
