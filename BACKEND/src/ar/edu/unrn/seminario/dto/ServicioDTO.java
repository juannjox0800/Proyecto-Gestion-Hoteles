package ar.edu.unrn.seminario.dto;

public class ServicioDTO {

	private String nombre;
	private String descripcion;
	private int precio;
	private boolean estadoVigente;
	
	public ServicioDTO(String nombre, String descripcion, int precio) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
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
	
	
}
