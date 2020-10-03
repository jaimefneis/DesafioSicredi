package com.jfn.desafio.sicredi.service;

 import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfn.desafio.sicredi.entity.Pauta;
import com.jfn.desafio.sicredi.repository.PautaRepository;

@Service
public class PautaService {
	
	@Autowired
	private PautaRepository pautaRepository;

	public Pauta cadastrarPauta(Pauta pauta) {		
		return this.pautaRepository.save(pauta);	
	}
	
	public List<Pauta> obterPautas() {
		return this.pautaRepository.findAll();
	}
}
