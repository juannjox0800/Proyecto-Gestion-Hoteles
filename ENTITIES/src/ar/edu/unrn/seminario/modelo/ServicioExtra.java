package ar.edu.unrn.seminario.modelo;

import java.time.LocalDate;
import java.util.Objects;

public class ServicioExtra extends Servicio {

	private int dias;
	private LocalDate fechaSolicitud;
	
	public ServicioExtra(String nombre, String descripcion, int precio) {
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

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(dias, fechaSolicitud);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServicioExtra other = (ServicioExtra) obj;
		return dias == other.dias && Objects.equals(fechaSolicitud, other.fechaSolicitud);
	}

	@Override
	public String toString() {
	
		return super.toString() + "Servicio Extra:" + "Cantidad de días solicitados: " + dias + "Fecha de solicitud: " + fechaSolicitud;
	}
	
	
	

}
