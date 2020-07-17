package com.kudo.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kudo.web.entity.CommentEntity;


public interface KudoRepository extends JpaRepository<CommentEntity,Integer >{
	
	

}
