package com.mitocode.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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

import com.mitocode.dto.ConsultaDTO;
import com.mitocode.dto.ConsultaListaExamenDTO;
import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Consulta;
import com.mitocode.model.Examen;
import com.mitocode.service.IConsultaService;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
@RestController
@RequestMapping("/consultas")
public class ConsultaController {
	
	@Autowired
	private IConsultaService service;
	
	//con autowired spring debe tener un bean para usar este mapper
    @Autowired
    private ModelMapper mapper;
	
	@GetMapping
	public ResponseEntity<List<ConsultaDTO>> listar() throws Exception{
		//hay que ver como de eta lista consulta termino llegando
		//a consulta DTO con mapper (ver declaracion clase MapperConfig)
		//con stream() se transforma a a ConsultaDTO
		List<ConsultaDTO> lista = service.listar().stream().map(p->mapper.map(p,ConsultaDTO.class)).collect(Collectors.toList());
		return new ResponseEntity<>(lista,HttpStatus.OK);
	}
	//lo que se captura en la url ({id} se almacena en Integer id)
	@GetMapping("/{id}")
	public ResponseEntity<ConsultaDTO> listarPorId(@PathVariable("id") Integer id) throws Exception{
		Consulta obj =  service.listarPorId(id);
		
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " +id);
		}
		//debo tratar de obtener un consultadto
		ConsultaDTO dto = mapper.map(obj, ConsultaDTO.class);//source y fuente de destino
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}
	
	/*@PostMapping
	public ResponseEntity<Consulta> registrar(@RequestBody Consulta consulta) throws Exception{
		Consulta obj = service.registrar(consulta);
		return new ResponseEntity<>(obj,HttpStatus.CREATED);
	}*/
	/*@PostMapping
	public ResponseEntity<Void> registrar(@Valid @RequestBody ConsultaDTO dtoRequest) throws Exception{
		Consulta pac = mapper.map(dtoRequest,Consulta.class);
		Consulta obj = service.registrar(pac);
		//generar infomaci�n de como acceder al recurso
		//localhost:8080/paciennte/5
		//localhost:8080/consulta
		//{ServletUriComponentsBuilder.fromCurrentRequest()}
		//luego se concatena a una parte dinamica
		//{path("/{id}")}
		//y luego le pasamos el contenido de esa parte dinamica
		//{que es el id del objeto que acabo de insertar}
		//se extrae texto para formar el string de la ruta
		
		//primera linea extrae peticion actual
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdConsulta()).toUri();
		return ResponseEntity.created(location).build();
	}*/
	
	@PostMapping
	public ResponseEntity<Void> registrar(@Valid @RequestBody ConsultaListaExamenDTO dto) throws Exception{
		//con mapper extraemos del dto
		Consulta c = mapper.map(dto.getConsulta(), Consulta.class);
		//se engloba la lista de examenes a utilizar new TypeToken<List<Examen>> clase que devuelve lista de algo
		List<Examen> examenes = mapper.map(dto.getLstExamen(), new TypeToken<List<Examen>>() {}.getType());
		//El método registrarTransaccional recibe dos parametros: la consulta y la lista de examenes
		
		Consulta obj = service.registrarTransaccional(c,examenes);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdConsulta()).toUri();
		return ResponseEntity.created(location).build();
		
	}
	
	
	@PutMapping
	public ResponseEntity<Consulta> modificar(@Valid @RequestBody ConsultaDTO dtoRequest) throws Exception{
		Consulta obj = service.listarPorId(dtoRequest.getIdConsulta());
		
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENONTRADO " + dtoRequest.getIdConsulta());
		}
		
		Consulta pac = mapper.map(dtoRequest,Consulta.class);
		Consulta consulta = service.modificar(pac);
		return new ResponseEntity<>(consulta,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception{
		Consulta obj = service.listarPorId(id);
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENONTRADO " + id);
		}
		
		service.eliminar(id);
		 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	//objetivo listar un consulta por su id
	@GetMapping("/hateoas/{id}")
	public EntityModel<ConsultaDTO> listarHateoas(@PathVariable("id") Integer id) throws Exception{
		Consulta obj = service.listarPorId(id);
		
		if(obj == null) {
			throw new ModeloNotFoundException("ID NO ENONTRADO " + id);
		}
		
		ConsultaDTO dto = mapper.map(obj, ConsultaDTO.class);
		EntityModel<ConsultaDTO> recurso = EntityModel.of(dto);
		//genera un link,un atributo llamado link que da la informaci�n de lo que quiero mostrar
		//es decir quiero generar la url de alg�n recurso 
		//cual es el texto de esta url -->linkTo(methodOn(this.getClass()) getClass es PaciienteController
		// generamos un localhost:8080/consultas/{id} el id es dinamico
		//se apunta a un controlador y los metodos que tenga
		WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).listarPorId(id));
		WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).listarPorId(id));
		
		recurso.add(link1.withRel("consulta-info1"));
		//recurso.add(link2.withRel("consulta-info2"));
		
		return recurso;
	}
}
