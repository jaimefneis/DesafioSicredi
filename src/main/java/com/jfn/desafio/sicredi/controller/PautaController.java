package com.jfn.desafio.sicredi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jfn.desafio.sicredi.entity.Pauta;
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
	public Pauta cadastrarPauta(@RequestBody Pauta pauta)
	{
		return this.pautaService.cadastrarPauta(pauta);		
	}
	
}
