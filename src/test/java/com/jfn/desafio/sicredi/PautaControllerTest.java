package com.jfn.desafio.sicredi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jfn.desafio.sicredi.entity.Pauta;
import com.jfn.desafio.sicredi.repository.PautaRepository;
import com.jfn.desafio.sicredi.repository.VotoRepository;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PautaControllerTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@MockBean
	private PautaRepository pautaRepository;
	
	 
	
	@Test
	public void obterPautasWhenPautasIsEmptyThenReturnEmptyList()
	{
		List<Pauta> emptyPautas = Collections.emptyList();
		BDDMockito.when(pautaRepository.findAll()).thenReturn(emptyPautas);
		
		ResponseEntity<String> response = restTemplate.getForEntity("/obterPautas", String.class);
		
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}
	
	
}
