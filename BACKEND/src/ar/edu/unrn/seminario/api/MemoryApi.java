package ar.edu.unrn.seminario.api;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ar.edu.unrn.seminario.dto.ClienteDTO;
import ar.edu.unrn.seminario.dto.HabitacionDTO;
import ar.edu.unrn.seminario.dto.PagoDTO;
import ar.edu.unrn.seminario.dto.PlanDTO;
import ar.edu.unrn.seminario.dto.ReservaDTO;
import ar.edu.unrn.seminario.dto.ServicioDTO;
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadDuplicadaException;
import ar.edu.unrn.seminario.exception.EntidadEnUsoException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.FechaInvalidaException;
import ar.edu.unrn.seminario.exception.FormatoInvalidoException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;


import ar.edu.unrn.seminario.mapper.HotelMapper;
import ar.edu.unrn.seminario.modelo.Cama;
import ar.edu.unrn.seminario.modelo.Cliente;
import ar.edu.unrn.seminario.modelo.Habitacion;
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

    private int contReservas = 1;
    private int ultimoIdPago = 1;


	@Override
	public void registrarCliente(String nombre, String apellido, String contacto, int dni) throws EntidadDuplicadaException, DatoVacioException, ValorInvalidoException{
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
	public ClienteDTO  obtenerCliente(int dni) throws EntidadNoEncontradaException, FormatoInvalidoException {
		if (dni <= 0) {
		    throw new FormatoInvalidoException("el DNI no es valido");
		    }
		for (Cliente cliente : clientes) {
			if (cliente.getDni() == dni) {
				return HotelMapper.aDTO(cliente);
			}
			}
		 throw new EntidadNoEncontradaException("El cliente no existe en el sistema");
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
	public void registrarServicio(String nombre, String descripcion, float precio, boolean estadoVigente) throws EntidadDuplicadaException, ValorInvalidoException, DatoVacioException {
		boolean existe = this.servicios
				.stream()
				.anyMatch(s -> s.getNombre().equalsIgnoreCase(nombre));

		if (existe) {
			throw new EntidadDuplicadaException("Ya existe un servicio con el nombre: " + nombre);
		}

		this.servicios.add(new Servicio(nombre, descripcion, precio, estadoVigente));

	}

	@Override
	public void modificarServicio(String nombre, String descripcion, float precio, boolean estadoVigente) throws EntidadNoEncontradaException, ValorInvalidoException {
		Servicio servicio = this.servicios
				.stream()
				.filter(s -> s.getNombre().equalsIgnoreCase(nombre))
				.findFirst()
				.orElseThrow(() -> new EntidadNoEncontradaException("No se encontro el servicio: " + nombre)) ;

		servicio.setDescripcion(descripcion);
		servicio.setPrecio(precio);
		servicio.setEstadoVigente(estadoVigente);	}

	@Override
	public void eliminarServicio(String nombre) throws EntidadNoEncontradaException, EntidadEnUsoException{
		boolean enUso = this.planes.stream()
							.anyMatch(p -> p.getServicios().stream()
											.anyMatch(s -> s.getNombre().equalsIgnoreCase(nombre)));
		
		if (enUso) {
			throw new EntidadEnUsoException("No se puede eliminar el servicio '" + nombre + "' porque esta incluido en un plan");
		}
		
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
	public void registrarPlan(ArrayList<ServicioDTO> listaServicios, float costo,	String nombre, boolean esActivo) throws EntidadDuplicadaException, DatoVacioException, ValorInvalidoException{
		boolean existe = this.planes
				.stream()
				.anyMatch(s -> s.getNombre().equalsIgnoreCase(nombre));

		if (existe) {
			throw new EntidadDuplicadaException("Ya existe un plan con el nombre: " + nombre);
		}
		ArrayList<Servicio>servicios=new ArrayList<>();
		for (ServicioDTO a : listaServicios) {
			servicios.add(HotelMapper.aModelo(a));
		}
		this.planes.add(new Plan(servicios, costo, nombre, esActivo));
			
	}
	
	
	@Override
	public void modificarPlan(ArrayList<ServicioDTO> listaServicios, float costo,String nombre,boolean esActivo) throws EntidadNoEncontradaException, ValorInvalidoException, DatoVacioException, EntidadDuplicadaException{
		Plan plan = this.planes.stream().filter(s-> s.getNombre().equalsIgnoreCase(nombre)).findFirst().orElseThrow(()-> new EntidadNoEncontradaException ("no se encontro el plan" + nombre));
		List <Servicio> servicios = new ArrayList<>();

		for (ServicioDTO a : listaServicios) {
			servicios.add(HotelMapper.aModelo(a));
		}
		plan.setServicios(servicios);
		plan.setCosto(costo);
		plan.setNombre(nombre);
		plan.setEstadoActivo(esActivo);

	}
	@Override
	public void eliminarPlan(String nombre) throws EntidadNoEncontradaException{
			boolean eliminado = this.planes.removeIf(h -> h.getNombre().equalsIgnoreCase(nombre));		
			if (!eliminado) {
				throw new EntidadNoEncontradaException("No se ha encontrado el plan.");
				}
	}

	@Override
	public PlanDTO obtenerPlan(String nombre) throws EntidadNoEncontradaException{
		Optional<Plan> plan = planes.stream().filter(h -> h.getNombre().equalsIgnoreCase(nombre)).findFirst();		
		if(plan.isPresent()) {
			PlanDTO dto = HotelMapper.aDTO(plan.get());
			return dto;
		}else {
			throw new EntidadNoEncontradaException("No se ha encontrado el plan.");
		}
		
	}
	public float calcPrecioServicios(ArrayList<ServicioDTO> listaServicios) {
		float precioRecom=0;
		for (ServicioDTO a : listaServicios) {
			precioRecom+=a.getPrecio();
		}
		precioRecom-=precioRecom/10;
		return precioRecom;
	}
	public List<PlanDTO> obtenerListaPlan(){
		List<PlanDTO> planes = new ArrayList<>();
		for (Plan a : this.planes) {
			planes.add(HotelMapper.aDTO(a));
		}
		return planes;
	}

	@Override
	public void realizarReserva(List<HabitacionDTO> obtenerListaHabitacion, PlanDTO plan, ClienteDTO cliente,
			LocalDate fechaDeEntrada, LocalDate fechaSalida, PagoDTO pagoSeña, float costoTotal) throws DatoVacioException, ValorInvalidoException, EntidadDuplicadaException{

				//private boolean estadoCancelacion;
		List<Habitacion> habitaciones = new ArrayList<>();

		for (HabitacionDTO habitacionDTO : obtenerListaHabitacion) {
		    Habitacion habitacion = HotelMapper.aModelo(habitacionDTO);
		    habitaciones.add(habitacion);
		}


		Plan planModelo = HotelMapper.aModelo(plan);
		Cliente clienteModelo = HotelMapper.aModelo(cliente);
		Pago pagoModelo = HotelMapper.aModelo(pagoSeña);
		pagoModelo.setIdPago(ultimoIdPago);
		ultimoIdPago++;

		int numeroReserva = contReservas;
		contReservas ++;
		Reserva reserva = new Reserva(habitaciones, planModelo, clienteModelo, fechaDeEntrada,
				                      fechaSalida, pagoModelo, numeroReserva);

		costoTotal = reserva.getCostoTotal() + costoTotal;
		reserva.setCostoTotal(costoTotal);

		reservas.add(reserva);

	}

	@Override
	public void modificarReserva(ReservaDTO reserva )
			throws EntidadNoEncontradaException, DatoVacioException, ValorInvalidoException, EntidadDuplicadaException{
		Reserva reservaEncontrada = null;
		for (Reserva r : reservas) {
			if (r.getNumeroReserva() == reserva.getNumeroReserva()) {
				reservaEncontrada = r;
				break;
			}
		}
		// siempre se va a encontrar pq se selecciona de la lista de reservas
		if (reservaEncontrada == null) {
		    throw new EntidadNoEncontradaException("No se encontró la reserva.");
		}
	     // NO EMPEZO
		LocalDate hoy =  LocalDate.now();
		if (hoy.isBefore(reserva.getFechaEntrada())) {

			List<Habitacion> habitaciones = new ArrayList<>();
			for (HabitacionDTO h : reserva.getHabitaciones()) {
			    habitaciones.add(HotelMapper.aModelo(h));
			}

			reservaEncontrada.setHabitaciones(habitaciones);
			reservaEncontrada.setPlan(HotelMapper.aModelo(reserva.getPlan()));
			reservaEncontrada.setFechaEntrada(reserva.getFechaEntrada());
			reservaEncontrada.setFechaSalida(reserva.getFechaSalida());
			reservaEncontrada.setCostoTotal(reserva.getCostoTotal());  // este seria asi?

		}// EN CURSO, solo puede cambiar el plan por uno mayor
		else if (!hoy.isBefore(reserva.getFechaEntrada()) && hoy.isBefore(reserva.getFechaSalida())) {
			if (reserva.getPlan().getCosto() <= reservaEncontrada.getPlan().getCosto()) {
				throw new ValorInvalidoException ("El plan selecionado debe ser de mayor precio que el plan actual.");
			}
			reservaEncontrada.setCostoTotal(reserva.getCostoTotal());
			reservaEncontrada.setPlan(HotelMapper.aModelo(reserva.getPlan()));
		}// TERMINO
		else {
			throw new ValorInvalidoException ("No se puede modificar una reserva que ya finalizo.");
			}
		}



	@Override
	public void eliminarReserva(int numeroReserva) throws EntidadNoEncontradaException{ //  no lanza excepciones pq al llamar al metodo ya se verificaron en el momento de buscarReserva (?

		boolean eliminado = this.reservas.removeIf(r -> r.getNumeroReserva() == numeroReserva);
		
		if (!eliminado) {
			throw new EntidadNoEncontradaException();
		}
	}

	@Override
	public ReservaDTO obtenerReserva(int numeroReserva) throws EntidadNoEncontradaException, FormatoInvalidoException{ // busca y devuelve un DTO de una reserva
		if (numeroReserva < 0) {
		    throw new FormatoInvalidoException("el numero no es valido");
		    }
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
	public float calcularCostoReserva(List<HabitacionDTO> habitaciones, LocalDate fechaEntrada, LocalDate fechaSalida, PlanDTO plan){ //contar los fakin dias
		int costoHabitaciones = habitaciones.stream() .mapToInt (habitacion -> habitacion.getCosto()) .sum();

		long dias = Duration.between(fechaEntrada.atStartOfDay(), fechaSalida.atStartOfDay()).toDays();
		
		return (costoHabitaciones + plan.getCosto())* (float) dias; //un plan tiene un costo fijo, independiente de la cantidad de dias????
		}



	@Override
	public void RegistrarPago(String tipoPago, int monto, LocalDate fechaPago, int idPago) {
		// TODO Auto-generated method stub

	}
}
