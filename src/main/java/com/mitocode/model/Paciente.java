package com.mitocode.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
//@Table(name="tbl_paciente")
public class Paciente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idPaciente;
	
	@Column(name = "nombres",nullable=false,length = 70)
	private String nombres;
	
	@Column(nullable=false,length = 70)
	private String apellidos;
	
	@Column(nullable=false,length = 10)
	private String rut;
	
	@Column(nullable=true,length = 150)
	private String direccion;
	
	@Column(nullable=false,length = 9)
	private String telefono;
	
	@Column(nullable=false,length = 90)
	private String email;
}
