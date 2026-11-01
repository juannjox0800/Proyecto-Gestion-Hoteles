package ar.edu.unrn.seminario.exception;

public class EntidadNoEncontradaException extends Exception{
	
	public EntidadNoEncontradaException() { 	}
	
	public EntidadNoEncontradaException(String mensaje) {
		super(mensaje);
	}
}
