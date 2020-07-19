package com.kudo.web.entity;

import java.sql.Timestamp;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="user_comment")
@Cacheable(false)
public class User_comment {
	
	public User_comment() {}
	
public User_comment(Integer user_id, Integer comment_id, Integer like) {
		super();
		this.user_id = user_id;
		this.comment_id = comment_id;
		this.like = like;
	}


private static final long serialVersionUID = -723583058586873479L;
	
	@Id
	@Column(name="user_id")
	private Integer user_id;

	public Integer getUser_id() {
		return user_id;
	}


	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}


	public Integer getComment_id() {
		return comment_id;
	}


	public void setComment_id(Integer comment_id) {
		this.comment_id = comment_id;
	}


	public Integer getLike() {
		return like;
	}


	public void setLike(Integer like) {
		this.like = like;
	}


	@Column(name="comment_id")
	private Integer comment_id;

	
	@Column(name="like_option")
	private Integer like;
	
	

}
