package com.kudo.web.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kudo.web.entity.User_comment;

public interface User_kudoRepository extends JpaRepository<User_comment,Integer>{

	
	
 


	@Query("select uc from User_comment uc where uc.comment_id=:comment_id and uc.user_id=:user_id")
	public User_comment findbyUseridCommentid(@Param("user_id")Integer id, @Param("comment_id")Integer comment_id);

	
	//@Query("INSERT INTO User_comment uc  (uc.user_id,uc.comment_id,uc.like_option) values (:user_id , :comment_id,:like)")
}
