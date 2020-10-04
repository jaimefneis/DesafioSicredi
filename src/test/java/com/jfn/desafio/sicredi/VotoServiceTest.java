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
import com.jfn.desafio.sicredi.exception.CpfDuplicatedForVotingException;
import com.jfn.desafio.sicredi.exception.SessaoVotacaoNotAvailableForVotingException;
import com.jfn.desafio.sicredi.repository.PautaRepository;
import com.jfn.desafio.sicredi.repository.VotoRepository;
import com.jfn.desafio.sicredi.service.PautaService;
import com.jfn.desafio.sicredi.service.VotoService;

@SpringBootTest
public class VotoServiceTest {

	@Autowired
	private VotoService votoService;
	
	@MockBean
	private PautaRepository pautaRepository;
	private VotoRepository votoRepository;

	@Test
	public void votarWhenPautaCadastradaEAbertaComVotoSimThenRetornaVotoCadastrado() {
				
	}
	
	@Test
	public void votarWhenPautaCadastradaEAbertaComVotoNaoThenRetornaVotoCadastrado() {
				
	}
	
	@Test
	public void votarWhenPautaNaoCadastradaThenPautaNotFoundException() {
				
	}
	
	@Test
	public void votarWhenPautaCadastradaENaoAbertaThenSessaoVotacaoNotAvailableForVotingException() {
				
	}
	
	@Test
	public void votarWhenPautaCadastradaJaEncerradaThenSessaoVotacaoNotAvailableForVotingException() {
				
	}
	
	@Test
	public void votarWhenPautaCadastradaEAbertaComCpfInvalidoThenCpfInvalidException() {
				
	}
	
	@Test
	public void votarWhenPautaCadastradaEAbertaComCpfJaVotadoThenCpfDuplicatedForVotingException() {
				
	}

}
