package com.jfn.desafio.sicredi.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="sessao_votacao")
public class SessaoVotacao {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private Date startDate;
	private Date endDate;
		
	@OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "pauta_id", referencedColumnName = "id")	
	private Pauta pauta;
	
	@OneToMany( fetch = FetchType.LAZY, targetEntity = Voto.class)
	private List<Voto> votos;
}
