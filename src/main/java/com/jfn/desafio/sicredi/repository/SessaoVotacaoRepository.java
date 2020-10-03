package com.jfn.desafio.sicredi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfn.desafio.sicredi.entity.SessaoVotacao;

@Repository
public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Integer>{
	SessaoVotacao findByPautaId(int pautaId);
}
