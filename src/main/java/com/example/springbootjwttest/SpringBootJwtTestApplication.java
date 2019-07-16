package com.example.springbootjwttest;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.springbootjwttest.entity.Role;
import com.example.springbootjwttest.entity.User;
import com.example.springbootjwttest.service.UserService;

@SpringBootApplication
public class SpringBootJwtTestApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJwtTestApplication.class, args);
	}

	
	@Autowired
	private UserService userService;
	
	@Override
	public void run(String... args) throws Exception {
		User admin = new User();
	    admin.setUsername("kucinghitam");
	    admin.setPassword("blackcat");
	    admin.setEmail("admin@email.com");
	    admin.setRoles(new ArrayList<Role>(Arrays.asList(
	    		Role.ROLE_ADMIN,
	    		Role.ROLE_CLIENT
	    		)));

	    userService.signUp(admin);

	    User client = new User();
	    client.setUsername("kucingputih");
	    client.setPassword("whitecat");
	    client.setEmail("client@email.com");
	    client.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));

	    userService.signUp(client);
	}

}
