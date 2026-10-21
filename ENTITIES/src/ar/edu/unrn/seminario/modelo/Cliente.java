package ar.edu.unrn.seminario.modelo;

import java.util.Objects;

public class Cliente {
	private String Nombre;
	private String Apellido;
	private String Contacto;
	private int dni;
	
	public Cliente(String nombre, String apellido, String contacto, int dni) {
		Nombre = nombre;
		Apellido = apellido;
		Contacto = contacto;
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

	public String getContacto() {
		return Contacto;
	}

	public void setContacto(String contacto) {
		Contacto = contacto;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	@Override
	public String toString() {
		return "Cliente [Nombre=" + Nombre + ", Apellido=" + Apellido + ", Contacto=" + Contacto + ", dni=" + dni + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(Apellido, Contacto, Nombre, Integer.valueOf(dni));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return dni == other.dni;
	}
	
	

}
