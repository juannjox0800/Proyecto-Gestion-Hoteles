package ar.edu.unrn.seminario.api;

import java.time.*;
import java.util.*;

import ar.edu.unrn.seminario.dto.CamaDTO;
import ar.edu.unrn.seminario.dto.ClienteDTO;
import ar.edu.unrn.seminario.dto.HabitacionDTO;
import ar.edu.unrn.seminario.dto.HuespedDTO;
import ar.edu.unrn.seminario.dto.PagoDTO;
import ar.edu.unrn.seminario.dto.PlanDTO;
import ar.edu.unrn.seminario.dto.ReservaDTO;
import ar.edu.unrn.seminario.dto.ServicioDTO;
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadDuplicadaException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;

import ar.edu.unrn.seminario.exception.FechaInvalidaException;
import ar.edu.unrn.seminario.exception.FormatoInvalidoException;

import ar.edu.unrn.seminario.modelo.Cliente;
import ar.edu.unrn.seminario.modelo.Habitacion;
import ar.edu.unrn.seminario.modelo.Plan;
import ar.edu.unrn.seminario.modelo.Reserva;
import ar.edu.unrn.seminario.modelo.Servicio;

public interface IApi {
	
	void registrarCliente(String nombre, String apellido, String contacto, int dni) throws EntidadDuplicadaException, DatoVacioException, ValorInvalidoException;
	void modificarCLiente(int dni, String nombre, String apellido, String contacto)throws EntidadNoEncontradaException, DatoVacioException,ValorInvalidoException ;
	void eliminarCliente(int dni)throws EntidadNoEncontradaException;
	boolean existeCliente(int dni);
	ClienteDTO obtenerCliente(int dni)throws EntidadNoEncontradaException, FormatoInvalidoException ;

	List<ClienteDTO>obtenerListaClientes();
	
	
	void registrarHabitacion(int numero, String tipoHabitacion, String estado, int costo, List<String> camas) throws EntidadDuplicadaException, DatoVacioException, ValorInvalidoException;
	boolean modificarHabitacion(int numero, int numeroNuevo, String tipoHabitacion, String estado,int costo, List<String> camas) throws DatoVacioException, EntidadNoEncontradaException;
	boolean eliminarHabitacion(int numero) throws EntidadNoEncontradaException;
	HabitacionDTO obtenerHabitacion(int numero) throws EntidadNoEncontradaException;
	List <HabitacionDTO>obtenerListaHabitacion();

	
	void registrarServicio(String nombre,String descripcion, float precio, boolean estadoVigente)throws EntidadDuplicadaException, ValorInvalidoException, DatoVacioException ;
	void modificarServicio(String nombre, String descripcion, float precio, boolean estadoVigente) throws EntidadNoEncontradaException, ValorInvalidoException;
	void eliminarServicio(String nombre)throws EntidadNoEncontradaException;
	ServicioDTO obtenerServicio(String nombre) throws EntidadNoEncontradaException;

	List<ServicioDTO> obtenerListaServicios();
	
	void registrarPlan(ArrayList<ServicioDTO> listaServicios, int costo,String nombre, boolean esActivo)throws EntidadDuplicadaException, DatoVacioException, ValorInvalidoException;
	void modificarPlan(ArrayList<ServicioDTO> listaServicios, int costo,String nombre, boolean esActivo)throws EntidadNoEncontradaException, ValorInvalidoException, DatoVacioException;
	void eliminarPlan(String nombre)throws EntidadNoEncontradaException;
	PlanDTO obtenerPlan(String nombre)throws EntidadNoEncontradaException;
	List<PlanDTO> obtenerListaPlan();
	void guardarListaPLan(List <PlanDTO> listaNueva) throws DatoVacioException, ValorInvalidoException;

	//
	void realizarReserva(List <HabitacionDTO>obtenerListaHabitacion, PlanDTO plan,ClienteDTO cliente, LocalDate fechaDeEntrada, LocalDate fechaSalida, PagoDTO pagoSeña, int costoTotal)throws DatoVacioException, ValorInvalidoException;
	void modificarReserva(int numeroReserva);
	void cancelarReserva(int numeroReserva);
	ReservaDTO obtenerReserva(int numeroReserva) throws EntidadNoEncontradaException, FormatoInvalidoException;
	List<ReservaDTO> obtenerListaReserva();
	List<HabitacionDTO> buscarDisponibilidad(LocalDate fechaEntrada, LocalDate fechaSalida) throws FechaInvalidaException;
	void RegistrarPago(String tipoPago, int monto, LocalDate fechaPago, int idPago);
	PlanDTO buscarPlan(String nombrePlan) throws EntidadNoEncontradaException;
	int calcularSeña(int costoHabitaciones, LocalDate fechaEntrada, LocalDate fechaSalida);
	
	
	
	
	
	
}
