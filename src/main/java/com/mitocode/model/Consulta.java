package com.mitocode.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
public class Consulta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idConsulta;
	
	//JPQL = Java Persistence Query Language
	//es una forma de expresar nuestras consultas 
	//pero con el formato orientado a objetos
	//SELECT * FROM Consulta c WHERE c.paciente.rut = ?
	//las clases acceden a tablas
	//En este caso @ManyToOne significa para muchas consultas le 
	//voy asignar un paciente 
	
	@ManyToOne
	@JoinColumn(name = "id_paciente",nullable = false,foreignKey = @ForeignKey(name="FK_consulta_paciente"))
	private Paciente paciente;
	

	@ManyToOne
	@JoinColumn(name = "id_medico",nullable = false,foreignKey = @ForeignKey(name="FK_consulta_medico"))
	private Medico medico;
	

	@ManyToOne
	@JoinColumn(name = "id_especialidad",nullable = false,foreignKey = @ForeignKey(name="FK_consulta_especialidad"))
	private Especialidad especialidad;
	
	@Column(length = 3)
	private String numConsultorio;
	
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy") lo recibe el request
	
	@Column(nullable = false)  //ISO Date yyyy-mm-ddTHH:mm:ss
	private LocalDateTime fecha;
	
	// se asocia al atributo consulta de la tabla detalleconsulta
	@OneToMany(mappedBy = "consulta",cascade = {CascadeType.ALL},orphanRemoval = true)
	private List<DetalleConsulta> detalleConsulta;
	// si se quiere eliminar solo un elemento del detalle de un OneToMany activar el orphanRemoval
}
