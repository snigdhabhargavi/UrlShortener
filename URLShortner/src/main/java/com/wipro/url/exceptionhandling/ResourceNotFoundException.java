package com.wipro.url.exceptionhandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger =  LoggerFactory.getLogger(ResourceNotFoundException.class);
	
	public ResourceNotFoundException() {
        super();
    }
	
	public ResourceNotFoundException(String message) {
		super(message);
		logger.error("Error {}", message);
	}
	
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
	
}
