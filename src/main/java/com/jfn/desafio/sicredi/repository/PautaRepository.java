package com.jfn.desafio.sicredi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jfn.desafio.sicredi.entity.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Integer> {

}
