package ar.edu.unrn.seminario.dto;

public class ClienteDTO {
	private String Nombre;
	private String Apellido;
	private String Contacto;
	private int dni;
	
	public ClienteDTO(String nombre, String apellido, String contacto, int dni) {
		Nombre = nombre;
		Apellido = apellido;
		Contacto = contacto;
		this.dni = dni;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getApellido() {
		return Apellido;
	}

	public void setApellido(String apellido) {
		Apellido = apellido;
	}

	public String getContacto() {
		return Contacto;
	}

	public void setContacto(String contacto) {
		Contacto = contacto;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}
}
