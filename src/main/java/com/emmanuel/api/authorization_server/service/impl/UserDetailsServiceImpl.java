package com.emmanuel.api.authorization_server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.emmanuel.api.authorization_server.exception.ObjectNotFoundException;
import com.emmanuel.api.authorization_server.persistence.entity.User;
import com.emmanuel.api.authorization_server.persistence.repository.UserRepository;



@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userrepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userrepository.getByUsername(username).orElseThrow(()-> new ObjectNotFoundException("No pudo ser encontrado el sujeto"));
		return user;
	}

}
