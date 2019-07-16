package com.example.springbootjwttest.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springbootjwttest.entity.User;
import com.example.springbootjwttest.repository.UserRepository;
import com.example.springbootjwttest.response.CustomException;
import com.example.springbootjwttest.security.JwtTokenProvider;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public String signIn(String username, String password) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(username, password));
			return jwtTokenProvider.createToken(
					username,
					userRepository.findByUsername(username).get().getRoles());
		} catch (Exception e) {
			throw new CustomException("Invalid username/password", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public String signUp(User user) {
		if(!userRepository.existsByUsername(user.getUsername())) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User savedUser = userRepository.save(user);
			return jwtTokenProvider.createToken(savedUser.getUsername(), savedUser.getRoles());
		}else {
			throw new CustomException("Username is already exists", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public void delete(String username) {
		userRepository.deleteByUsername(username);
	}

	public User search(String username) {
		return returnUserIfExists(username);
	}

	public User whoAmI(HttpServletRequest req) {
		String token = jwtTokenProvider.resolveToken(req);
		return returnUserIfExists(jwtTokenProvider.getUsername(token));
	}

	public String refresh(String username) {
		User user = returnUserIfExists(username);
		return jwtTokenProvider.createToken(username, user.getRoles());
	}
	
	private User returnUserIfExists(String username) {
		return userRepository.findByUsername(username).orElseThrow(() ->
					new CustomException("Username " + username + " doesn't exists",
										HttpStatus.NOT_FOUND));
	}

}
