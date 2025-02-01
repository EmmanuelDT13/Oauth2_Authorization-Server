package com.emmanuel.api.authorization_server.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emmanuel.api.authorization_server.persistence.entity.ClientApp;

public interface ClientAppRepository  extends JpaRepository<ClientApp, Long>{

	Optional<ClientApp> findByClientId(String clientId);
	
}
