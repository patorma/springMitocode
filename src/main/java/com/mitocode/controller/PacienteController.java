package com.mitocode.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.model.Paciente;
import com.mitocode.service.IPacienteService;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
	
	@Autowired
	private IPacienteService service;
	
	@GetMapping
	public ResponseEntity<List<Paciente>> listar() throws Exception{
		List<Paciente> lista = service.listar();
		return new ResponseEntity<>(lista,HttpStatus.OK);
	}
	//lo que se captura en la url ({id} se almacena en Integer id)
	@GetMapping("/{id}")
	public ResponseEntity<Paciente> listarPorId(@PathVariable("id") Integer id) throws Exception{
		Paciente obj =  service.listarPorId(id);
		return new ResponseEntity<>(obj,HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Paciente> registrar(@RequestBody Paciente paciente) throws Exception{
		Paciente obj = service.registrar(paciente);
		return new ResponseEntity<>(obj,HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Paciente> modificar(@RequestBody Paciente paciente) throws Exception{
		Paciente obj = service.modificar(paciente);
		return new ResponseEntity<>(obj,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception{
		 service.eliminar(id);
		 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
