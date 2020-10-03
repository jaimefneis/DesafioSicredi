package com.jfn.desafio.sicredi.exception;

public class PautaNotFoundException extends RuntimeException {

	public PautaNotFoundException(int pautaId)
	{
		super(String.format("A pauta %d não foi encontrada", pautaId));
	}
}
