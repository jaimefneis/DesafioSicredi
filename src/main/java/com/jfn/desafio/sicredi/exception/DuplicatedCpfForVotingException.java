package com.jfn.desafio.sicredi.exception;

public class DuplicatedCpfForVotingException extends SicrediRuntimeException {

	public DuplicatedCpfForVotingException(int pautaId, String cpf) {
		super(String.format("O CPF %s já votou na pauta %d", cpf, pautaId));
	}

}
