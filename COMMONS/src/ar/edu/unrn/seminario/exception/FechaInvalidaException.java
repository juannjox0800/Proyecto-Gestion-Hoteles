package ar.edu.unrn.seminario.exception;

public class FechaInvalidaException extends Exception{
	
	public FechaInvalidaException() {	}
	
	public FechaInvalidaException(String mensaje) {
		super(mensaje);
	}
}
