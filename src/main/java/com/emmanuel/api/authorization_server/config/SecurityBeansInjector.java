package com.emmanuel.api.authorization_server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.emmanuel.api.authorization_server.service.impl.UserDetailsServiceImpl;


@Configuration
public class SecurityBeansInjector {

//	@Autowired
//	AuthenticationConfiguration authenticationConfiguration;
	
	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Bean
	AuthenticationProvider generateAuthenticationProvider() {
		DaoAuthenticationProvider my_daoauthenticationprovider = new DaoAuthenticationProvider();
		my_daoauthenticationprovider.setPasswordEncoder(this.generatePasswordEncoder());
		my_daoauthenticationprovider.setUserDetailsService(userDetailsServiceImpl);
		return my_daoauthenticationprovider;
	}
	
	@Bean
	PasswordEncoder generatePasswordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		
		System.out.println("Pass1: " + "calve1");
		System.out.println("Pass2: " + "calve2");
		System.out.println("Pass3: " + "calve3");
		
		return encoder;
	}
	
}
