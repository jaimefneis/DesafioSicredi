package com.jfn.desafio.sicredi.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SicrediResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
		
	@ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {
    	HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    	if(ex instanceof SicrediRuntimeException) {
    		httpStatus = ((SicrediRuntimeException)ex).getHttpStatus();   		
    	}
    	
    	SicrediError error = new SicrediError(ex.getMessage(), httpStatus);
    	
    	return handleExceptionInternal(ex, error, new HttpHeaders(), httpStatus, request);
    }
	
}
