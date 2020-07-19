package com.kudo.web.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class User_kudorepositoryimpl {

	
	@PersistenceContext
    private EntityManager entityManager;

	/*
	 * public User_kudorepositoryimpl(Class domainClass, EntityManager em) {
	 * super(domainClass, em); // TODO Auto-generated constructor stub }
	 */
	@Transactional
	public void insert(Integer user_id, Integer comment_id, Integer like) {
		entityManager.createNativeQuery("INSERT INTO user_comment (user_id, comment_id, like_option) VALUES (?,?,?)")
	    .setParameter(1, user_id)
	    .setParameter(2, comment_id)
	    .setParameter(3, like)
	    .executeUpdate();

	}

}
