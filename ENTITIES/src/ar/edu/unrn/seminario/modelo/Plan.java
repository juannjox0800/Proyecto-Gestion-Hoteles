package ar.edu.unrn.seminario.modelo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadDuplicadaException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;

public class Plan {
	private List<Servicio> servicios;
	private float costo;
	private String nombre;
	private boolean estadoActivo;

	public Plan(List<Servicio> servicios, float costo, String nombre, boolean estadoActivo)throws ValorInvalidoException,DatoVacioException, EntidadDuplicadaException{
		this.setServicios(servicios);
		this.setCosto(costo);
		this.setNombre(nombre);
		this.setServicios(servicios);
	}

	public Plan(float costo, String nombre, boolean estadoActivo)throws ValorInvalidoException,DatoVacioException {
		this.setCosto(costo);
		this.setNombre(nombre);
		this.setEstadoActivo(estadoActivo);
		this.servicios = new ArrayList<Servicio>();
	}

	public List<Servicio> getServicios() {
		return servicios;
	}

	public void setServicios(List<Servicio> servicios)throws ValorInvalidoException, EntidadDuplicadaException {
		this.servicios = new ArrayList<>();
		for (Servicio a : servicios) {
			this.agregarServicio(a);
		}
	}

	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) throws ValorInvalidoException{
		if (costo<0) throw new ValorInvalidoException("costo invalido, vuelve a escribir uno");
		this.costo = costo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) throws DatoVacioException{
		if (nombre.trim().isEmpty()|| nombre==null) throw new DatoVacioException("nombre vacio, escribe un nombre para el plan");
		this.nombre = nombre;
	}

	public boolean isEstadoActivo() {
		return estadoActivo;
	}

	public void setEstadoActivo(boolean estadoActivo) {
		this.estadoActivo = estadoActivo;
	}
	public boolean getEstadoActivo() {
		return estadoActivo;
	}

	public void agregarServicio(Servicio servicio) throws ValorInvalidoException, EntidadDuplicadaException {
		boolean yaAgregado = servicios.stream().anyMatch(s -> s.getNombre().equalsIgnoreCase(servicio.getNombre()));	
		if (yaAgregado) throw new EntidadDuplicadaException("ya se encuentra ese servicio en el plan");
		if ( servicio.getNombre()== null ||servicio.getNombre().trim().isEmpty() ||servicio.getPrecio()<0 || servicio.isEstadoVigente()==false) {
			throw new ValorInvalidoException("intentaste ingresar servicios invalidos, chequealos y vuelve a intentar");
		}
		servicios.add(servicio);
		
	}

	public void eliminarServicio(Servicio servicio) throws EntidadNoEncontradaException {
		boolean eliminado = this.servicios.removeIf(h -> h.getNombre()==nombre);		
		if (!eliminado) {
			throw new EntidadNoEncontradaException("No se ha encontrado el servicio.");
			}
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Plan other = (Plan) obj;
		return Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return "Plan [servicios=" + servicios + ", costo=" + costo + ", nombre=" + nombre + ", estadoActivo=" + estadoActivo
			+ "]";
	}
}
