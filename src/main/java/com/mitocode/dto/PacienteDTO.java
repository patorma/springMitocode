package com.mitocode.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Paciente DTO Data")
public class PacienteDTO {
	
	private Integer idPaciente;
	
	@Schema(description = "Nombres del Paciente")
	@NotNull
	@Size(min =3,message = "{nombres.size}")
	private String nombres;
	
	@Schema(description = "Apellidoss del Paciente")
	@NotNull
	@Size(min =3,message = "{apellidos.size}")
	private String apellidos;
	
	@NotNull
	@Size(min =9)
	private String rut;
	
	@NotNull
	@Size(min =3,max =150)
	private String direccion;
	
	@NotEmpty
	@Email
	private String email;
	
	@Size(min =9, max =9)
	//@Pattern(regexp = "")
	private String telefono;
	

}
