package com.skywalker.expensetracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EtBadRequestException extends RuntimeException{
	
	public EtBadRequestException() {
		super();
	}
	
	public EtBadRequestException(String msg) {
		super(msg);
	}

}
