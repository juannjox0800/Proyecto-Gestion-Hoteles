package ar.edu.unrn.seminario.modelo;

import java.util.Objects;

public class Huesped {
	private String Nombre;
	private String Apellido;
	private int dni;
	
	public Huesped() {
		
	}

	public Huesped(String nombre, String apellido, int dni) {
		Nombre = nombre;
		Apellido = apellido;
		this.dni = dni;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getApellido() {
		return Apellido;
	}

	public void setApellido(String apellido) {
		Apellido = apellido;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Apellido, Nombre, Integer.valueOf(dni));
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
		return "Huesped [Nombre=" + Nombre + ", Apellido=" + Apellido + ", dni=" + dni + "]";
	}
	
	
}
