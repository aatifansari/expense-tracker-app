package com.skywalker.expensetracker.exceptions;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@SuppressWarnings("unused")
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request){
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("Timestamp", LocalDateTime.now());
		String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
		if(StringUtils.hasLength(errorMessage)) {
			body.put("message", errorMessage);
			return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
		}
		else {
			body.put("message", "Invalid request");
			return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
		}
	}
	
	@ExceptionHandler(DateFormatException.class)
	protected ResponseEntity<Object> handleDateFormatException(DateFormatException ex){
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("Timestamp", LocalDateTime.now());
		if(StringUtils.hasLength(ex.getMessage())) {
			body.put("message", ex.getMessage());
			return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
		}
		else {
			body.put("message", "Invalid Date");
			return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
		}
	}
	
	@ExceptionHandler(EtAuthException.class)
	protected ResponseEntity<Object> handleEtAuthException(EtAuthException ex){
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("Timestamp", LocalDateTime.now());
		if(StringUtils.hasLength(ex.getMessage())) {
			body.put("message", ex.getMessage());
			return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
		}
		else {
			body.put("message", "Unauthorized");
			return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
		}
	}
	
	@ExceptionHandler(EtBadRequestException.class)
	protected ResponseEntity<Object> handleEtBadRequestException(EtBadRequestException ex){
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("Timestamp", LocalDateTime.now());
		if(StringUtils.hasLength(ex.getMessage())) {
			body.put("message", ex.getMessage());
			return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
		}
		else {
			body.put("message", "Invalid Request");
			return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
		}
	}
	
	

}
