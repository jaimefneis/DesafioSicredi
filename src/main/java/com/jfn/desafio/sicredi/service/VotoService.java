package com.jfn.desafio.sicredi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfn.desafio.sicredi.entity.Pauta;
import com.jfn.desafio.sicredi.entity.Voto;
import com.jfn.desafio.sicredi.exception.DuplicatedCpfForVotingException;
import com.jfn.desafio.sicredi.exception.InvalidCpfException;
import com.jfn.desafio.sicredi.exception.PautaNotFoundException;
import com.jfn.desafio.sicredi.exception.SessaoVotacaoNotAvailableForVotingException;
import com.jfn.desafio.sicredi.exception.SessaoVotacaoNotOpenException;
import com.jfn.desafio.sicredi.repository.PautaRepository;
import com.jfn.desafio.sicredi.repository.VotoRepository;

@Service
public class VotoService {

	@Autowired
	private VotoRepository votoRepository;

	@Autowired
	private PautaRepository pautaRepository;

	public Voto votar(int pautaId, String cpf, boolean agree) {

		Pauta pauta = this.pautaRepository.findById(pautaId).orElseThrow(() -> new PautaNotFoundException(pautaId));

		if (!this.isSessaoVotacaoOpen(pauta)) {
			throw new SessaoVotacaoNotAvailableForVotingException(pautaId);
		}

		if (this.isSessaoVotacaoClosed(pauta)) {
			throw new SessaoVotacaoNotOpenException(pautaId);
		}

		if (!this.isCpfValid(cpf)) {
			throw new InvalidCpfException(cpf);
		}

		if (this.isCpfDuplicatedForVoting(pautaId, cpf)) {
			throw new DuplicatedCpfForVotingException(pautaId, cpf);
		}

		this.computarVoto(pauta, agree);

		Voto voto = new Voto();
		voto.setPauta(pauta);
		voto.setCpf(cpf);
		voto.setAgree(agree);

		return this.votoRepository.save(voto);
	}

	private void computarVoto(Pauta pauta, boolean agree) {
		synchronized (this) {
			if (agree)
			{
				pauta.setTotalYes(pauta.getTotalYes() + 1);
			} else {
				pauta.setTotalNo(pauta.getTotalNo() + 1);
			}
			this.pautaRepository.save(pauta);
		}
	}

	private boolean isSessaoVotacaoOpen(Pauta pauta) {
		return pauta.getStartDate() != null;
	}

	private boolean isSessaoVotacaoClosed(Pauta pauta) {
		Date now = new Date();

		return !(now.after(pauta.getStartDate()) && now.before(pauta.getEndDate()));
	}

	private boolean isCpfValid(String cpf) {
		return true;
	}

	private boolean isCpfDuplicatedForVoting(int pautaId, String cpf) {
		List<Voto> votos = this.votoRepository.findByPautaIdAndCpf(pautaId, cpf);
		return votos != null && votos.size() > 0;
	}

}
