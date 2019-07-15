package com.example.springbootjwttest.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.example.springbootjwttest.entity.User;

@Service
public class UserService {

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
