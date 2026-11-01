package ar.edu.unrn.seminario.exception;

public class ValorInvalidoException extends Exception{
	
	public ValorInvalidoException() { 	}
	
	public ValorInvalidoException(String mensaje) { 
		super(mensaje);
	}
}
