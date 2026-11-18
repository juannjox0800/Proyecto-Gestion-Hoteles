package ar.edu.unrn.seminario.api;


import java.sql.SQLException;
import java.util.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import ar.edu.unrn.seminario.dto.ClienteDTO;
import ar.edu.unrn.seminario.dto.HabitacionDTO;
import ar.edu.unrn.seminario.dto.PagoDTO;
import ar.edu.unrn.seminario.dto.PlanDTO;
import ar.edu.unrn.seminario.dto.ReservaDTO;
import ar.edu.unrn.seminario.dto.ServicioDTO;
import ar.edu.unrn.seminario.exception.AccesoDatosException;
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadDuplicadaException;
import ar.edu.unrn.seminario.exception.EntidadEnUsoException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;

import ar.edu.unrn.seminario.exception.FechaInvalidaException;
import ar.edu.unrn.seminario.exception.FormatoInvalidoException;

public interface IApi {
	
	void registrarCliente(String nombre, String apellido, String contacto, int dni) throws EntidadDuplicadaException, DatoVacioException, ValorInvalidoException, AccesoDatosException;
	void modificarCLiente(int dni, String nombre, String apellido, String contacto) throws EntidadNoEncontradaException, DatoVacioException, ValorInvalidoException, AccesoDatosException;
	void eliminarCliente(int dni) throws EntidadNoEncontradaException, AccesoDatosException, EntidadEnUsoException;
	boolean existeCliente(int dni) throws DatoVacioException, ValorInvalidoException, AccesoDatosException;
	ClienteDTO obtenerCliente(int dni)throws EntidadNoEncontradaException, FormatoInvalidoException, DatoVacioException, ValorInvalidoException, AccesoDatosException;
	List<ClienteDTO>obtenerListaClientes()throws DatoVacioException, ValorInvalidoException, AccesoDatosException;
	
	
	void registrarHabitacion(int numero, String tipoHabitacion, String estado, float costo, List<String> camas) throws EntidadDuplicadaException, DatoVacioException, ValorInvalidoException, AccesoDatosException;
	boolean modificarHabitacion(int numero, String tipoHabitacion, String estado,float costo, List<String> camas) throws DatoVacioException, EntidadNoEncontradaException, ValorInvalidoException, AccesoDatosException;
	boolean eliminarHabitacion(int numero) throws EntidadNoEncontradaException, EntidadEnUsoException, AccesoDatosException, DatoVacioException, ValorInvalidoException;
	HabitacionDTO obtenerHabitacion(int numero) throws EntidadNoEncontradaException, AccesoDatosException, DatoVacioException, ValorInvalidoException;
	List <HabitacionDTO>obtenerListaHabitacion() throws AccesoDatosException, DatoVacioException, ValorInvalidoException;

	
	void registrarServicio(String nombre,String descripcion, float precio, boolean estadoVigente)throws EntidadDuplicadaException, ValorInvalidoException, DatoVacioException, AccesoDatosException ;
	void modificarServicio(String nombre, String descripcion, float precio, boolean estadoVigente) throws EntidadNoEncontradaException, ValorInvalidoException, DatoVacioException, AccesoDatosException;
	ServicioDTO obtenerServicio(String nombre) throws EntidadNoEncontradaException, DatoVacioException, ValorInvalidoException, AccesoDatosException;
	void eliminarServicio(String nombre)throws EntidadNoEncontradaException, EntidadEnUsoException, AccesoDatosException;

	List<ServicioDTO> obtenerListaServicios() throws DatoVacioException, ValorInvalidoException, AccesoDatosException;
	
	void registrarPlan(ArrayList<ServicioDTO> listaServicios, float costo,String nombre, boolean esActivo)throws EntidadDuplicadaException, DatoVacioException, ValorInvalidoException, AccesoDatosException;
	void modificarPlan(ArrayList<ServicioDTO> listaServicios, float costo,String nombre, boolean esActivo)throws EntidadNoEncontradaException, ValorInvalidoException, DatoVacioException, AccesoDatosException;
	void eliminarPlan(String nombre)throws EntidadNoEncontradaException, AccesoDatosException, EntidadEnUsoException;
	PlanDTO obtenerPlan(String nombre)throws EntidadNoEncontradaException, AccesoDatosException, DatoVacioException, ValorInvalidoException;
	float calcPrecioServicios(ArrayList<ServicioDTO> listaServicios);
	
	List<PlanDTO> obtenerListaPlan() throws AccesoDatosException, ValorInvalidoException, DatoVacioException, EntidadDuplicadaException;
	void realizarReserva(List <HabitacionDTO>obtenerListaHabitacion, PlanDTO plan,ClienteDTO cliente, LocalDate fechaDeEntrada, LocalDate fechaSalida, PagoDTO pagoSeña, float costoTotal)throws DatoVacioException, ValorInvalidoException, EntidadDuplicadaException;
	void modificarReserva(ReservaDTO reserva )throws EntidadNoEncontradaException, DatoVacioException, ValorInvalidoException, EntidadDuplicadaException;
	void eliminarReserva(int numeroReserva) throws EntidadNoEncontradaException, ValorInvalidoException, ValorInvalidoException;
	ReservaDTO obtenerReserva(int numeroReserva) throws EntidadNoEncontradaException, FormatoInvalidoException, AccesoDatosException;
	List<ReservaDTO> obtenerListaReserva();
	List<HabitacionDTO> buscarDisponibilidad(LocalDate fechaEntrada, LocalDate fechaSalida) throws FechaInvalidaException, AccesoDatosException;
	void RegistrarPago(String tipoPago, int monto, LocalDate fechaPago, int idPago);
	float calcularCostoReserva(List<HabitacionDTO> habitaciones, LocalDate fechaEntrada, LocalDate fechaSalida, PlanDTO plan);
	

}
