package com.mitocode.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;

@Entity
@IdClass(ConsultaExamenPk.class)
@Data
public class ConsultaExamen {
	//llave primaria compuesta 
	
	@Id
	private Consulta consulta;
	
	@Id
	private Examen examen;
	
	

}
