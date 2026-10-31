package ar.edu.unrn.seminario.modelo;

import java.util.Objects;

import ar.edu.unrn.seminario.exception.DatoVacioException;

public class Cama {
	private String tipo;
	
	public Cama(String tipo) throws DatoVacioException
	{
		if(tipo.isEmpty()) {
			throw new DatoVacioException("No a seleccionado una cama. Por favor, intente de nuevo.");
		}
		
		this.setTipo(tipo);	
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(tipo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cama other = (Cama) obj;
		return Objects.equals(tipo, other.tipo);
	}

	@Override
	public String toString() {
		return "la cama es: "+ tipo;
	}
	
}
