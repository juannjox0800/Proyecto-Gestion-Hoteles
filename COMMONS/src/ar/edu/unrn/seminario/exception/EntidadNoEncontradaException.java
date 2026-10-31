package ar.edu.unrn.seminario.exception;

public class EntidadNoEncontradaException extends RuntimeException{
	
	public EntidadNoEncontradaException() { 	}
	
	public EntidadNoEncontradaException(String mensaje) {
		super(mensaje);
	}
}
