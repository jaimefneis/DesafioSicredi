package com.jfn.desafio.sicredi.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SicrediError {	
    private String message;
    private HttpStatus httpStatus;      
}
