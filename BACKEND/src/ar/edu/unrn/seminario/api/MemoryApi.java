package ar.edu.unrn.seminario.api;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import ar.edu.unrn.seminario.dto.ClienteDTO;
import ar.edu.unrn.seminario.dto.HabitacionDTO;
import ar.edu.unrn.seminario.dto.HuespedDTO;
import ar.edu.unrn.seminario.dto.PagoDTO;
import ar.edu.unrn.seminario.dto.PlanDTO;
import ar.edu.unrn.seminario.dto.ReservaDTO;
import ar.edu.unrn.seminario.dto.ServicioDTO;
import ar.edu.unrn.seminario.modelo.Cliente;
import ar.edu.unrn.seminario.modelo.Habitacion;
import ar.edu.unrn.seminario.modelo.Huesped;
import ar.edu.unrn.seminario.modelo.Pago;
import ar.edu.unrn.seminario.modelo.Plan;
import ar.edu.unrn.seminario.modelo.Reserva;
import ar.edu.unrn.seminario.modelo.Servicio;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.FechaInvalidaException;
import ar.edu.unrn.seminario.exception.FormatoInvalidoException;
import ar.edu.unrn.seminario.mapper.HotelMapper;



public class MemoryApi implements IApi {
	private List<Cliente> clientes = new ArrayList<>();
	private List<Habitacion> habitaciones = new ArrayList<>();
	private List<Plan> planes = new ArrayList<>();
	private List<Reserva> reservas = new ArrayList<>(); 
	private List<Servicio> servicios = new ArrayList<>();
	
    private int contReservas = 0;
	
	@Override
	public void registrarCliente(String nombre, String apellido, String contacto, int dni) {
	
	}


	@Override
	public void modificarCLiente(int dni) {
		for (Cliente a : clientes) {
			
		}
	}

	@Override
	public void eliminarCliente(int dni) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existeCliente(int dni) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ClienteDTO  obtenerCliente(int dni) throws EntidadNoEncontradaException, FormatoInvalidoException {   
		if (dni <= 0) {
		    throw new FormatoInvalidoException("El DNI ingresado no es válido");
		    }
		for (Cliente cliente : clientes) {
			if (cliente.getDni() == dni) 
				return HotelMapper.aDTO(cliente);
			}
		 throw new EntidadNoEncontradaException("El cliente no existe en el sistema");
	}

	@Override
	public List<ClienteDTO> obtenerListaClientes() {
		// TODO Auto-generated method stub
		return null;
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
			LocalDate fechaDeEntrada, LocalDate fechaSalida, PagoDTO pagoSeña, int costoTotal) {
		
				//private boolean estadoCancelacion;
		
		
		List<Habitacion> habitaciones = new ArrayList<>();

		for (HabitacionDTO habitacionDTO : obtenerListaHabitacion) {
		    Habitacion habitacion = HotelMapper.aModelo(habitacionDTO);  
		    habitaciones.add(habitacion);
		}

		
		Plan planModelo = HotelMapper.aModelo(plan);
		Cliente clienteModelo = HotelMapper.aModelo(cliente);
		Pago pagoModelo = HotelMapper.aModelo(pagoSeña);
		
		int numeroReserva = contReservas;
		contReservas ++;
		Reserva reserva = new Reserva(habitaciones, planModelo, clienteModelo, fechaDeEntrada,
				                      fechaSalida, pagoModelo, numeroReserva);
		
		costoTotal = reserva.getCostoTotal() + costoTotal;
		reserva.setCostoTotal(costoTotal);
		
		reservas.add(reserva);
		
	}

	@Override
	public void modificarReserva(int numeroReserva) { // falta la ventana ??? TERMINAR METODO excepcion la reserva esta cancelada/no existe
		obtenerReserva(numeroReserva);
		       
	}

	@Override
	public void cancelarReserva(int numeroReserva) throws EntidadNoEncontradaException { //  falta la ventana ??? 
		for (Reserva reserva : reservas) {
			if (reserva.getNumeroReserva() == numeroReserva) {
				reserva.cambiarEstadoCancelacion();
			}
			 throw new EntidadNoEncontradaException("No se encontro la reserva");
		}
		
	}

	@Override
	public ReservaDTO obtenerReserva(int numeroReserva) throws EntidadNoEncontradaException{ // busca y devuelve un DTO de una reserva
		for (Reserva reserva : reservas) {
			if (reserva.getNumeroReserva() == numeroReserva) {
				return HotelMapper.aDTO(reserva);
				}
		    }
		 throw new EntidadNoEncontradaException("No se encontro la reserva");
		}
		

	@Override
	public List<ReservaDTO> obtenerListaReserva() { // devuelve la lista DTO de reservas 
		List<ReservaDTO> listaDTO = new ArrayList<>();
		for (Reserva reserva : reservas) {
	        ReservaDTO reservaDTO = HotelMapper.aDTO(reserva);
	        listaDTO.add(reservaDTO);
	    }
		return listaDTO;
	}
	
	@Override
	public List<HabitacionDTO> buscarDisponibilidad(LocalDate fechaEntrada, LocalDate fechaSalida) throws FechaInvalidaException { 
		
		if (!fechaEntrada.isBefore(fechaSalida)){
			throw new FechaInvalidaException("la fecha de entrada debe ser anterior a la fecha de salida");
		}
		
		List<Habitacion> habitacionesOcupadas = reservas.stream() 
											.filter(reserva -> !reserva.isEstadoCancelacion())
											.filter(reserva -> fechaEntrada.isBefore(reserva.getFechaSalida()) &&
								                       fechaSalida.isAfter(reserva.getFechaEntrada()))
											.flatMap (reserva -> reserva.getHabitaciones().stream())
											.collect(Collectors.toList());
											
	   List <HabitacionDTO> habitacionesDisponibles = habitaciones.stream().filter (habitacion -> !habitacionesOcupadas.contains(habitacion))
			   									  .map(habitacion -> HotelMapper.aDTO(habitacion))
			   									  .collect(Collectors.toList());
	   
	   return habitacionesDisponibles;
	}
	
	@Override
	public PlanDTO buscarPlan(String nombrePlan) throws EntidadNoEncontradaException {
	    PlanDTO plan = null;
		for (Plan p : planes) {
	        if (p.getNombre().equals(nombrePlan)) {
	            plan = HotelMapper.aDTO(p);
	            break;
	            }
	        } 
		
		if (plan == null) {
			throw new EntidadNoEncontradaException("Plan no encontrado");
			}
		
		if (!plan.isEstadoActivo()) {
			throw new EntidadNoEncontradaException("el plan no esta activo");
			}
		return plan;
	}
	
	@Override
	public int calcularSeña(int costoHabitaciones, LocalDate fechaEntrada, LocalDate fechaSalida) { //contar los fakin dias
		Period periodo = Period.between(fechaEntrada, fechaSalida);
		int dias = periodo.getDays();
		return costoHabitaciones * dias / 2;
		}

	

	@Override
	public void RegistrarPago(String tipoPago, int monto, LocalDate fechaPago, int idPago) {
		// TODO Auto-generated method stub
		
	}

}
