package ar.edu.unrn.seminario.dto;

import java.time.LocalDate;

public class PagoDTO {
	private String TipoPago;
	private int Monto;
	private LocalDate FechaPago;
	private int IdPago;
	
	public PagoDTO(String tipoPago, int monto, LocalDate fechaPago, int idPago) {
		TipoPago = tipoPago;
		Monto = monto;
		FechaPago = fechaPago;
		IdPago = idPago;
	}

	public String getTipoPago() {
		return TipoPago;
	}

	public void setTipoPago(String tipoPago) {
		TipoPago = tipoPago;
	}

	public int getMonto() {
		return Monto;
	}

	public void setMonto(int monto) {
		Monto = monto;
	}

	public LocalDate getFechaPago() {
		return FechaPago;
	}

	public void setFechaPago(LocalDate fechaPago) {
		FechaPago = fechaPago;
	}

	public int getIdPago() {
		return IdPago;
	}

	public void setIdPago(int idPago) {
		IdPago = idPago;
	}
}
