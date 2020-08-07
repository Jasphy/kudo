package com.kudo.web.service;

import java.sql.Blob;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kudo.web.entity.CommentEntity;
import com.kudo.web.entity.UserEntity;
import com.kudo.web.entity.User_comment;
import com.kudo.web.repository.KudoRepository;
import com.kudo.web.repository.UserRepository;
import com.kudo.web.repository.User_kudoRepository;
import com.kudo.web.repository.User_kudorepositoryimpl;

@Service
public class UserKudoService {
	
	@Autowired
	UserRepository usr;
	
	@Autowired
	KudoRepository kr;
	
	@Autowired
	User_kudoRepository ukr;
	
	@Autowired
	User_kudorepositoryimpl ukri;

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

	public void sort(List<CommentEntity> list) {
		// TODO Auto-generated method stub
		
Comparator<CommentEntity> co=(e1, e2) -> e1.getInsert_ts().compareTo(e2.getInsert_ts());
	
		
		Collections.reverseOrder(co);
		
		Collections.sort(list,co);	
		Collections.reverse(list);
		
	}

	public CommentEntity findcommentbyid(Integer id) {
		// TODO Auto-generated method stub
		
		return kr.findById(id).get();
		
	}

	public void updatelikecount(int count, Integer id) {
		// TODO Auto-generated method stub
		
		kr.updatelikecount(new Integer(count),id);
		
	}

	public void deletecomment(Integer id) {
		// TODO Auto-generated method stub
		
		kr.deleteById(id);
	}

	public void usercommentmap(UserEntity attribute, CommentEntity ce) {
		// TODO Auto-generated method stub
		
		User_comment uc=new User_comment(attribute.getId(),ce.getComment_id(),new Integer(1));
		//ukr.save(uc);
		
		
		ukri.insert(uc.getUser_id(),uc.getComment_id(),uc.getLike());
	}

	public User_comment findusercommentmap(UserEntity attribute, CommentEntity ce) {
		// TODO Auto-generated method stub
		
		User_comment uc=ukr.findbyUseridCommentid(attribute.getId(),ce.getComment_id());
		
		
		return uc ;
	}

	public UserEntity findUserById(Integer id) {
		// TODO Auto-generated method stub
		
		UserEntity ue=usr.findById(id).get();
		return ue;
	}

	public void updatephoto(Integer id, Blob blob) {
		// TODO Auto-generated method stub
		
		usr.updatephoto(id,blob);
		
	}

}
