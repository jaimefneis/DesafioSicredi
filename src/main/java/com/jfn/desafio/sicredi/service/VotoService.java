package com.jfn.desafio.sicredi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.jfn.desafio.sicredi.entity.Pauta;
import com.jfn.desafio.sicredi.entity.SessaoVotacao;
import com.jfn.desafio.sicredi.entity.Voto;
import com.jfn.desafio.sicredi.exception.CpfDuplicatedForVotingException;
import com.jfn.desafio.sicredi.exception.PautaNotFoundException;
import com.jfn.desafio.sicredi.exception.SessaoVotacaoNotAvailableForVotingException;
import com.jfn.desafio.sicredi.exception.SessaoVotacaoNotFoundException;
import com.jfn.desafio.sicredi.repository.SessaoVotacaoRepository;
import com.jfn.desafio.sicredi.repository.VotoRepository;

@Service
public class VotoService {

	@Autowired
	private VotoRepository votoRepository;
	
	@Autowired
	private SessaoVotacaoRepository sessaoVotacaoRepository;
	
	public Voto votar(int sessaoVotacaoId, String cpf, boolean agree) throws SessaoVotacaoNotFoundException, SessaoVotacaoNotAvailableForVotingException, CpfDuplicatedForVotingException {
		
		SessaoVotacao sessaoVotacao = this.sessaoVotacaoRepository.findById(sessaoVotacaoId)
				.orElseThrow(() -> new SessaoVotacaoNotFoundException(sessaoVotacaoId));
				
		if (!this.isSessaoVotacaoOpen(sessaoVotacao)) {
			throw new SessaoVotacaoNotAvailableForVotingException();
		}
		
		if (!this.isCpfValid(cpf)) {
			
		}
		
		if (this.isCpfDuplicatedForVoting(sessaoVotacaoId, cpf)) {
			throw new CpfDuplicatedForVotingException();
		}
		
		Voto voto = new Voto();
		voto.setSessaoVotacao(sessaoVotacao);
		voto.setCpf(cpf);
		voto.setAgree(agree);
		
		return this.votoRepository.save(voto);		
	}
	
	private boolean isSessaoVotacaoOpen(SessaoVotacao sessaoVotacao) {
		Date now = new Date();
		
		return now.after(sessaoVotacao.getStartDate()) && now.before(sessaoVotacao.getEndDate());
	}

	private boolean isCpfValid(String cpf)
	{
		return true;
	}
	
	private boolean isCpfDuplicatedForVoting(int sessaoVotacaoId, String cpf)
	{
		return false;
		//List<Voto> votos = this.votoRepository.findBySessaoVotacaoIdAndCpf(sessaoVotacaoId, cpf);
		//return votos != null && votos.size() > 0;
	}
	
}
