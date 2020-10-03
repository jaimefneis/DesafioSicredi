package com.jfn.desafio.sicredi.exception;

public class CpfDuplicatedForVotingException extends RuntimeException {
	
	public CpfDuplicatedForVotingException(int pautaId, String cpf)
	{
		super(String.format("O CPF %s já votou na pauta %d", cpf, pautaId));
	}
	
}
