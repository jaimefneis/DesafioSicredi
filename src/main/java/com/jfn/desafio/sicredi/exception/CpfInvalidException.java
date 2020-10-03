package com.jfn.desafio.sicredi.exception;

public class CpfInvalidException extends RuntimeException {
	public CpfInvalidException(String cpf)
	{
		super(String.format("O CPF %s é inválido", cpf));
	}
}
