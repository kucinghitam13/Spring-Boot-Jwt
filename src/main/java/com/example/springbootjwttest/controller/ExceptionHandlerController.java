package com.example.springbootjwttest.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.springbootjwttest.response.CustomException;

@RestControllerAdvice
public class ExceptionHandlerController {

//	public ErrorAttributes errorAttributes() {
//		return new DefaultErrorAttributes() {
//			@Override
//			public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
//				// TODO Auto-generated method stub
//				return super.getErrorAttributes(webRequest, includeStackTrace);
//			}
//		};
//	}
	
	@ExceptionHandler(CustomException.class)
	public void handleCustomException(HttpServletResponse response, CustomException ex) throws IOException{
		response.sendError(ex.getHttpStatus().value(), ex.getMessage());
	}

	@ExceptionHandler(AccessDeniedException.class)
	public void handleAccessDeniedException(HttpServletResponse response) throws IOException{
		response.sendError(HttpStatus.FORBIDDEN.value(), "Access is denied");
	}
	@ExceptionHandler(Exception.class)
	public void handleGenericException(HttpServletResponse response) throws IOException{
		response.sendError(HttpStatus.BAD_REQUEST.value(), "Something went wrong");
	}
}
