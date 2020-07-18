package com.kudo.web.entity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="Comment")
@Cacheable(false)
public class CommentEntity {
	
	

	public CommentEntity(Integer like_count) {
		super();
		this.like_count = like_count;
	}

	public CommentEntity(String text_message, Timestamp insert_ts, UserEntity user,Integer like_count) {
		super();
		this.text_message = text_message;
		this.insert_ts = insert_ts;
		this.user = user;
		this.like_count=like_count;
	}
	
	public CommentEntity() {}

	private static final long serialVersionUID = -723583058586873479L;
	
	@Id
	@Column(name="comment_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer comment_id;
	
	
	@Column(name="text_message")
	private String text_message;
	
	@Column(name="Insert_ts")
	private Timestamp insert_ts;
	@Column(name="like_count")
	private Integer like_count;
	
	@ManyToOne
	private UserEntity user;

	public Integer getComment_id() {
		return comment_id;
	}

	public void setComment_id(Integer comment_id) {
		this.comment_id = comment_id;
	}

	public String getText_message() {
		return text_message;
	}

	public void setText_message(String text_message) {
		this.text_message = text_message;
	}

	public Timestamp getInsert_ts() {
		return insert_ts;
	}

	public void setInsert_ts(Timestamp insert_ts) {
		this.insert_ts = insert_ts;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
	public Integer getLike_count() {
		return like_count;
	}

	public void setLike_count(Integer like_count) {
		this.like_count = like_count;
	}
	
	

	
	
	
	

}

/*
 * CREATE TABLE Comment ( comment_id int(11) NOT NULL PRIMARY KEY
 * AUTO_INCREMENT, Text_message varchar(30) NOT NULL, Insert_ts timestamp NULL,
 * Insert_user_id int(11), Foreign Key(Insert_user_id) references User(id));
 */