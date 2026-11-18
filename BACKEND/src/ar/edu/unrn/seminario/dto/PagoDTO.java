package ar.edu.unrn.seminario.dto;

import java.time.LocalDate;

public class PagoDTO {
	private String tipoPago;
	private float monto;
	private LocalDate fechaPago;
	private int idPago;
	
	public PagoDTO(String tipoPago, float monto, LocalDate fechaPago) {
		this.tipoPago = tipoPago;
		this.monto = monto;
		this.fechaPago = fechaPago;
	}

	public String getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}

	public float getMonto() {
		return monto;
	}

	public void setMonto(float monto) {
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
