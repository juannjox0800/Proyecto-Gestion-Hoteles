package ar.edu.unrn.seminario.modelo;
import java.util.ArrayList;
import java.util.Objects;

public class Plan {
	private ArrayList<Servicio> servicios;
	private int costo;
	private String nombre;
	private boolean estadoActivo;

	public Plan(ArrayList<Servicio> servicios, int costo, String nombre, boolean estadoActivo) {
		this.servicios = servicios;
		this.costo = costo;
		this.nombre = nombre;
		this.estadoActivo = estadoActivo;
	}

	public Plan(int costo, String nombre, boolean estadoActivo) {
		this.costo = costo;
		this.nombre = nombre;
		this.estadoActivo = estadoActivo;
		this.servicios = new ArrayList<Servicio>();
	}

	public ArrayList<Servicio> getServicios() {
		return servicios;
	}

	public void setServicios(ArrayList<Servicio> servicios) {
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

	public void agregarServicio(Servicio servicio) {
		servicios.add(servicio);
	}

	public void eliminarServicio(Servicio servicio) {
    	servicios.remove(servicio);
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
