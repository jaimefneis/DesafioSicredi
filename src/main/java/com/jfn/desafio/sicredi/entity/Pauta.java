package com.jfn.desafio.sicredi.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="pauta")
public class Pauta {
	
	@Id
	@GeneratedValue
	private int id;
		
	private String description;	
}
