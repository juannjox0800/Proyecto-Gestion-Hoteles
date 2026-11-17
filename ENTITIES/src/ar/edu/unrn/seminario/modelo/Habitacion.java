package ar.edu.unrn.seminario.modelo;

import java.util.*;
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;

public class Habitacion {
	private int numero;
	private String tipoHabitacion;
	private String estado;
	private float costo;
	private List <Cama> camas = new ArrayList<Cama>();
	
	
	public Habitacion(int numero, String tipoHabitacion, float costo) throws DatoVacioException, ValorInvalidoException
	{ 
		
		setNumero(numero);
		setTipoHabitacion(tipoHabitacion);
		setCosto(costo);
		
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) throws ValorInvalidoException{
		if(numero <= 0) {
			throw new ValorInvalidoException("Valor inválido. Por favor, ingrese la información correctamente.");	
		}
		
		this.numero = numero;
	}

	public String getTipoHabitacion() {
		return tipoHabitacion;
	}

	public void setTipoHabitacion(String tipoHabitacion) throws DatoVacioException {
		
		if(tipoHabitacion.isEmpty()) {
			throw new DatoVacioException("No pueden existir campos vacíos. Por favor, ingrese la información restante.");
		}
		
		this.tipoHabitacion = tipoHabitacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) throws ValorInvalidoException {
		if(costo <= 0) {
			throw new ValorInvalidoException("Valor inválido. Por favor, ingrese la información correctamente.");	
		}
	
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
	
	public int cantCamas() {
		return this.camas.size();
	}
	
	public int cantHuespedes() {
		int contador = 0;
	    
	    for (Cama cama : this.camas) {
	        if ("Simple".equalsIgnoreCase(cama.getTipo())) {
	            contador += 1;
	        } else if ("Matrimonial".equalsIgnoreCase(cama.getTipo())) {
	            contador += 2;
	        }
	    }
	    
	    return contador;
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
