package com.mitocode.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "rol")
public class Rol {

	@Id
	private Integer idRol;
	
	@Column(nullable = false, length = 15)
	private String nombre;
	
	@Column(nullable = true, length = 150)
	private String descripcion; 
}
