package com.jfn.desafio.sicredi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jfn.desafio.sicredi.entity.Voto;
import com.jfn.desafio.sicredi.service.VotoService;

@RestController
public class VotoController {
	
	@Autowired
	private VotoService votoService;
	
	@PostMapping("/votar/{sessaoVotacaoId}")
	public Voto votar(@PathVariable int sessaoVotacaoId, @RequestParam String cpf, boolean agree) {	
		return this.votoService.votar(sessaoVotacaoId, cpf, agree);		
	}

}
