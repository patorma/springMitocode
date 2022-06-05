package com.mitocode.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;

@Entity
@Data
public class Usuario {
    
	@Id
	private Integer idUsuario;
	
	@Column(name = "nombre" , nullable= false, unique = true)
	private String username;
	
	@Column(name = "clave" , nullable= false)
	private String password;
	
	@Column(name = "estado" , nullable= false)
	private boolean enabled; //usuario habilitado o no para iniciar sesion
	
	//referencedColumnName = "idUsuario" hace referencia al atributo e esta clase usuario idUsuario
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_rol",
			joinColumns = @JoinColumn(name="id_usuario",referencedColumnName = "idUsuario"),
			inverseJoinColumns = @JoinColumn(name = "id_rol",referencedColumnName = "idRol"))
	private List<Rol> roles;
}
