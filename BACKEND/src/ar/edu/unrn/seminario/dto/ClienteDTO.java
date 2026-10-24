package ar.edu.unrn.seminario.dto;

public class ClienteDTO {
	private String nombre;
	private String apellido;
	private String contacto;
	private int dni;
	
	public ClienteDTO(String nombre, String apellido, String contacto, int dni) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.contacto = contacto;
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
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

	public void setDni(int dni) {
		this.dni = dni;
	}
}
