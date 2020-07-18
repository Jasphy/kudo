package com.kudo.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)  
public class KudoBaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(KudoBaseApplication.class, args);
	}

}
