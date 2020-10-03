package com.jfn.desafio.sicredi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfn.desafio.sicredi.entity.Pauta;
import com.jfn.desafio.sicredi.exception.PautaNotFoundException;
import com.jfn.desafio.sicredi.exception.SessaoVotacaoAlreadyOpenException;
import com.jfn.desafio.sicredi.service.PautaService;

@RestController
public class PautaController {
	
	@Autowired
	private PautaService pautaService;

	@GetMapping("/obterPautas")
	public List<Pauta> obterPautas() {
		return this.pautaService.obterPautas();
	}
	
	@PostMapping("/cadastrarPauta")
	@ResponseBody
	public Pauta cadastrarPauta(@RequestParam String description)
	{
		return this.pautaService.cadastrarPauta(description);		
	}
	
	@PostMapping("/abrirSessaoVotacao/{pautaId}")	
	public Pauta abrirSessaoVotacao(@PathVariable int pautaId, @RequestParam(value = "timeoutInMinutes", defaultValue = "1") int timeoutInMinutes) throws PautaNotFoundException, SessaoVotacaoAlreadyOpenException
	{		
		return this.pautaService.abrirSessaoVotacao(pautaId, timeoutInMinutes);		
	}
	
	@GetMapping("/obterResultadoPauta/{pautaId}")
	public Pauta obterResultadoPauta(@PathVariable int pautaId) {		
		return this.pautaService.obterResultadoPauta(pautaId);				
	}
	
}
