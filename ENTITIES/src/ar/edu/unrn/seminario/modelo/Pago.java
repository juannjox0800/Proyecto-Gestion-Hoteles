package ar.edu.unrn.seminario.modelo;

import java.time.LocalDate;
import java.util.Objects;

public class Pago {
	private String TipoPago;
	private int Monto;
	private LocalDate FechaPago;
	private int IdPago;
	
	public Pago(String tipoPago, int monto, LocalDate fechaPago, int idPago) {
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

	@Override
	public int hashCode() {
		return Objects.hash(FechaPago, Integer.valueOf(IdPago), Integer.valueOf(Monto), TipoPago);
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
		return IdPago == other.IdPago;
	}

	@Override
	public String toString() {
		return "Pago [TipoPago=" + TipoPago + ", Monto=" + Monto + ", FechaPago=" + FechaPago + ", IdPago=" + IdPago
				+ "]";
	}

}
