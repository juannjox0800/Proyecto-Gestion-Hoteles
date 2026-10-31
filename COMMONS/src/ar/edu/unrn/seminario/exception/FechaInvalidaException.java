package ar.edu.unrn.seminario.exception;

public class FechaInvalidaException extends RuntimeException{
	
	public FechaInvalidaException() {	}
	
	public FechaInvalidaException(String mensaje) {
		super(mensaje);
	}
}
