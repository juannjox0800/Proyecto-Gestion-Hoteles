package ar.edu.unrn.seminario.dto;

import java.time.LocalDate;
import java.util.List;

public class ReservaDTO {
	private List<HabitacionDTO> habitaciones;
	private PlanDTO plan;
	private ClienteDTO cliente;
	private LocalDate fechaEntrada;
	private LocalDate fechaSalida;
	private PagoDTO pagoSeña;
	private PagoDTO pagoFinal;
	private int costoTotal;
	private boolean estadoCancelacion;
	private int numeroReserva;
	private List<HuespedDTO> huespedes;
	
	
	public ReservaDTO(List<HabitacionDTO> habitaciones, PlanDTO plan, ClienteDTO cliente, LocalDate fechaEntrada,
			LocalDate fechaSalida, PagoDTO pagoSeña, List<HuespedDTO> huespedes) {
		this.habitaciones = habitaciones;
		this.plan = plan;
		this.cliente = cliente;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.pagoSeña = pagoSeña;
		this.huespedes = huespedes;
	}


	public List<HabitacionDTO> getHabitaciones() {
		return habitaciones;
	}


	public void setHabitaciones(List<HabitacionDTO> habitaciones) {
		this.habitaciones = habitaciones;
	}


	public PlanDTO getPlan() {
		return plan;
	}


	public void setPlan(PlanDTO plan) {
		this.plan = plan;
	}


	public ClienteDTO getCliente() {
		return cliente;
	}


	public void setCliente(ClienteDTO cliente) {
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


	public PagoDTO getPagoSeña() {
		return pagoSeña;
	}


	public void setPagoSeña(PagoDTO pagoSeña) {
		this.pagoSeña = pagoSeña;
	}


	public PagoDTO getPagoFinal() {
		return pagoFinal;
	}


	public void setPagoFinal(PagoDTO pagoFinal) {
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


	public void setEstadoCancelacion(boolean estadoCancelacion) {
		this.estadoCancelacion = estadoCancelacion;
	}


	public int getNumeroReserva() {
		return numeroReserva;
	}


	public void setNumeroReserva(int numeroReserva) {
		this.numeroReserva = numeroReserva;
	}


	public List<HuespedDTO> getHuespedes() {
		return huespedes;
	}


	public void setHuespedes(List<HuespedDTO> huespedes) {
		this.huespedes = huespedes;
	}
	

}
