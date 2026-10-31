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
import ar.edu.unrn.seminario.modelo.Cliente;
import ar.edu.unrn.seminario.modelo.Habitacion;
import ar.edu.unrn.seminario.modelo.Plan;
import ar.edu.unrn.seminario.modelo.Reserva;
import ar.edu.unrn.seminario.modelo.Servicio;

public interface IApi {

	/*void registrarUsuario(String username, String password, String email, String nombre, Integer rol);

	UsuarioDTO obtenerUsuario(String username);

	
	
	void eliminarUsuario(String username);

	List<RolDTO> obtenerRoles();

	List<RolDTO> obtenerRolesActivos();

	void guardarRol(Integer codigo, String descripcion, boolean estado); // crear el objeto de dominio �Rol�

	RolDTO obtenerRolPorCodigo(Integer codigo); // recuperar el rol almacenado

	void activarRol(Integer codigo); // recuperar el objeto Rol, implementar el comportamiento de estado.

	void desactivarRol(Integer codigo); // recuperar el objeto Rol, imp

	List<UsuarioDTO> obtenerUsuarios(); // recuperar todos los usuarios

	void activarUsuario(String username); // recuperar el objeto Usuario, implementar el comportamiento de estado.

	void desactivarUsuario(String username); // recuperar el objeto Usuario, implementar el comportamiento de estado.*/
				//verificar hacemos con el objeto como parametro 
	void registrarCliente(String nombre, String apellido, String contacto, int dni);
	void modificarCLiente(int dni);
	void eliminarCliente(int dni);
	boolean existeCliente(int dni);
	ClienteDTO obtenerCLiente(int dni);
	List<ClienteDTO>obtenerListaClientes();
	
	void registrarHabitacion(int numero, String tipoHabitacion, int costo, List<String> camas);
	boolean modificarHabitacion(int numero, int numeroNuevo, String tipoHabitacion, int costo, List<String> camas);
	boolean eliminarHabitacion(int numero);
	HabitacionDTO obtenerHabitacion(int numero);
	List <HabitacionDTO>obtenerListaHabitacion();
	
	void registrarServicio(String nombre,String descripcion, int precio);
	void modiicarServicio(String nombre);
	void eliminarServicio(String nombre);
	ServicioDTO obtenerServicio(String nombre);
	List<ServicioDTO> obtenerListaServicios();
	
	void registrarPlan(ArrayList<ServicioDTO> listaServicios, int costo, String nombre);
	void modificarPlan(String nombre);
	void eliminarPlan(String nombre);
	PlanDTO obtenerPlan(String nombre);
	List<PlanDTO> obtenerListaPlan();
	
	//
	void realizarReserva(List <HabitacionDTO>obtenerListaHabitacion, PlanDTO plan,ClienteDTO cliente, LocalDate fechaDeEntrada, LocalDate fechaSalida, PagoDTO pagoSeña, List<HuespedDTO> huespedes);
	void modificarReserva(int numeroReserva);
	void cancelarReserva(int numeroReserva);
	ReservaDTO obtenerReserva(int numeroReserva);
	List<ReservaDTO> obtenerListaReserva();
	void RegistrarPago(String tipoPago, int monto, LocalDate fechaPago, int idPago);
	
	
	
	
	
	
	
}
