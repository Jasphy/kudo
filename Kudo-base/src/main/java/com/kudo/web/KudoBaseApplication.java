package com.kudo.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)  
@PropertySource(value = {"classpath:configuration.properties"})
public class KudoBaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(KudoBaseApplication.class, args);
	}

}
