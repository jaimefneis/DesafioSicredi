package com.jfn.desafio.sicredi.exception;

public class SessaoVotacaoNotAvailableForVotingException extends SicrediRuntimeException {
	
	public SessaoVotacaoNotAvailableForVotingException(int pautaId)
	{
		super(String.format("A sessão de votação para pauta %d não está aberta", pautaId));
	}
	
}
