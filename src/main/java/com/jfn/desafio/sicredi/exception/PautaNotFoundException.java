package com.jfn.desafio.sicredi.exception;

import org.springframework.http.HttpStatus;

public class PautaNotFoundException extends SicrediRuntimeException {

	public PautaNotFoundException(int pautaId)
	{
		super(String.format("A pauta %d não foi encontrada", pautaId), HttpStatus.NOT_FOUND);
	}
}
