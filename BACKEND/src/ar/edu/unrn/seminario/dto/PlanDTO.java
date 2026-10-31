package ar.edu.unrn.seminario.dto;

import java.util.ArrayList;
import java.util.List;

public class PlanDTO {
	private List<ServicioDTO> servicios;
	private int costo;
	private String nombre;
	private boolean estadoActivo;
	
	public PlanDTO(List<ServicioDTO> servicios, int costo, String nombre) {
		this.servicios = servicios;
		this.costo = costo;
		this.nombre = nombre;
	}

	public List<ServicioDTO> getServicios() {
		return servicios;
	}

	public void setServicios(List<ServicioDTO> servicios) {
		this.servicios = servicios;
	}

	public int getCosto() {
		return costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isEstadoActivo() {
		return estadoActivo;
	}

	public void setEstadoActivo(boolean estadoActivo) {
		this.estadoActivo = estadoActivo;
	}
	
}
