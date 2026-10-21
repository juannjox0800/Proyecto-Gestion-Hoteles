package ar.edu.unrn.seminario.dto;

import java.util.*;

public class HabitacionDTO {
	private int numero;
	private String tipoHabitacion;
	private String estado;
	private int costo;
	private List <CamaDTO> camas = new ArrayList<CamaDTO>();
	
	
	public HabitacionDTO() {
		
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


	@Override
	public int hashCode() {
		return Objects.hash(camas, costo, estado, numero, tipoHabitacion);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HabitacionDTO other = (HabitacionDTO) obj;
		return Objects.equals(camas, other.camas) && costo == other.costo && Objects.equals(estado, other.estado)
				&& numero == other.numero && Objects.equals(tipoHabitacion, other.tipoHabitacion);
	}


	@Override
	public String toString() {
		return "la  habitacion N°"+this.numero+"\nEs del tipo: "+ this.tipoHabitacion+ "\nSu estado actual" + this.estado + "\nCosto de:"+ this.costo;
	}
	
	
}
