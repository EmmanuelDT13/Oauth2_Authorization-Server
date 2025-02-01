package com.emmanuel.api.authorization_server.persistence.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.emmanuel.api.authorization_server.persistence.entity.User;


public interface UserRepository extends JpaRepository<User, Long>{

	public Optional<User> getByUsername(String username);
	
}
