package com.emmanuel.api.authorization_server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

import com.emmanuel.api.authorization_server.exception.ObjectNotFoundException;
import com.emmanuel.api.authorization_server.mapper.ClientAppMapper;
import com.emmanuel.api.authorization_server.persistence.entity.ClientApp;
import com.emmanuel.api.authorization_server.persistence.repository.ClientAppRepository;

@Service
public class RegisteredClientService implements RegisteredClientRepository{

	
	@Autowired
	private ClientAppRepository clientAppRepository;
	
	@Override
	public void save(RegisteredClient registeredClient) {
	}

	@Override
	public RegisteredClient findById(String id) {
		ClientApp clientApp = clientAppRepository.findByClientId(id).orElseThrow(() -> new ObjectNotFoundException("Client app doesn't existe"));
		return ClientAppMapper.toRegisteredClient(clientApp);
	}

	@Override
	public RegisteredClient findByClientId(String clientId) {
		return this.findById(clientId);
	}

}
