package com.jfn.desafio.sicredi.exception;

public class SessaoVotacaoNotOpenException extends SicrediRuntimeException {
	
	public SessaoVotacaoNotOpenException(int pautaId)
	{
		super(String.format("A sessão de votação para pauta %d está encerrada", pautaId));
	}
	
}
