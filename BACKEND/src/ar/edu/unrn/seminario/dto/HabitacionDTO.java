package ar.edu.unrn.seminario.dto;

import java.util.*;

public class HabitacionDTO {
	private int numero;
	private String tipoHabitacion;
	private String estado;
	private int costo;
	private List <CamaDTO> camas = new ArrayList<CamaDTO>();
	
	
	public HabitacionDTO(int numero, String tipoHabitacion, int costo) {
		this.numero = numero;
		this.tipoHabitacion = tipoHabitacion;
		this.costo = costo;
	}


	public int getNumero() {
		return numero;
	}


	public void setNumero(int numero) {
		this.numero = numero;
	}


	public String getTipoHabitacion() {
		return tipoHabitacion;
	}


	public void setTipoHabitacion(String tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public int getCosto() {
		return costo;
	}


	public void setCosto(int costo) {
		this.costo = costo;
	}


	public List<CamaDTO> getCamas() {
		return camas;
	}


	public void setCamas(List<CamaDTO> camas) {
		this.camas = camas;
	}


	
}
