package com.example.springbootjwttest.response;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class CustomException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private final String message;
	private final HttpStatus httpStatus;

}
