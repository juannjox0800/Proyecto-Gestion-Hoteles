package ar.edu.unrn.seminario.modelo;

import java.util.Objects;

public class Servicio {

	private String nombre;
	private String descripcion;
	private int precio;
	private boolean estadoVigente;
	
	public Servicio(String nombre, String descripcion, int precio ) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.estadoVigente = false;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public boolean isEstadoVigente() {
		return estadoVigente;
	}

	public void setEstadoVigente(boolean estadoVigente) {
		this.estadoVigente = estadoVigente;
	}

	@Override
	public int hashCode() {
		return Objects.hash(descripcion, estadoVigente, nombre, precio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Servicio other = (Servicio) obj;
		return Objects.equals(descripcion, other.descripcion) && estadoVigente == other.estadoVigente
				&& Objects.equals(nombre, other.nombre) && precio == other.precio;
	}

	@Override
	public String toString() {
		return "Nombre del servicio: \n" + nombre + "\n Descripción: " + descripcion + " \n Precio: " + precio + "\n Vigencia: "
				+ (estadoVigente ? "Sí" : "No");
	}
	
	
	
	
	
	
	
	
}
