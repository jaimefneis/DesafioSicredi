package com.jfn.desafio.sicredi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfn.desafio.sicredi.entity.Pauta;
import com.jfn.desafio.sicredi.entity.Voto;
import com.jfn.desafio.sicredi.exception.CpfDuplicatedForVotingException;
import com.jfn.desafio.sicredi.exception.CpfInvalidException;
import com.jfn.desafio.sicredi.exception.PautaNotFoundException;
import com.jfn.desafio.sicredi.exception.SessaoVotacaoNotAvailableForVotingException;
import com.jfn.desafio.sicredi.repository.PautaRepository;
import com.jfn.desafio.sicredi.repository.VotoRepository;

@Service
public class VotoService {

	@Autowired
	private VotoRepository votoRepository;
	
	@Autowired
	private PautaRepository pautaRepository;
	
	public Voto votar(int pautaId, String cpf, boolean agree) {
		
		Pauta pauta = this.pautaRepository.findById(pautaId)
				.orElseThrow(() -> new PautaNotFoundException(pautaId));
				
		if (!this.isSessaoVotacaoOpen(pauta)) {
			throw new SessaoVotacaoNotAvailableForVotingException(pautaId);
		}
		
		if (!this.isCpfValid(cpf)) {
			throw new CpfInvalidException(cpf);
		}
		
		if (this.isCpfDuplicatedForVoting(pautaId, cpf)) {
			throw new CpfDuplicatedForVotingException(pautaId, cpf);
		}
		
		if (agree) // TODO Race condition can occur here
		{
			pauta.setTotalYes(pauta.getTotalYes() + 1);
		}
		else
		{
			pauta.setTotalNo(pauta.getTotalNo() + 1);
		}
		this.pautaRepository.save(pauta);
		
		Voto voto = new Voto();
		voto.setPauta(pauta);
		voto.setCpf(cpf);
		voto.setAgree(agree);
		
		return this.votoRepository.save(voto);		
	}
	
	private boolean isSessaoVotacaoOpen(Pauta pauta) {
		Date now = new Date();
		
		return now.after(pauta.getStartDate()) && now.before(pauta.getEndDate());
	}

	private boolean isCpfValid(String cpf)
	{
		return true;
	}
	
	private boolean isCpfDuplicatedForVoting(int pautaId, String cpf)
	{		
		List<Voto> votos = this.votoRepository.findByPautaIdAndCpf(pautaId, cpf);
		return votos != null && votos.size() > 0;
	}
	
}
