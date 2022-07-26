package com.mitocode.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mitocode.dto.PacienteDTO;
import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Paciente;
import com.mitocode.service.IPacienteService;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
@RestController
@RequestMapping("/pacientes")
public class PacienteController {
	
	@Autowired
	private IPacienteService service;
	
	//con autowired spring debe tener un bean para usar este mapper
    @Autowired
    private ModelMapper mapper;
	
	@GetMapping
	public ResponseEntity<List<PacienteDTO>> listar() throws Exception{
		//hay que ver como de eta lista paciente termino llegando
		//a paciente DTO con mapper (ver declaracion clase MapperConfig)
		//con stream() se transforma a a PacienteDTO
		List<PacienteDTO> lista = service.listar().stream().map(p->mapper.map(p,PacienteDTO.class)).collect(Collectors.toList());
		return new ResponseEntity<>(lista,HttpStatus.OK);
	}
	//lo que se captura en la url ({id} se almacena en Integer id)
	@GetMapping("/{id}")
	public ResponseEntity<PacienteDTO> listarPorId(@PathVariable("id") Integer id) throws Exception{
		Paciente obj =  service.listarPorId(id);
		
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " +id);
		}
		//debo tratar de obtener un pacientedto
		PacienteDTO dto = mapper.map(obj, PacienteDTO.class);//source y fuente de destino
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}
	
	/*@PostMapping
	public ResponseEntity<Paciente> registrar(@RequestBody Paciente paciente) throws Exception{
		Paciente obj = service.registrar(paciente);
		return new ResponseEntity<>(obj,HttpStatus.CREATED);
	}*/
	@PostMapping
	public ResponseEntity<Void> registrar(@Valid @RequestBody PacienteDTO dtoRequest) throws Exception{
		Paciente pac = mapper.map(dtoRequest,Paciente.class);
		Paciente obj = service.registrar(pac);
		//generar infomaci�n de como acceder al recurso
		//localhost:8080/paciennte/5
		//localhost:8080/paciente
		//{ServletUriComponentsBuilder.fromCurrentRequest()}
		//luego se concatena a una parte dinamica
		//{path("/{id}")}
		//y luego le pasamos el contenido de esa parte dinamica
		//{que es el id del objeto que acabo de insertar}
		//se extrae texto para formar el string de la ruta
		
		//primera linea extrae peticion actual
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdPaciente()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<Paciente> modificar(@Valid @RequestBody PacienteDTO dtoRequest) throws Exception{
		Paciente obj = service.listarPorId(dtoRequest.getIdPaciente());
		
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENONTRADO " + dtoRequest.getIdPaciente());
		}
		
		Paciente pac = mapper.map(dtoRequest,Paciente.class);
		Paciente paciente = service.modificar(pac);
		return new ResponseEntity<>(paciente,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception{
		Paciente obj = service.listarPorId(id);
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENONTRADO " + id);
		}
		
		service.eliminar(id);
		 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	//objetivo listar un paciente por su id
	@GetMapping("/hateoas/{id}")
	public EntityModel<PacienteDTO> listarHateoas(@PathVariable("id") Integer id) throws Exception{
		Paciente obj = service.listarPorId(id);
		
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENONTRADO " + id);
		}
		
		PacienteDTO dto = mapper.map(obj, PacienteDTO.class);
		EntityModel<PacienteDTO> recurso = EntityModel.of(dto);
		//genera un link,un atributo llamado link que da la informaci�n de lo que quiero mostrar
		//es decir quiero generar la url de alg�n recurso 
		//cual es el texto de esta url -->linkTo(methodOn(this.getClass()) getClass es PaciienteController
		// generamos un localhost:8080/pacientes/{id} el id es dinamico
		//se apunta a un controlador y los metodos que tenga
		WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).listarPorId(id));
		WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).listarPorId(id));
		
		recurso.add(link1.withRel("paciente-info1"));
		//recurso.add(link2.withRel("paciente-info2"));
		
		return recurso;
	}
}
