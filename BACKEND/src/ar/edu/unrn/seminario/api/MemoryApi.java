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
			String nombre, boolean esActivo) throws EntidadDuplicadaException, DatoVacioException, ValorInvalidoException{

		if (planExiste(nombre)) throw new EntidadDuplicadaException("Ya existe esta entidad");
		
		ArrayList<Servicio> servicios = new ArrayList<>();
		for (ServicioDTO dto : listaServicios) { 			
			servicios.add(HotelMapper.aModelo(dto));
		}
		
		Plan planadd= new Plan(servicios,costo ,nombre, esActivo);
		this.planes.add(planadd);
		
			
	}
	
	
	public boolean planExiste(String nombre) {
		boolean s =false;
	for (Plan a : this.planes) {
		if (a.getNombre().equals(nombre)) s = true;
	}
		
		return s;
		
	}

	@Override
	public void modificarPlan(ArrayList<ServicioDTO> listaServicios, int costo,String nombre,boolean esActivo) throws EntidadNoEncontradaException, ValorInvalidoException, DatoVacioException{
			if (!planExiste(nombre)) throw new EntidadNoEncontradaException("Plan no existe");
			if(costo<0)throw new ValorInvalidoException("valor no valido ");
			if(nombre.trim().isEmpty() || nombre==null)throw new DatoVacioException("coloca nombre");
			if(listaServicios==null||listaServicios.isEmpty())throw new DatoVacioException("selecciona almenos un servicio");
		ArrayList<Servicio> servicios = new ArrayList<>();
		for (ServicioDTO dto : listaServicios) {
			servicios.add(HotelMapper.aModelo(dto));
		for (Plan a : this.planes) {
			if (a.getNombre().equals(nombre)) {
				a.setServicios(servicios);
				a.setCosto(costo);
				a.setNombre(nombre);
				a.setEstadoActivo(esActivo);
			}}}
	}
	@Override
	public void eliminarPlan(String nombre) throws EntidadNoEncontradaException{
		if (!planExiste(nombre))throw new EntidadNoEncontradaException("Plan no existe"); 
			this.planes.removeIf(p -> p.getNombre().equals(nombre));
	}

	@Override
	public PlanDTO obtenerPlan(String nombre) throws EntidadNoEncontradaException{
		if (!planExiste(nombre))
			for(Plan p : this.planes) {
				if (p.getNombre().equals(nombre)) {
					return HotelMapper.aDTO(p);
				}
			
		}
		throw new EntidadNoEncontradaException("Plan no existe");

		
	}
	@Override
	public void guardarListaPLan(List <PlanDTO> listaNueva) throws DatoVacioException, ValorInvalidoException{
		List<Plan> listaGuardar= new ArrayList<>();
		for(PlanDTO a: listaNueva) {
			listaGuardar.add(HotelMapper.aModelo(a));
		}
		this.planes.clear();
		this.planes.addAll(listaGuardar);
	}

	@Override
	public List<PlanDTO> obtenerListaPlan() {
		ArrayList <PlanDTO> planesDTO=new ArrayList<>();
		for (Plan p: this.planes) {
			planesDTO.add(HotelMapper.aDTO(p));
		}
		return planesDTO;
	}
	
	
	@Override
	public void realizarReserva(List<HabitacionDTO> obtenerListaHabitacion, PlanDTO plan, ClienteDTO cliente,
			LocalDate fechaDeEntrada, LocalDate fechaSalida, PagoDTO pagoSeña, List<HuespedDTO> huespedes) {
		
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
	
}
