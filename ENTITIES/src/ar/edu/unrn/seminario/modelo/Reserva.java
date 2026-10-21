package ar.edu.unrn.seminario.modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Reserva {
	private List<Habitacion> Habitaciones;
	//private Plan plan;
	private Cliente Cliente;
	private LocalDate FechaEntrada;
	private LocalDate FechaSalida;
	private Pago PagoSeña;
	private Pago PagoFinal;
	private int CostoTotal;
	private boolean EstadoCancelacion;
	private int NumeroReserva;
	private List<Huesped> Huespedes;
	
	public Reserva(List<Habitacion> habitaciones, ar.edu.unrn.seminario.modelo.Cliente cliente, LocalDate fechaEntrada,
			LocalDate fechaSalida, Pago pagoSeña, Pago pagoFinal, int costoTotal, boolean estadoCancelacion,
			int numeroReserva, List<Huesped> huespedes) {
		Habitaciones = habitaciones;
		Cliente = cliente;
		FechaEntrada = fechaEntrada;
		FechaSalida = fechaSalida;
		PagoSeña = pagoSeña;
		PagoFinal = pagoFinal;
		CostoTotal = costoTotal;
		EstadoCancelacion = estadoCancelacion;
		NumeroReserva = numeroReserva;
		Huespedes = huespedes;
	}

	public List<Habitacion> getHabitaciones() {
		return Habitaciones;
	}

	public void setHabitaciones(List<Habitacion> habitaciones) {
		Habitaciones = habitaciones;
	}

	public Cliente getCliente() {
		return Cliente;
	}

	public void setCliente(Cliente cliente) {
		Cliente = cliente;
	}

	public LocalDate getFechaEntrada() {
		return FechaEntrada;
	}

	public void setFechaEntrada(LocalDate fechaEntrada) {
		FechaEntrada = fechaEntrada;
	}

	public LocalDate getFechaSalida() {
		return FechaSalida;
	}

	public void setFechaSalida(LocalDate fechaSalida) {
		FechaSalida = fechaSalida;
	}

	public Pago getPagoSeña() {
		return PagoSeña;
	}

	public void setPagoSeña(Pago pagoSeña) {
		PagoSeña = pagoSeña;
	}

	public Pago getPagoFinal() {
		return PagoFinal;
	}

	public void setPagoFinal(Pago pagoFinal) {
		PagoFinal = pagoFinal;
	}

	public int getCostoTotal() {
		return CostoTotal;
	}

	public void setCostoTotal(int costoTotal) {
		CostoTotal = costoTotal;
	}

	public boolean isEstadoCancelacion() {
		return EstadoCancelacion;
	}

	public void cambiarEstadoCancelacion() {
		EstadoCancelacion = !EstadoCancelacion;
	}

	public int getNumeroReserva() {
		return NumeroReserva;
	}

	public void setNumeroReserva(int numeroReserva) {
		NumeroReserva = numeroReserva;
	}

	public List<Huesped> getHuespedes() {
		return Huespedes;
	}

	public void setHuespedes(List<Huesped> huespedes) {
		Huespedes = huespedes;
	}
	
	public void agregarHuesped (Huesped huesped) {
		Huespedes.add(huesped);
	}
	
	public void eliminarHuesped(int dni) {
		for (Huesped e: Huespedes) {
			if (e.getDni() == dni) {
				Huespedes.remove(e);
			}
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(Cliente, Integer.valueOf(CostoTotal), Boolean.valueOf(EstadoCancelacion), FechaEntrada,
				FechaSalida, Habitaciones, Huespedes, Integer.valueOf(NumeroReserva), PagoFinal, PagoSeña);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reserva other = (Reserva) obj;
		return Objects.equals(Cliente, other.Cliente) && CostoTotal == other.CostoTotal
				&& EstadoCancelacion == other.EstadoCancelacion && Objects.equals(FechaEntrada, other.FechaEntrada)
				&& Objects.equals(FechaSalida, other.FechaSalida) && Objects.equals(Habitaciones, other.Habitaciones)
				&& Objects.equals(Huespedes, other.Huespedes) && NumeroReserva == other.NumeroReserva
				&& Objects.equals(PagoFinal, other.PagoFinal) && Objects.equals(PagoSeña, other.PagoSeña);
	}

	@Override
	public String toString() {
		return "Reserva [Habitaciones=" + Habitaciones + ", Cliente=" + Cliente + ", FechaEntrada=" + FechaEntrada
				+ ", FechaSalida=" + FechaSalida + ", PagoSeña=" + PagoSeña + ", PagoFinal=" + PagoFinal
				+ ", CostoTotal=" + CostoTotal + ", EstadoCancelacion=" + EstadoCancelacion + ", NumeroReserva="
				+ NumeroReserva + ", Huespedes=" + Huespedes + "]";
	}
}
