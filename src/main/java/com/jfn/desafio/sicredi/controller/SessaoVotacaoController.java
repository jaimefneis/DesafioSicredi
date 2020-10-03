package com.jfn.desafio.sicredi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jfn.desafio.sicredi.dto.SessaoVotacaoVotoDTO;
import com.jfn.desafio.sicredi.entity.SessaoVotacao;
import com.jfn.desafio.sicredi.exception.PautaNotFoundException;
import com.jfn.desafio.sicredi.exception.SessaoVotacaoAlreadyOpenException;
import com.jfn.desafio.sicredi.exception.SessaoVotacaoNotOpenException;
import com.jfn.desafio.sicredi.service.SessaoVotacaoService;

@RestController
public class SessaoVotacaoController {

	@Autowired
	private SessaoVotacaoService sessaoVotacaoService;
	
	@PostMapping("/abrirSessaoVotacao/{pautaId}")	
	public SessaoVotacao abrirSessaoVotacao(@PathVariable int pautaId, @RequestParam(value = "timeoutInMinutes", defaultValue = "1") int timeoutInMinutes)
	{	
		try {
			return this.sessaoVotacaoService.abrirSessaoVotacao(pautaId, timeoutInMinutes);
		} catch (PautaNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		} catch (SessaoVotacaoAlreadyOpenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/obterResultadoPauta/{pautaId}")
	public SessaoVotacaoVotoDTO obterResultadoPauta(@PathVariable int pautaId) {
		try {
			return this.sessaoVotacaoService.obterResultadoPauta(pautaId);
		} catch (PautaNotFoundException | SessaoVotacaoNotOpenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
