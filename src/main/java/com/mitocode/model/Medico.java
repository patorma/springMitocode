package com.mitocode.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Medico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idMedico;
	
	@Column(nullable = false,length = 70)
	private String nombres;
	
	@Column(nullable = false,length = 70)
	private String apellidos;
	
	@Column(nullable = false,length = 12, unique = true)
	private String cmp;// código unico para el medico
	
	@Column(nullable=true)
	private String fotoUrl;
}
