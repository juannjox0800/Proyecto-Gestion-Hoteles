package ar.edu.unrn.seminario.dto;

public class HuespedDTO {
	private String Nombre;
	private String Apellido;
	private int dni;
	
	public HuespedDTO() {
		
	}

	public HuespedDTO(String nombre, String apellido, int dni) {
		Nombre = nombre;
		Apellido = apellido;
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

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}
}
