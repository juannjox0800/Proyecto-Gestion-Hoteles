package ar.edu.unrn.seminario.modelo;

import java.util.*;
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;

public class Habitacion {
	private int numero;
	private String tipoHabitacion;
	private String estado;
	private int costo;
	private List <Cama> camas = new ArrayList<Cama>();
	
	
	public Habitacion(int numero, String tipoHabitacion, int costo) throws DatoVacioException, ValorInvalidoException
	{ 
		
		if(tipoHabitacion.isEmpty()) {
			throw new DatoVacioException("No pueden existir campos vacíos. Por favor, ingrese la información restante.");
		}
		
		if(numero <= 0  || costo <= 0 ) {
			throw new ValorInvalidoException("Valores inválidos. Por favor, ingrese la información correctamente.");
			
		}
		
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

	public List<Cama> getCamas() {
		return camas;
	}

	public void setCamas(List<Cama> camas) {
		this.camas = camas;
	}
	
	
	public void agregarCama(Cama cama) {
		this.camas.add(cama);
	}
	
	public void eliminarCama(Cama cama) {
		this.camas.remove(cama);
		
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(numero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Habitacion other = (Habitacion) obj;
		return (numero == other.numero);
	}

	@Override
	public String toString() {
		return "Habitacion [numero=" + numero + ", tipoHabitacion=" + tipoHabitacion + ", estado=" + estado + ", costo="
				+ costo + ", camas=" + camas + "]";
	}
	
}
