package com.example.springbootjwttest.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springbootjwttest.entity.User;
import com.example.springbootjwttest.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
//	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public String signIn(String username, String password) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public String signUp(User user) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public void delete(String username) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public Object search(String username) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public Object whoAmI(HttpServletRequest req) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public String refresh(String remoteUser) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

}
