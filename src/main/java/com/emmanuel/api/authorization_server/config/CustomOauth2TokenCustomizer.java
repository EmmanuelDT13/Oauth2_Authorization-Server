package com.emmanuel.api.authorization_server.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

@Component
public class CustomOauth2TokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext>{


	@Override
	public void customize(JwtEncodingContext context) {
		// TODO Auto-generated method stub
		
		Authentication authenticacion = context.getPrincipal();
		
		String token_type = context.getTokenType().getValue();
		
		
		System.out.println("Adding extra claims.");
		if (token_type.equals("access_token")) {
			List<String> permissions = authenticacion.getAuthorities().stream().map(el -> el.getAuthority()).collect(Collectors.toList());
			context.getClaims().claim("permissions", permissions);
		}
		
	}

}
