package com.mitocode.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table
public class DetalleConsulta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idDetalle;
	
	@ManyToOne
	@JoinColumn(name="id_consulta",nullable = false, foreignKey = @ForeignKey(name="FK_consulta_detalle"))
	private Consulta consulta;
	
	@Column(nullable=false,length = 70)
	private String diagnostico;
	
	@Column(nullable = false,length = 300)
	private String tratamiento;
}
