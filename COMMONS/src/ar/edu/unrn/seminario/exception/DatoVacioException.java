package ar.edu.unrn.seminario.exception;

public class DatoVacioException extends Exception{
	
	public DatoVacioException() {	}
	
	public DatoVacioException(String mensaje) {
		super(mensaje);
	}
}
