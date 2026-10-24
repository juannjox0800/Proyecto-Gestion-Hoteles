package ar.edu.unrn.seminario.dto;

import java.time.LocalDate;

public class ServicioExtraDTO extends ServicioDTO {

	private int dias;
	private LocalDate fechaSolicitud;
	
	public ServicioExtraDTO(String nombre, String descripcion, int precio) {
		super(nombre, descripcion, precio);
	}
	
	public int getDias() {
		return dias;
	}
	
	public void setDias(int dias) {
		this.dias = dias;
	}
	
	public LocalDate getFechaSolicitud() {
		return fechaSolicitud;
	}
	
	public void setFechaSolicitud(LocalDate fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	
	
	
}
