package com.jfn.desafio.sicredi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.jfn.desafio.sicredi.entity.Pauta;
import com.jfn.desafio.sicredi.exception.PautaNotFoundException;
import com.jfn.desafio.sicredi.exception.SessaoVotacaoAlreadyOpenException;
import com.jfn.desafio.sicredi.exception.SessaoVotacaoNotOpenException;
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
		// Arrange
		List<Pauta> pautas = Collections.emptyList();
		when(pautaRepository.findAll()).thenReturn(pautas);
		
		// Act
		List<Pauta> result = pautaService.obterPautas();
		
		// Assert
		assertEquals(pautas.size(), result.size());		
	}
	
	@Test
	public void obterPautasWhenPautasCadastradasThenRetornaTodasPautas() {
		// Arrange
		List<Pauta> pautas = new ArrayList<>();
		pautas.add(new Pauta(1, "Description 1", null, null, 0, 0));
		pautas.add(new Pauta(2, "Description 2", new Date(), new Date(), 10, 3));
		when(pautaRepository.findAll()).thenReturn(pautas);
		
		// Act
		List<Pauta> result = pautaService.obterPautas();
		
		// Assert
		assertEquals(pautas.size(), result.size());
		
		assertEquals(pautas.get(0).getId(), result.get(0).getId());
		assertEquals(pautas.get(0).getDescription(), result.get(0).getDescription());
		assertEquals(pautas.get(0).getStartDate(), result.get(0).getStartDate());
		assertEquals(pautas.get(0).getEndDate(), result.get(0).getEndDate());
		assertEquals(pautas.get(0).getTotalYes(), result.get(0).getTotalYes());
		assertEquals(pautas.get(0).getTotalNo(), result.get(0).getTotalNo());
		
		assertEquals(pautas.get(1).getId(), result.get(1).getId());
		assertEquals(pautas.get(1).getDescription(), result.get(1).getDescription());
		assertEquals(pautas.get(1).getStartDate(), result.get(1).getStartDate());
		assertEquals(pautas.get(1).getEndDate(), result.get(1).getEndDate());
		assertEquals(pautas.get(1).getTotalYes(), result.get(1).getTotalYes());
		assertEquals(pautas.get(1).getTotalNo(), result.get(1).getTotalNo());
	}
	
	@Test
	public void cadastrarPautaWhenDescricaoValidaThenRetornaPautaCadastrada() {
		
		// Arrange
		String description = "Descrição pauta";
		when(pautaRepository.save(Mockito.any(Pauta.class))).thenAnswer(i -> i.getArguments()[0]);
		
		// Act
		Pauta result = pautaService.cadastrarPauta(description);
				
		// Assert
		assertEquals(description, result.getDescription());		
		assertEquals(null, result.getStartDate());
		assertEquals(null, result.getEndDate());
		assertEquals(0, result.getTotalYes());
		assertEquals(0, result.getTotalNo());
	}
	
	@Test
	public void abrirSessaoVotacaoWhenPautaCadastradaENaoAbertaThenAbrirPautaComTempoDefinido()
	{
		// Arrange
		int timeout = 5;
		Pauta pauta = new Pauta(1, "description", null, null, 0, 0);
		when(pautaRepository.findById(pauta.getId())).thenReturn(Optional.of(pauta));
		when(pautaRepository.save(Mockito.any(Pauta.class))).thenAnswer(i -> i.getArguments()[0]);
		
		// Act
		Pauta result = pautaService.abrirSessaoVotacao(pauta.getId(), timeout);
		
		// Assert
		assertNotNull(result.getStartDate());
		assertNotNull(result.getEndDate());
		assertEquals(0, result.getTotalYes());
		assertEquals(0, result.getTotalNo());
		assertEquals((result.getEndDate().getTime() - result.getStartDate().getTime()) / (60 * 1000), timeout);
	}
	
	@Test
	public void abrirSessaoVotacaoWhenPautaNaoCadastradaThenPautaNotFoundException()
	{
		// Arrange			
		when(pautaRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.empty());
		
		
		// Act and Assert
		assertThrows(PautaNotFoundException.class, () -> pautaService.abrirSessaoVotacao(1, 2));			
	}
	
	@Test
	public void abrirSessaoVotacaoWhenPautaCadastradaEAbertaThenSessaoVotacaoAlreadyOpenException()
	{		
		Pauta pauta = new Pauta(1, "description", new Date(), new Date(), 0, 0);
		when(pautaRepository.findById(pauta.getId())).thenReturn(Optional.of(pauta));
		
		// Act and Assert
		assertThrows(SessaoVotacaoAlreadyOpenException.class, () -> pautaService.abrirSessaoVotacao(1, 2));
	}
	
	@Test
	public void obterResultadoPautaWhenPautaNaoCadastradaThenPautaNotFoundException()
	{
		when(pautaRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.empty());
		
		// Act and Assert
		assertThrows(PautaNotFoundException.class, () -> pautaService.obterResultadoPauta(1));
	}
	
	@Test
	public void obterResultadoPautaWhenPautaCadastradaENaoAbertaThenSessaoVotacaoNotOpenException()
	{
		// Arrange
		Pauta pauta = new Pauta(1, "description", null, null, 0, 0);
		when(pautaRepository.findById(pauta.getId())).thenReturn(Optional.of(pauta));
		
		// Act and Assert
		assertThrows(SessaoVotacaoNotOpenException.class, () -> pautaService.obterResultadoPauta(pauta.getId()));
	}
	
	@Test
	public void obterResultadoPautaWhenPautaCadastradaEAbertaThenRetornaPauta()
	{
		// Arrange
		Pauta pauta = new Pauta(1, "description", new Date(), new Date(), 15, 35);
		when(pautaRepository.findById(pauta.getId())).thenReturn(Optional.of(pauta));
		
		// Act
		Pauta result = pautaService.obterResultadoPauta(pauta.getId());
		
		// Assert
		assertEquals(pauta.getTotalYes(), result.getTotalYes());
		assertEquals(pauta.getTotalNo(), result.getTotalNo());
	}

}
