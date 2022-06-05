package com.mitocode.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "examen")
@EqualsAndHashCode
public class Examen {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idExamen;
	
	@Column(name = "nombre",nullable= false, length = 50)
	private String nombre;
	
	@Column(name = "descripcion",nullable = true, length = 150)
	private String descripcion;
}
