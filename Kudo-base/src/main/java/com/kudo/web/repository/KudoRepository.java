package com.kudo.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.kudo.web.entity.CommentEntity;


public interface KudoRepository extends JpaRepository<CommentEntity,Integer >{

	
	@Transactional
	  @Modifying
	@Query("update CommentEntity ce set ce.like_count=:count where ce.comment_id=:id")
	void updatelikecount(@Param("count") Integer integer,@Param("id") Integer id);
	
	

}
