package com.mitocode.service;

import java.util.List;

import com.mitocode.model.Paciente;

public interface IPacienteService {

	Paciente registrar(Paciente paciente)throws Exception;
	Paciente modificar(Paciente paciente) throws Exception;
	List<Paciente> listar() throws Exception;
	Paciente listarPorId(Integer id) throws Exception;
	void eliminar(Integer id) throws Exception;
}
