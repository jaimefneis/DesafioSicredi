package com.jfn.desafio.sicredi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.lang.model.element.UnknownAnnotationValueException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.jfn.desafio.sicredi.entity.Pauta;
import com.jfn.desafio.sicredi.entity.Voto;
import com.jfn.desafio.sicredi.exception.DuplicatedCpfForVotingException;
import com.jfn.desafio.sicredi.exception.PautaNotFoundException;
import com.jfn.desafio.sicredi.exception.SessaoVotacaoNotAvailableForVotingException;
import com.jfn.desafio.sicredi.exception.SessaoVotacaoNotOpenException;
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

	@MockBean
	private VotoRepository votoRepository;

	@Test
	public void votarWhenPautaCadastradaEAbertaComVotoSimThenRetornaVotoCadastrado() {
		// Arrange
		Date now = new Date();
		int votoYes = 15;
		Pauta pauta = new Pauta(1, "description", now, new Date(now.getTime() + 10 * (60 * 1000)), votoYes, 0);
		when(pautaRepository.findById(pauta.getId())).thenReturn(Optional.of(pauta));

		List<Voto> votos = Collections.emptyList();
		when(votoRepository.findByPautaIdAndCpf(Mockito.any(Integer.class), Mockito.any(String.class)))
				.thenReturn(votos);

		when(votoRepository.save(Mockito.any(Voto.class))).thenAnswer(i -> i.getArguments()[0]);

		// Act
		Voto result = votoService.votar(pauta.getId(), "000.000.000-00", true);

		// Assert
		assertEquals(votoYes + 1, result.getPauta().getTotalYes());
		assertEquals(0, result.getPauta().getTotalNo());
	}

	@Test
	public void votarWhenPautaCadastradaEAbertaComVotoNaoThenRetornaVotoCadastrado() {
		// Arrange
		Date now = new Date();
		int votoNo = 6;
		Pauta pauta = new Pauta(1, "description", now, new Date(now.getTime() + 10 * (60 * 1000)), 0, votoNo);
		when(pautaRepository.findById(pauta.getId())).thenReturn(Optional.of(pauta));

		List<Voto> votos = Collections.emptyList();
		when(votoRepository.findByPautaIdAndCpf(Mockito.any(Integer.class), Mockito.any(String.class)))
				.thenReturn(votos);

		when(votoRepository.save(Mockito.any(Voto.class))).thenAnswer(i -> i.getArguments()[0]);

		// Act
		Voto result = votoService.votar(pauta.getId(), "000.000.000-00", false);

		// Assert
		assertEquals(0, result.getPauta().getTotalYes());
		assertEquals(votoNo + 1, result.getPauta().getTotalNo());
	}

	@Test
	public void votarWhenPautaNaoCadastradaThenPautaNotFoundException() {
		// Arrange
		when(pautaRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.empty());

		// Act and Assert
		assertThrows(PautaNotFoundException.class, () -> votoService.votar(1, "000.000.000-00", false));
	}

	@Test
	public void votarWhenPautaCadastradaENaoAbertaThenSessaoVotacaoNotAvailableForVotingException() {
		// Arrange
		Date now = new Date();
		Pauta pauta = new Pauta(1, "description", null, null, 0, 0);
		when(pautaRepository.findById(pauta.getId())).thenReturn(Optional.of(pauta));

		// Act and Assert
		assertThrows(SessaoVotacaoNotAvailableForVotingException.class,
				() -> votoService.votar(pauta.getId(), "000.000.000-00", true));
	}

	@Test
	public void votarWhenPautaCadastradaJaEncerradaThenSessaoVotacaoNotOpenException() {
		// Arrange
		Date now = new Date();
		Pauta pauta = new Pauta(1, "description", now, now, 100, 50);
		when(pautaRepository.findById(pauta.getId())).thenReturn(Optional.of(pauta));

		// Act and Assert
		assertThrows(SessaoVotacaoNotOpenException.class,
				() -> votoService.votar(pauta.getId(), "000.000.000-00", true));
	}

	@Test
	public void votarWhenPautaCadastradaEAbertaComCpfInvalidoThenCpfInvalidException() {
		// TODO Por enquanto não está validando CPF		
	}

	@Test
	public void votarWhenPautaCadastradaEAbertaComCpfJaVotadoThenDuplicatedCpfForVotingException() {
		// Arrange
		Date now = new Date();
		String cpf = "000.000.000-00";
		Pauta pauta = new Pauta(1, "description", now, new Date(now.getTime() + 10 * (60 * 1000)), 0, 0);
		when(pautaRepository.findById(pauta.getId())).thenReturn(Optional.of(pauta));

		List<Voto> votos = new ArrayList<>();
		votos.add(new Voto(2, cpf, true, pauta));

		when(votoRepository.findByPautaIdAndCpf(Mockito.any(Integer.class), Mockito.any(String.class)))
				.thenReturn(votos);

		// Act and Assert
		assertThrows(DuplicatedCpfForVotingException.class,
				() -> votoService.votar(pauta.getId(), cpf, true));
	}

}
