package com.emmanuel.api.authorization_server.controller;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController {

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	
	@GetMapping("/logout")
	public String logout() {
		return "logout";
	}
	
	@PostMapping("/logout")
	public String logoutok(HttpSecurity httpSecurity) throws Exception{
		
		httpSecurity.logout(logoutConfig ->{
			logoutConfig.logoutSuccessUrl("login?logout")
			.deleteCookies("SESSIONID")
			.clearAuthentication(true)
			.invalidateHttpSession(true);
		});
		return "login?logout";
	}
}
