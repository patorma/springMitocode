package com.mitocode.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public final  ResponseEntity<ExceptionResponse> manejarTodasExcepciones(Exception ex,WebRequest request){
		ExceptionResponse er = new ExceptionResponse(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<>(er,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	// se intercepta cuando exista una excepcion de tipo ModeloNotFoundException
	//tener la excepcion como parametro y tener el request de esa excepcion generada
	//en producción no es conveniente poner ex.getMessage()
	@ExceptionHandler(ModeloNotFoundException.class)
	public ResponseEntity<ExceptionResponse> manejarModeloNotFoundException(ModeloNotFoundException ex, WebRequest request){
		ExceptionResponse er = new ExceptionResponse(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<>(er,HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		// en una sola linea de codigo es recorrer todos los errores concatenandoles una coma
		//uniendo todos esos recorridos y poniendo una sola variable mensaje
		String mensaje = ex.getBindingResult().getAllErrors().stream().map(e ->e.getDefaultMessage().concat(",")).collect(Collectors.joining());
		
		ExceptionResponse er = new ExceptionResponse(LocalDateTime.now(),mensaje,request.getDescription(false));
		return new ResponseEntity<>(er,HttpStatus.BAD_REQUEST);
	}
	
	
}
