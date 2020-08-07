package com.kudo.web.entity;

import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="User")
@JsonAutoDetect
public class UserEntity {
	
private static final long serialVersionUID = -723583058586873479L;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	
	@Column(name="name")
	private String name;
	@Column(name="password")
	private String password;
	@Column(name="email")
	private String email;
	@Column(name="sex")
	private String gender;
	@Column(name="country")
	private String country;
	@Lob
	@Column(name="image")
	private Blob photo;
	
	 
	
	
	
	@OneToMany(cascade=CascadeType.PERSIST,mappedBy="user")
	private Set<CommentEntity> comments=new HashSet<>();
public UserEntity() {}
	
	public UserEntity(String username, String password2, String email2, String gender2, String country2,
			Blob blob) {
		// TODO Auto-generated constructor stub
		

		
		this.name = username;
		this.password = password2;
		this.email = email2;
		this.gender = gender2;
		this.country = country2;
		this.photo=blob;
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Blob getPhoto() {
		return photo;
	}
	public void setPhoto(Blob photo) {
		this.photo = photo;
	}
	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email + ", gender="
				+ gender + ", country=" + country + ", photo=" + photo + "]";
	}

	
	

}

/*
 * CREATE TABLE User ( id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, name
 * varchar(30) NOT NULL, password varchar(30) NOT NULL, email varchar(30) NOT
 * NULL, sex varchar(30) NOT NULL, country varchar(30) NULL, image blob null);
 */
