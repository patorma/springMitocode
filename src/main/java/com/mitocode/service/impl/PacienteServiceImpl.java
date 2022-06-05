package com.mitocode.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitocode.model.Paciente;
import com.mitocode.repo.IPacienteRepo;
import com.mitocode.service.IPacienteService;

@Service
public class PacienteServiceImpl implements IPacienteService{
	
	@Autowired
	private IPacienteRepo repo;//instancia que gestiona sping para logica de acceso de datos

	@Override
	public Paciente registrar(Paciente paciente) throws Exception {
	
		return repo.save(paciente);
	}

	@Override
	public Paciente modificar(Paciente paciente) throws Exception {
		return repo.save(paciente);
	}

	@Override
	public List<Paciente> listar() throws Exception {
		return repo.findAll();
	}

	@Override
	public Paciente listarPorId(Integer id) throws Exception {
		//Optional<Paciente> op = repo.findById(id);
		//return op.isPresent() ? op.get() : new Paciente();
		return repo.findById(id).orElse(null);
	}

	@Override
	public void eliminar(Integer id) throws Exception {
	
		repo.deleteById(id);
		
	}

}
