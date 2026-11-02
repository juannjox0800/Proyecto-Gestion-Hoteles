package ar.edu.unrn.seminario.api;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

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
import ar.edu.unrn.seminario.mapper.HotelMapper;


import ar.edu.unrn.seminario.modelo.Cama;

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
	public ClienteDTO obtenerCLiente(int dni) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClienteDTO> obtenerListaClientes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registrarHabitacion(int numero, String tipoHabitacion, String estado, int costo, List<String> camas) throws EntidadDuplicadaException, DatoVacioException, ValorInvalidoException     
	{
		boolean existe = this.habitaciones.stream().anyMatch(h -> h.getNumero() == numero);
		
		if(existe) {
			throw new EntidadDuplicadaException("Ya existe una habitación con el número:" + numero);
		}
		
		Habitacion h = new Habitacion(numero, tipoHabitacion, costo);
		h.setEstado(estado);
		
		for(String cama : camas ) {
			h.agregarCama(new Cama(cama));
		}
		
		habitaciones.add(h);
	}

	@Override
	public boolean modificarHabitacion(int numero, int numeroNuevo, String tipoHabitacion, String estado, int costo, List<String> camas) throws DatoVacioException, EntidadNoEncontradaException {
		Optional<Habitacion> habitacion = habitaciones.stream().filter(h -> h.getNumero() == numero ).findFirst();
		
		if(habitacion.isPresent()) {
			Habitacion h = habitacion.get();
			h.setNumero(numeroNuevo);
			h.setTipoHabitacion(tipoHabitacion);
			h.setEstado(estado);
			h.setCosto(costo);
			
			h.getCamas().clear();
			for(String cama : camas ) {
				h.agregarCama(new Cama(cama));
			}
			
			return true;
		}else {
			throw new EntidadNoEncontradaException("No se encontró la habitación con el número:" + numero);
		}
		
	}

	@Override
	public boolean eliminarHabitacion(int numero) throws EntidadNoEncontradaException
	{
		boolean eliminado = this.habitaciones.removeIf(h -> h.getNumero() == numero);
		
		if (!eliminado) {
			throw new EntidadNoEncontradaException("No se ha encontrado la habitación.");
		}
		
		return true;
	}

	@Override
	public HabitacionDTO obtenerHabitacion(int numero) throws EntidadNoEncontradaException {
		Optional<Habitacion> habitacion = habitaciones.stream().filter(h -> h.getNumero() == numero ).findFirst();
		
		if(habitacion.isPresent()) {
			HabitacionDTO dto = HotelMapper.aDTO(habitacion.get());
			return dto;
		}else {
			throw new EntidadNoEncontradaException("No se ha encontrado la habitación.");
		}
		
	
	}

	@Override
	public List<HabitacionDTO> obtenerListaHabitacion() {
		List<HabitacionDTO> listaDTO = new ArrayList<>();
		
		for(Habitacion h : habitaciones) {
			
			HabitacionDTO dto = HotelMapper.aDTO(h);
			
			listaDTO.add(dto);
		}
		
		return listaDTO;
	}
	

	@Override
	public void registrarServicio(String nombre, String descripcion, int precio, boolean estadoVigente) throws EntidadDuplicadaException, ValorInvalidoException, DatoVacioException {
		boolean existe = this.servicios
				.stream()
				.anyMatch(s -> s.getNombre().equalsIgnoreCase(nombre));

		if (existe) {
			throw new EntidadDuplicadaException("Ya existe un servicio con el nombre: " + nombre);
		}

		this.servicios.add(new Servicio(nombre, descripcion, precio, estadoVigente));

		
	}

	@Override
	public void modificarServicio(String nombre, String descripcion, int precio, boolean estadoVigente) throws EntidadNoEncontradaException {
		Servicio servicio = this.servicios
				.stream()
				.filter(s -> s.getNombre().equalsIgnoreCase(nombre))
				.findFirst()
				.orElseThrow(() -> new EntidadNoEncontradaException("No se encontro el servicio: " + nombre)) ;

		servicio.setDescripcion(descripcion);
		servicio.setPrecio(precio);
		servicio.setEstadoVigente(estadoVigente);	}

	@Override
	public void eliminarServicio(String nombre) throws EntidadNoEncontradaException{
		boolean fueEliminado = this.servicios.removeIf(s -> s.getNombre().equalsIgnoreCase(nombre));
		
		if (!fueEliminado) {
			throw new EntidadNoEncontradaException("No se encontro el servicio '" + nombre + "' para eliminar");
		}

	}

	@Override
	public ServicioDTO obtenerServicio(String nombre) throws EntidadNoEncontradaException {
		Servicio servicio =  this.servicios
				.stream()
				.filter(s -> s.getNombre().equalsIgnoreCase(nombre))
				.findFirst()
				.orElseThrow(() -> new EntidadNoEncontradaException("No se encontro el servicio: " + nombre));
		
		
		return HotelMapper.aDTO(servicio);
	}

	@Override
	public List<ServicioDTO> obtenerListaServicios() {
		
		return this.servicios
					.stream()
					.map(HotelMapper :: aDTO)
					.collect(Collectors.toList());
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
