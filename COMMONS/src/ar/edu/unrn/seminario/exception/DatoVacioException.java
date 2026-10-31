package ar.edu.unrn.seminario.exception;

public class DatoVacioException extends RuntimeException{
	
	public DatoVacioException() {	}
	
	public DatoVacioException(String mensaje) {
		super(mensaje);
	}
}
