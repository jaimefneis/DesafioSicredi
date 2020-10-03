package com.jfn.desafio.sicredi.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfn.desafio.sicredi.dto.SessaoVotacaoVotoDTO;
import com.jfn.desafio.sicredi.entity.Pauta;
import com.jfn.desafio.sicredi.entity.SessaoVotacao;
import com.jfn.desafio.sicredi.entity.Voto;
import com.jfn.desafio.sicredi.exception.PautaNotFoundException;
import com.jfn.desafio.sicredi.exception.SessaoVotacaoAlreadyOpenException;
import com.jfn.desafio.sicredi.exception.SessaoVotacaoNotOpenException;
import com.jfn.desafio.sicredi.repository.PautaRepository;
import com.jfn.desafio.sicredi.repository.SessaoVotacaoRepository;

@Service
public class SessaoVotacaoService {	
	
	@Autowired
	private SessaoVotacaoRepository sessaoVotacaoRepository;
	
	@Autowired
	private PautaRepository pautaRepository;
		
	public SessaoVotacao abrirSessaoVotacao(int pautaId, int timeoutInMinutes) throws PautaNotFoundException, SessaoVotacaoAlreadyOpenException {	
				
		Pauta pauta = this.pautaRepository.findById(pautaId)
			.orElseThrow(() -> new PautaNotFoundException(pautaId));
				
		if (this.isSessaoVotacaoAlreadyOpen(pautaId)) {
			throw new SessaoVotacaoAlreadyOpenException();
		}
		
		Date startDate = new Date();		
		Date endDate = getEndDate(startDate, timeoutInMinutes);
		
		SessaoVotacao sessaoVotacao = new SessaoVotacao();		
		sessaoVotacao.setPauta(pauta);
		sessaoVotacao.setStartDate(startDate);
		sessaoVotacao.setEndDate(endDate);
		
		return this.sessaoVotacaoRepository.save(sessaoVotacao);
	}
	
	private boolean isSessaoVotacaoAlreadyOpen(int pautaId)
	{
		SessaoVotacao sessaoVotacao = this.sessaoVotacaoRepository.findByPautaId(pautaId);
		return sessaoVotacao != null;
	}
	
	private Date getEndDate(Date startDate, int timeoutInMinutes)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.MINUTE, timeoutInMinutes);
		return calendar.getTime();
	}
	
	public SessaoVotacaoVotoDTO obterResultadoPauta(int pautaId) throws PautaNotFoundException, SessaoVotacaoNotOpenException
	{
		this.pautaRepository.findById(pautaId).orElseThrow(() -> new PautaNotFoundException(pautaId));
			
		SessaoVotacao sessaoVotacao = this.sessaoVotacaoRepository.findByPautaId(pautaId);
		if (sessaoVotacao == null) {
			throw new SessaoVotacaoNotOpenException();
		}
		
		SessaoVotacaoVotoDTO dto = new SessaoVotacaoVotoDTO();
		List<Voto> votos = sessaoVotacao.getVotos();
		if (votos != null) {			
			dto.setTotalAgree(votos.stream().filter(p -> p.isAgree()).count());
			dto.setTotalNotAgree(votos.stream().filter(p -> !p.isAgree()).count());
		}
		
		return dto;
	}
	
	
}
