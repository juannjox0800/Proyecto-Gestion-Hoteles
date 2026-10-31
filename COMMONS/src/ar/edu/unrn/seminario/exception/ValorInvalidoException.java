package ar.edu.unrn.seminario.exception;

public class ValorInvalidoException extends RuntimeException{
	
	public ValorInvalidoException() { 	}
	
	public ValorInvalidoException(String mensaje) { 
		super(mensaje);
	}
}
