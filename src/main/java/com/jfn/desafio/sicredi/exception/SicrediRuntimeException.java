package com.jfn.desafio.sicredi.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.Getter;


public class SicrediRuntimeException extends RuntimeException {
	
	@Getter
	private HttpStatus httpStatus;
	
	public SicrediRuntimeException(String message)
	{
		this(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public SicrediRuntimeException(String message, HttpStatus httpStatus)
	{
		super(message);
		this.httpStatus = httpStatus;
	}
}
