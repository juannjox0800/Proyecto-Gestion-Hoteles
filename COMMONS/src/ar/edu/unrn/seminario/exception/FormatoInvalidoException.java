package ar.edu.unrn.seminario.exception;

public class FormatoInvalidoException extends Exception{
	public FormatoInvalidoException() { 	}
	
	public FormatoInvalidoException(String mensaje) {
		super(mensaje);
	}
}
