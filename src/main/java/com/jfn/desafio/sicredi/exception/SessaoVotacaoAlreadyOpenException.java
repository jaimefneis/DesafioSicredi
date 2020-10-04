package com.jfn.desafio.sicredi.exception;

public class SessaoVotacaoAlreadyOpenException extends SicrediRuntimeException {

	public SessaoVotacaoAlreadyOpenException(int pautaId)
	{
		super(String.format("A sessão de votação para pauta %d já está aberta", pautaId));
	}
}
