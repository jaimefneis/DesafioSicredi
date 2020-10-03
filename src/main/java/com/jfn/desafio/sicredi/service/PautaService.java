package com.jfn.desafio.sicredi.service;

 import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfn.desafio.sicredi.entity.Pauta;
import com.jfn.desafio.sicredi.exception.PautaNotFoundException;
import com.jfn.desafio.sicredi.exception.SessaoVotacaoAlreadyOpenException;
import com.jfn.desafio.sicredi.exception.SessaoVotacaoNotOpenException;
import com.jfn.desafio.sicredi.repository.PautaRepository;

@Service
public class PautaService {
	
	@Autowired
	private PautaRepository pautaRepository;

	public Pauta cadastrarPauta(String description) {
		Pauta pauta = new Pauta();
		pauta.setDescription(description);
		
		return this.pautaRepository.save(pauta);	
	}
	
	public List<Pauta> obterPautas() {
		return this.pautaRepository.findAll();
	}
	
	public Pauta abrirSessaoVotacao(int pautaId, int timeoutInMinutes) {	
		
		Pauta pauta = this.pautaRepository.findById(pautaId)
			.orElseThrow(() -> new PautaNotFoundException(pautaId));
				
		if (this.isSessaoVotacaoAlreadyOpen(pauta)) {
			throw new SessaoVotacaoAlreadyOpenException(pautaId);
		}
		
		Date startDate = new Date();		
		Date endDate = getEndDate(startDate, timeoutInMinutes);
		
		pauta.setStartDate(startDate);
		pauta.setEndDate(endDate);
		pauta.setTotalYes(0);
		pauta.setTotalNo(0);
		
		return this.pautaRepository.save(pauta);
	}
	
	private boolean isSessaoVotacaoAlreadyOpen(Pauta pauta) {		
		return pauta.getStartDate() != null;
	}
	
	private Date getEndDate(Date startDate, int timeoutInMinutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.MINUTE, timeoutInMinutes);
		return calendar.getTime();
	}
	
	public Pauta obterResultadoPauta(int pautaId) {
		Pauta pauta = this.pautaRepository.findById(pautaId).orElseThrow(() -> new PautaNotFoundException(pautaId));
			
		if (pauta.getStartDate() == null) {
			throw new SessaoVotacaoNotOpenException(pautaId);
		}
		
		return pauta;
	}
}
