package com.hirehub.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<?> handleEmailExists(EmailAlreadyExistsException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("message", ex.getMessage());
		body.put("status", HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handleRuntime(RuntimeException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("message", ex.getMessage());
		body.put("status", HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
	
}
