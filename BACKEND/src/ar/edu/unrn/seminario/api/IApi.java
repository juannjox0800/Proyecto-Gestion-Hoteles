package ar.edu.unrn.seminario.api;

import java.util.List;

import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;

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
	
	void registrarHabitacion(int numero, String tipoHabitacion, int costo);
	void modificarHabitacion(int numero);
	void eliminarHabitacion(int numero);
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
