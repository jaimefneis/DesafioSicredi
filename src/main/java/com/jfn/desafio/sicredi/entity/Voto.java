package com.jfn.desafio.sicredi.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(name="voto")//, uniqueConstraints = {@UniqueConstraint(columnNames= {"sessao_votacao_id", "cpf"})})
public class Voto {
	
	@Id
	@GeneratedValue
	private int id;
		
	private String cpf;	
	private boolean agree;
	
	@ManyToOne
	private SessaoVotacao sessaoVotacao;
}
