package ar.edu.unrn.seminario.modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Reserva {
	private List<Habitacion> habitaciones;
	private Plan plan;
	private Cliente cliente;
	private LocalDate fechaEntrada;
	private LocalDate fechaSalida;
	private Pago pagoSeña;
	private Pago pagoFinal;
	private int costoTotal;
	private boolean estadoCancelacion;
	private int numeroReserva;
	private List<Huesped> huespedes;

	public Reserva(List<Habitacion> habitaciones, Plan plan, Cliente cliente,
			LocalDate fechaEntrada, LocalDate fechaSalida, Pago pagoSeña, int numeroReserva, List<Huesped> huespedes) {
		this.habitaciones = habitaciones;
		this.plan = plan;
		this.cliente = cliente;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.pagoSeña = pagoSeña;
		this.numeroReserva = numeroReserva;
		this.huespedes = huespedes;
		
		this.estadoCancelacion = false;
		this.costoTotal = 0;
	}



	public List<Habitacion> getHabitaciones() {
		return habitaciones;
	}

	public void setHabitaciones(List<Habitacion> habitaciones) {
		this.habitaciones = habitaciones;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public LocalDate getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(LocalDate fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public LocalDate getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(LocalDate fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public Pago getPagoSeña() {
		return pagoSeña;
	}

	public void setPagoSeña(Pago pagoSeña) {
		this.pagoSeña = pagoSeña;
	}

	public Pago getPagoFinal() {
		return pagoFinal;
	}

	public void setPagoFinal(Pago pagoFinal) {
		this.pagoFinal = pagoFinal;
	}

	public int getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(int costoTotal) {
		this.costoTotal = costoTotal;
	}

	public boolean isEstadoCancelacion() {
		return estadoCancelacion;
	}

	public void cambiarEstadoCancelacion() {
		this.estadoCancelacion = !estadoCancelacion;
	}

	public int getNumeroReserva() {
		return numeroReserva;
	}

	public void setNumeroReserva(int numeroReserva) {
		this.numeroReserva = numeroReserva;
	}

	public List<Huesped> getHuespedes() {
		return huespedes;
	}

	public void setHuespedes(List<Huesped> huespedes) {
		this.huespedes = huespedes;
	}
	
	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public void agregarHuesped (Huesped huesped) {
		huespedes.add(huesped);
	}
	
	public void eliminarHuesped(int dni) {
		huespedes.removeIf(h -> h.getDni() == dni);
	}

	@Override
	public int hashCode() {
		return Objects.hash(numeroReserva);
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
		return (numeroReserva == other.numeroReserva);
	}



	@Override
	public String toString() {
		return "Reserva [habitaciones=" + habitaciones + ", plan=" + plan + ", cliente=" + cliente + ", fechaEntrada="
				+ fechaEntrada + ", fechaSalida=" + fechaSalida + ", pagoSeña=" + pagoSeña + ", pagoFinal=" + pagoFinal
				+ ", costoTotal=" + costoTotal + ", estadoCancelacion=" + estadoCancelacion + ", numeroReserva="
				+ numeroReserva + ", huespedes=" + huespedes + "]";
	}
	
}
