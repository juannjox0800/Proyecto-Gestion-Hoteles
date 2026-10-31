package ar.edu.unrn.seminario.exception;

public class FormatoInvalidoException extends RuntimeException{
	public FormatoInvalidoException() { 	}
	
	public FormatoInvalidoException(String mensaje) {
		super(mensaje);
	}
}
