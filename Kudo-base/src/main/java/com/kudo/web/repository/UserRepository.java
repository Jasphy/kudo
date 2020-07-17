package com.kudo.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kudo.web.entity.UserEntity;


public interface UserRepository extends JpaRepository<UserEntity,Integer>{

	
	
	@Query("SELECT ue FROM UserEntity ue WHERE ue.name = :name")
	UserEntity findUserByName(@Param("name") String user);

}
