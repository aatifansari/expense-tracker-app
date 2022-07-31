package com.skywalker.expensetracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EtResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public EtResourceNotFoundException() {
		super();
	}
	
	public EtResourceNotFoundException(String message){
		super(message);
	}
	
	public EtResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public EtResourceNotFoundException(Throwable cause) {
		super(cause);
	}
}
