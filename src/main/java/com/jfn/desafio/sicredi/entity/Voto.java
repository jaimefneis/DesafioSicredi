package com.jfn.desafio.sicredi.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
public class Voto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
		
	private String cpf;	
	private boolean agree;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Pauta pauta;
}
