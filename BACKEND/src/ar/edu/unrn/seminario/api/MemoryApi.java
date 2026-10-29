package ar.edu.unrn.seminario.api;

import java.time.*;
import java.util.*;
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

public class MemoryApi implements IApi {
	private List<Cliente> clientes = new ArrayList<>();
	private List<Habitacion> habitaciones = new ArrayList<>();
	private List<Plan> planes = new ArrayList<>();
	private List<Reserva> reservas = new ArrayList<>(); 
	private List<Servicio> servicios = new ArrayList<>();
	
	@Override
	public void registrarCliente(String nombre, String apellido, String contacto, int dni) {
		
	Cliente cliente= new Cliente (nombre,apellido,contacto,dni);
	this.clientes.add(cliente);
	}


	@Override
	public void modificarCLiente(int dni, String nombre, String apellido, String contacto) {
		for (Cliente a : this.clientes) {
			if (a.getDni()== dni) {
				a.setNombre(nombre);
				a.setApellido(apellido);
				a.setContacto(contacto);
			}
		}
	}

	@Override
	public void eliminarCliente(int dni) {
		// TODO Auto-generated method stub
		this.clientes.removeIf(a-> a.getDni()== dni);
	}

	@Override
	public boolean existeCliente(int dni) {
		
		for (Cliente a : this.clientes) {
			if (a.getDni()== dni) {
				return true;
			}
		}
		return false;
		}
	@Override
	public ClienteDTO obtenerCliente(int dni) {
		for (Cliente a : this.clientes) {
			if (a.getDni()== dni) {
				return new ClienteDTO(a.getNombre(), a.getApellido(), a.getContacto(),a.getDni());
			}
		}
		return null;
	}
	

	@Override
	public List<ClienteDTO> obtenerListaClientes() {
		List<ClienteDTO> dtos= new ArrayList<>();
		for (Cliente a:this.clientes) {
			dtos.add(new ClienteDTO (a.getNombre(),a.getApellido(),a.getContacto(),a.getDni()));
		}
		return dtos;
	}

	@Override
	public void registrarHabitacion(int numero, String tipoHabitacion, int costo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificarHabitacion(int numero) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarHabitacion(int numero) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HabitacionDTO obtenerHabitacion(int numero) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HabitacionDTO> obtenerListaHabitacion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registrarServicio(String nombre, String descripcion, int precio) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modiicarServicio(String nombre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarServicio(String nombre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServicioDTO obtenerServicio(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ServicioDTO> obtenerListaServicios() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registrarPlan(ArrayList<ServicioDTO> listaServicios, int costo,
			String nombre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificarPlan(String nombre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarPlan(String nombre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PlanDTO obtenerPlan(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PlanDTO> obtenerListaPlan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void realizarReserva(List<HabitacionDTO> obtenerListaHabitacion, PlanDTO plan, ClienteDTO cliente,
			LocalDate fechaDeEntrada, LocalDate fechaSalida, PagoDTO pagoSeña, List<HuespedDTO> huespedes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificarReserva(int numeroReserva) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelarReserva(int numeroReserva) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ReservaDTO obtenerReserva(int numeroReserva) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReservaDTO> obtenerListaReserva() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void RegistrarPago(String tipoPago, int monto, LocalDate fechaPago, int idPago) {
		// TODO Auto-generated method stub
		
	}


	
}
