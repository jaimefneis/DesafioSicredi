package com.jfn.desafio.sicredi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfn.desafio.sicredi.entity.Voto;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Integer> {
	//List<Voto> findBySessaoVotacaoIdAndCpf(int sessaoVotacaoId, String cpf);
}
