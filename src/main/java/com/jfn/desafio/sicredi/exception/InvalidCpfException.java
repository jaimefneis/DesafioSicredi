package com.jfn.desafio.sicredi.exception;

public class InvalidCpfException extends SicrediRuntimeException {
	public InvalidCpfException(String cpf)
	{
		super(String.format("O CPF %s é inválido", cpf));
	}
}
