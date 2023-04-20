package com.electronicshop.exceptions;

public class ExceptionHandler {
	
	@org.springframework.web.bind.annotation.ExceptionHandler({Exception.class})
	public void resolveException(Exception e) {
	    e.printStackTrace();
	}
}
