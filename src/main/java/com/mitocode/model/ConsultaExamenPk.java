package com.mitocode.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class ConsultaExamenPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@ManyToOne
	@JoinColumn(name ="id_consulta", nullable = false)
	private Consulta consulta;
	

	@ManyToOne
	@JoinColumn(name ="id_examen", nullable = false)
	private Examen examen;
}
