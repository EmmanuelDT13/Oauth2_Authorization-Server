package com.emmanuel.api.authorization_server.mapper;

import java.util.stream.Collectors;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.Date;

import com.emmanuel.api.authorization_server.persistence.entity.ClientApp;

public class ClientAppMapper {

	
	//This method is for converting clientApp objects to RegisteredClient
	public static RegisteredClient toRegisteredClient(ClientApp clientApp) {
		
		RegisteredClient registeredClient = RegisteredClient.withId(clientApp.getClientId())
				.clientId(clientApp.getClientId())
				.clientSecret(clientApp.getClientSecret())
				.clientIdIssuedAt(new Date(System.currentTimeMillis()).toInstant())
				.clientAuthenticationMethods(clientAuthMethods -> { 
					clientApp.getClientAuthenticationMethods().stream().map(el -> new ClientAuthenticationMethod(el)).forEach(clientAuthMethods::add);
				})
				.authorizationGrantTypes(authGrantTypes -> {
					clientApp.getAuthorizationGrantTypes().stream().map(grantType -> new AuthorizationGrantType(grantType)).forEach(authGrantTypes::add);
				})
				.redirectUris(redirectUris -> {
					clientApp.getRedirectUris().stream().forEach(redirectUris::add);
				})
				.scopes(scopes -> {
					clientApp.getScopes().stream().forEach(scopes::add);
				} )
				.tokenSettings(TokenSettings.builder()
						.accessTokenTimeToLive(Duration.ofMinutes(clientApp.getDurationInMinutes()))
						.refreshTokenTimeToLive(Duration.ofMinutes(clientApp.getDurationInMinutes()*4)).build())
				.clientSettings(ClientSettings.builder().requireProofKey(clientApp.isRequieredProofKey()).build())
				.build();
		
		return registeredClient;
	}
	
}
