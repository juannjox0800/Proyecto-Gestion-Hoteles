package ar.edu.unrn.seminario.exception;

public class EntidadDuplicadaException extends RuntimeException{
	
	public EntidadDuplicadaException() {	}
	
	public EntidadDuplicadaException(String mensaje) {
		super(mensaje);
	}
}
