package ar.edu.unrn.seminario.modelo;

import java.util.Objects;

public class Cama {
	private String tipo;
	
	public Cama(String tipo) {
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
