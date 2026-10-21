package ar.edu.unrn.seminario.dto;


public class CamaDTO {
	private String tipo;
	
	public CamaDTO(String tipo) {
		this.setTipo(tipo);	
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


}
