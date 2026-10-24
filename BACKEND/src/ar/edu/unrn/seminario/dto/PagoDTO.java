package ar.edu.unrn.seminario.dto;

import java.time.LocalDate;

public class PagoDTO {
	private String tipoPago;
	private int monto;
	private LocalDate fechaPago;
	private int idPago;
	
	public PagoDTO(String tipoPago, int monto, LocalDate fechaPago, int idPago) {
		this.tipoPago = tipoPago;
		this.monto = monto;
		this.fechaPago = fechaPago;
		this.idPago = idPago;
	}

	public String getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}

	public int getMonto() {
		return monto;
	}

	public void setMonto(int monto) {
		this.monto = monto;
	}

	public LocalDate getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(LocalDate fechaPago) {
		this.fechaPago = fechaPago;
	}

	public int getIdPago() {
		return idPago;
	}

	public void setIdPago(int idPago) {
		this.idPago = idPago;
	}
}
