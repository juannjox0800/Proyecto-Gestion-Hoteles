package ar.edu.unrn.seminario.modelo;

import java.util.Objects;

import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;

public class Cliente {
	private String nombre;
	private String apellido;
	private String contacto;
	private int dni;
	
	public Cliente(String nombre, String apellido, String contacto, int dni) throws DatoVacioException, ValorInvalidoException{
		setNombre(nombre);
        setApellido(apellido);
        setContacto(contacto);
        setDni(dni);
	}

	public String getNombre() {
		
		return nombre;
	}

	public void setNombre(String nombre) throws DatoVacioException, ValorInvalidoException{
		if (nombre== null || nombre.trim().isEmpty()) {
			throw new DatoVacioException("El nombre no puede estar vacio");
		}
		if (nombre.matches(".*\\d.*")){
	        throw new ValorInvalidoException("El nombre no puede contener números.");
	    }
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) throws DatoVacioException, ValorInvalidoException {
		if (apellido ==null || apellido.trim().isEmpty()) {
			throw new DatoVacioException("El apellido no puede estar vacio");
		}
		if (apellido.matches(".*\\d.*")) {
	        throw new ValorInvalidoException("El apellido no puede contener números.");
	    }
		this.apellido = apellido;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) throws DatoVacioException {
		if (dni <= 0) {
            throw new DatoVacioException("El DNI no puede estar vacío o ser inválido.");
		}
		this.dni = dni;
	}

	@Override
	public String toString() {
		return "Cliente [Nombre=" + nombre + ", Apellido=" + apellido + ", Contacto=" + contacto + ", dni=" + dni + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return dni == other.dni;
	}
	
	

}
