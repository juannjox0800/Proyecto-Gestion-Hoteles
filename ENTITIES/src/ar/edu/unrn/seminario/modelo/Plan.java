package ar.edu.unrn.seminario.modelo;
import java.util.ArrayList;
import java.util.Objects;

public class Plan {
private ArrayList<Servicio> servicios;
private int costo;
private String nombre;
private boolean estadoActivo;

public Plan(ArrayList<Servicio> servicios, int costo, String nombre, boolean estadoActivo) {
	super();
	this.servicios = servicios;
	this.costo = costo;
	this.nombre = nombre;
	this.estadoActivo = estadoActivo;
}

public Plan(int costo, String nombre, boolean estadoActivo) {
	super();
	this.costo = costo;
	this.nombre = nombre;
	this.estadoActivo = estadoActivo;
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

public boolean getEstadoActivo() {
	return estadoActivo;
}

public void setEstadoActivo(boolean estadoActivo) {
	this.estadoActivo = estadoActivo;
}

public void agregarSevicio(Servicio servicio) {
	servicios.add(servicio);
}

public void eliminarServicio(Servicio servicio) {
    servicios.remove(servicio);
}

@Override
public int hashCode() {
	return Objects.hash(Integer.valueOf(costo), Boolean.valueOf(estadoActivo), nombre, servicios);
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
	return costo == other.costo && estadoActivo == other.estadoActivo && Objects.equals(nombre, other.nombre)
			&& Objects.equals(servicios, other.servicios);
}

@Override
public String toString() {
	return "Plan [servicios=" + servicios + ", costo=" + costo + ", nombre=" + nombre + ", estadoActivo=" + estadoActivo
			+ "]";
}




}
