package com.emmanuel.api.authorization_server.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
public class AuthorizationSecurityConfig {

	@Bean
	@Order(1)
	SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity httpSecurity) throws Exception{
		
		OAuth2AuthorizationServerConfigurer oAuth2AuthorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer();
		
		httpSecurity.securityMatcher(oAuth2AuthorizationServerConfigurer.getEndpointsMatcher())
		.with(oAuth2AuthorizationServerConfigurer, (authorizationServer) ->{
			authorizationServer.oidc(Customizer.withDefaults());
		})
		.authorizeHttpRequests(authorize ->{
			authorize.anyRequest().authenticated();
		})
		.exceptionHandling(exceptions ->{
			exceptions.defaultAuthenticationEntryPointFor(new LoginUrlAuthenticationEntryPoint("/login"), new MediaTypeRequestMatcher(MediaType.TEXT_HTML));
		});
		return httpSecurity.build();
	}
	
	
	@Bean
	@Order(2)
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception{
		
		httpSecurity.authorizeHttpRequests(authorize -> {
			authorize.requestMatchers("/login").permitAll();
			authorize.anyRequest().authenticated();
		})
		.formLogin(Customizer.withDefaults());
		return httpSecurity.build();
	}
	
	//Here, we a utilizing the key pair previously created and wrapping then so that spring security and oauth2 are able to use them for authorizing process.
	@Bean 
	JWKSource<SecurityContext> jwkSource() {
		KeyPair keyPair = generateRsaKey();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAKey rsaKey = new RSAKey.Builder(publicKey)
				.privateKey(privateKey)
				.keyID(UUID.randomUUID().toString())
				.build();
		JWKSet jwkSet = new JWKSet(rsaKey);
		return new ImmutableJWKSet<>(jwkSet);
	}
	
	//Here, we create the KeyPair, but Spring Security and Oauth2 are not able to use them to authorizing resources.
	private static KeyPair generateRsaKey() { 
		KeyPair keyPair;
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			keyPair = keyPairGenerator.generateKeyPair();
		}
		catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
		return keyPair;
	}
	
	@Bean 
	JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}
	
	@Bean 
	AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder().issuer("http://localhost:9595/authorization-server") .build();
	}
	
}
