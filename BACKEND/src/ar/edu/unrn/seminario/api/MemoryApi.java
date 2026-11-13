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
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadDuplicadaException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;
import ar.edu.unrn.seminario.mapper.HotelMapper;
import ar.edu.unrn.seminario.modelo.Cliente;
import ar.edu.unrn.seminario.modelo.Habitacion;
import ar.edu.unrn.seminario.modelo.Huesped;
import ar.edu.unrn.seminario.modelo.Pago;
import ar.edu.unrn.seminario.modelo.Plan;
import ar.edu.unrn.seminario.modelo.Reserva;
import ar.edu.unrn.seminario.modelo.Servicio;

public class MemoryApi implements IApi {
	private List<Cliente> clientes = new ArrayList<>();
	private List<Habitacion> habitaciones = new ArrayList<>();
	private List<Plan> planes = new ArrayList<>();
	private List<Reserva> reservas = new ArrayList<>(); 
	private List<Servicio> servicios = new ArrayList<>();
	
    private int contReservas = 0;
	
	@Override
	public void registrarCliente(String nombre, String apellido, String contacto, int dni) throws EntidadDuplicadaException{
		if (existeCliente(dni)) {
			throw new EntidadDuplicadaException("El cliente ya existe");
		}
			
	Cliente cliente= new Cliente (nombre,apellido,contacto,dni);
	this.clientes.add(cliente);
	}


	@Override
	public void modificarCLiente(int dni, String nombre, String apellido, String contacto) throws EntidadNoEncontradaException, DatoVacioException,ValorInvalidoException {
		Cliente cliente = this.clientes.stream()
	            .filter(c -> c.getDni() == dni)
	            .findFirst()
	            .orElseThrow(() -> new EntidadNoEncontradaException("El cliente a modificar no existe en el sistema"));
	    
	    
	    cliente.setNombre(nombre);
	    cliente.setApellido(apellido);
	    cliente.setContacto(contacto);
	}
	@Override
	public void eliminarCliente(int dni) throws EntidadNoEncontradaException{
		if (!existeCliente(dni)) {
			throw new EntidadNoEncontradaException("El cliente no existe. No se puede eliminar");
		}
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
	public ClienteDTO obtenerCliente(int dni) throws EntidadNoEncontradaException {
		for (Cliente a : this.clientes) {
			if (a.getDni()== dni) {
				return HotelMapper.aDTO(a);			}
		}
		throw new EntidadNoEncontradaException ("No se encontró el cliente con el DNI: " + dni);
	}
	

	@Override
	public List<ClienteDTO> obtenerListaClientes() {
		List<ClienteDTO> dtos= new ArrayList<>();
		for (Cliente a:this.clientes) {
			dtos.add(HotelMapper.aDTO(a));
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
		
		List<Habitacion> habitaciones = new ArrayList<>();

		for (HabitacionDTO habitacionDTO : obtenerListaHabitacion) {
		    Habitacion habitacion = HabitacionMapper.toEntity(habitacionDTO);  // Usa la clase mapper que todavia no se implemento
		    habitaciones.add(habitacion);
		}
		
		List<Huesped> listaHuespedes = new ArrayList<>();

		for (HuespedDTO huespedDTO : huespedes) {
		    Huesped huesped = HuespedMapper.toEntity(huespedDTO);  // Usa la clase mapper que todavia no se implemento
		    listaHuespedes.add(huesped);
		}
		
		Plan planModelo = PlanMapper.toEntity(plan);
		Cliente clienteModelo = ClienteMapper.toEntity(cliente);
		Pago pagoModelo = PagoMapper.toEntity(pagoSeña);
		
		int numeroReserva = contReservas;
		contReservas ++;
		Reserva reserva = new Reserva(habitaciones, planModelo, clienteModelo, fechaDeEntrada,
				                      fechaSalida, pagoModelo, numeroReserva, listaHuespedes);
		
		reservas.add(reserva);
		
	}

	@Override
	public void modificarReserva(int numeroReserva) {
		for (Reserva reserva : reservas) {
		    if (reserva.getNumeroReserva() == numeroReserva) {
		       
		    }
		}
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
/////////////
/// /////////////
///
/// //
/// 
/// 
/// 
/// 
/// 
/// 
/// 
/// 
/// 
/// 
/// 
/// 
	
}
