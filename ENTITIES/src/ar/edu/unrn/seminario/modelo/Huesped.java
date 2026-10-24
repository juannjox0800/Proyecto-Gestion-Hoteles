package ar.edu.unrn.seminario.modelo;

import java.util.Objects;

public class Huesped {
	private String nombre;
	private String apellido;
	private int dni;
	
	public Huesped() {
		
	}

	public Huesped(String nombre, String apellido, int dni) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Huesped other = (Huesped) obj;
		return dni == other.dni;
	}

	@Override
	public String toString() {
		return "Huesped [Nombre=" + nombre + ", Apellido=" + apellido + ", dni=" + dni + "]";
	}
	
	
}
