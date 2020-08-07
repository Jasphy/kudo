package com.kudo.web.repository;

import java.sql.Blob;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kudo.web.entity.UserEntity;

@Repository
public interface 

UserRepository extends JpaRepository<UserEntity,Integer>{

	
	
	@Query("SELECT ue FROM UserEntity ue WHERE ue.name = :name")
	UserEntity findUserByName(@Param("name") String user);

	@Transactional
	@Modifying
	@Query("update UserEntity ue set ue.photo=:blob where ue.id=:id")
	void updatephoto(@Param("id")Integer id, @Param("blob")Blob blob);

	
	
	
	@Modifying(clearAutomatically = true)
    @Transactional
    @Query(value="update UserEntity set password=:password where email=:email")
    int updatePassword(@Param("email") String email,@Param("password") String password);

	

}
