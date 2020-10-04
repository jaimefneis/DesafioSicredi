package com.jfn.desafio.sicredi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.jfn.desafio.sicredi.entity.Pauta;
import com.jfn.desafio.sicredi.repository.PautaRepository;
import com.jfn.desafio.sicredi.service.PautaService;

@SpringBootTest
public class PautaServiceTest {
	@Autowired
	private PautaService pautaService;
	
	@MockBean
	private PautaRepository pautaRepository;

	@Test
	public void obterPautasWhenNenhumaPautaCadastradaThenRetornaListaVazia() {
		
		List<Pauta> pautas = Collections.emptyList();
		when(pautaRepository.findAll()).thenReturn(pautas);
		
		List<Pauta> result = pautaService.obterPautas();
		
		assertEquals(pautas.size(), result.size());		
	}
	
	@Test
	public void obterPautasWhenPautasCadastradasThenRetornaTodasPautas() {
		
		List<Pauta> pautas = Collections.emptyList();
		when(pautaRepository.findAll()).thenReturn(pautas);
		
		List<Pauta> result = pautaService.obterPautas();
		
		assertEquals(pautas.size(), result.size());		
	}
	
	@Test
	public void cadastrarPautaWhenDescricaoValidaThenRetornaPautaCadastrada() {
		
		String description = "Descrição pauta";
		Pauta pauta = new Pauta();
		
		//when(pautaRepository.save(entity)).thenReturn(pautas);
		
		//List<Pauta> result = pautaService.obterPautas();
		
		//assertEquals(pautas.size(), result.size());		
	}
	
	@Test
	public void abrirSessaoVotacaoWhenPautaCadastradaENaoAbertaThenAbrirPauta()
	{
		
	}
	
	@Test
	public void abrirSessaoVotacaoWhenPautaNaoCadastradaThenPautaNotFoundException()
	{
		
	}
	
	@Test
	public void abrirSessaoVotacaoWhenPautaCadastradaEAbertaThenSessaoVotacaoAlreadyOpenException()
	{
		
	}
	
	@Test
	public void obterResultadoPautaWhenPautaNaoCadastradaThenPautaNotFoundException()
	{
		
	}
	
	@Test
	public void obterResultadoPautaWhenPautaCadastradaENaoAbertaThenSessaoVotacaoNotOpenException()
	{
		
	}
	
	@Test
	public void obterResultadoPautaWhenPautaCadastradaEAbertaThenRetornaPauta()
	{
		
	}

}
