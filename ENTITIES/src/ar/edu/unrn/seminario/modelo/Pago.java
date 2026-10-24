package ar.edu.unrn.seminario.modelo;

import java.time.LocalDate;
import java.util.Objects;

public class Pago {
	private String tipoPago;
	private int monto;
	private LocalDate fechaPago;
	private int idPago;
	
	public Pago(String tipoPago, int monto, LocalDate fechaPago, int idPago) {
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

	@Override
	public int hashCode() {
		return Objects.hash(idPago);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pago other = (Pago) obj;
		return idPago == other.idPago;
	}

	@Override
	public String toString() {
		return "Pago [TipoPago=" + tipoPago + ", Monto=" + monto + ", FechaPago=" + fechaPago + ", IdPago=" + idPago
				+ "]";
	}

}
