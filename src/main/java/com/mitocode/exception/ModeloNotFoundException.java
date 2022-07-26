package com.mitocode.exception;



//se ejecuta en tiempo de ejecucion

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class ModeloNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ModeloNotFoundException (String mensaje) {
		// se invoca al construtor del padre que espera un mensaje
		super(mensaje);
		
	}

}
