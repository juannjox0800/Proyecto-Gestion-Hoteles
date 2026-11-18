package ar.edu.unrn.seminario.api;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



import ar.edu.unrn.seminario.accesos.HabitacionDao;
import ar.edu.unrn.seminario.accesos.HabitacionDAOJDBC;

import ar.edu.unrn.seminario.accesos.PlanDAO;
import ar.edu.unrn.seminario.accesos.PlanDAOJDBC;
import ar.edu.unrn.seminario.accesos.ServicioDAO;
import ar.edu.unrn.seminario.accesos.ServicioDAOJDBC;

import ar.edu.unrn.seminario.accesos.ClienteDAO;
import ar.edu.unrn.seminario.accesos.ClienteDAOJDBC;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import ar.edu.unrn.seminario.accesos.ReservaDAO;
import ar.edu.unrn.seminario.accesos.ReservaDAOJDBC;

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
import ar.edu.unrn.seminario.exception.FechaInvalidaException;
import ar.edu.unrn.seminario.exception.FormatoInvalidoException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;
import ar.edu.unrn.seminario.mapper.HotelMapper;
import ar.edu.unrn.seminario.modelo.Cliente;


import ar.edu.unrn.seminario.modelo.Cama;
import ar.edu.unrn.seminario.modelo.Habitacion;
import ar.edu.unrn.seminario.modelo.Plan;
import ar.edu.unrn.seminario.modelo.Servicio;

import ar.edu.unrn.seminario.modelo.Habitacion;
import ar.edu.unrn.seminario.modelo.Pago;
import ar.edu.unrn.seminario.modelo.Plan;
import ar.edu.unrn.seminario.modelo.Reserva;


public class PersistenceApi implements IApi{
	
	private HabitacionDao habitacionDao;
	private PlanDAO planDao;
	private ServicioDAO servicioDao;

private ClienteDAO clienteDao; 

	public PersistenceApi() {
		planDao = new PlanDAOJDBC();
		servicioDao = new ServicioDAOJDBC();
		habitacionDao = new HabitacionDAOJDBC();
		clienteDao = new ClienteDAOJDBC();
	}
	
	@Override
	public void registrarCliente(String nombre, String apellido, String contacto, int dni) throws EntidadDuplicadaException, DatoVacioException, ValorInvalidoException, AccesoDatosException {

		Cliente cliente=  new Cliente (nombre, apellido,contacto, dni);
		this.clienteDao.create(cliente);
	}

	@Override
	public void modificarCLiente(int dni, String nombre, String apellido, String contacto) throws EntidadNoEncontradaException, DatoVacioException, ValorInvalidoException, AccesoDatosException{
			// TODO Auto-generated method stub
			Cliente cliente = clienteDao.find(dni);
			cliente.setApellido(apellido);
			cliente.setContacto(contacto);
			cliente.setNombre(nombre);
			 clienteDao.update(cliente);
		}


	@Override
	public void eliminarCliente(int dni) throws EntidadNoEncontradaException, AccesoDatosException, EntidadEnUsoException {
		clienteDao.remove(dni);
		
	}

	@Override
	public boolean existeCliente(int dni) throws DatoVacioException, ValorInvalidoException, AccesoDatosException {
		try {
			clienteDao.find(dni);
			return true;
		} catch (EntidadNoEncontradaException e) {
			return false;
		}	

	}

	@Override
	public ClienteDTO obtenerCliente(int dni) throws EntidadNoEncontradaException, FormatoInvalidoException, DatoVacioException, ValorInvalidoException, AccesoDatosException {
		Cliente cliente = clienteDao.find(dni);
		return HotelMapper.aDTO(cliente);
	}

	@Override
	public List<ClienteDTO> obtenerListaClientes() throws ValorInvalidoException, DatoVacioException, AccesoDatosException {
		List<Cliente> clientes = clienteDao.findAll();
		List<ClienteDTO> clientesDTO = new ArrayList<>();
		for (Cliente c : clientes) {
			clientesDTO.add(HotelMapper.aDTO(c));
		}
		return clientesDTO;

	}
	

	// ------------------------------------------------------------- HABITACIONES

	@Override
	public void registrarHabitacion(int numero, String tipoHabitacion, String estado, float costo, List<String> camas)throws EntidadDuplicadaException, DatoVacioException, ValorInvalidoException, AccesoDatosException {
		Habitacion h = habitacionDao.find(numero);
		if(h ==null) {
			h = new Habitacion(numero, tipoHabitacion, costo);
			h.setEstado(estado);
			
			for(String cama : camas) {
				h.agregarCama(new Cama(cama));
			}
			
			this.habitacionDao.create(h);
	
		}
		else {
			throw new EntidadDuplicadaException("Ya existe una habitacion con ese numero en el sistema. Intente con otro numero.");
			
		}
	}

	@Override
	public boolean modificarHabitacion(int numero, String tipoHabitacion, String estado, float costo, List<String> camas) throws DatoVacioException, EntidadNoEncontradaException, ValorInvalidoException, AccesoDatosException {
		Habitacion h = habitacionDao.find(numero);
		
		if(h!= null) {
			
			h.setTipoHabitacion(tipoHabitacion);
			h.setCosto(costo);
			h.setEstado(estado);
			h.getCamas().clear();
			
			for(String cama : camas) {
				h.agregarCama(new Cama(cama));
			}
			
			this.habitacionDao.update(h);
			return true;
			
		} else 
			throw new EntidadNoEncontradaException("No se ha encontrado la habitación.");
		
	}

	@Override
	public boolean eliminarHabitacion(int numero) throws EntidadNoEncontradaException, AccesoDatosException, DatoVacioException, ValorInvalidoException {
		Habitacion h = habitacionDao.find(numero);
		
		if(h!= null) {
			
			habitacionDao.remove(h);
			return true;
			
		} else 
			throw new EntidadNoEncontradaException("No se ha encontrado la habitación.");
			
	}

	@Override
	public HabitacionDTO obtenerHabitacion(int numero) throws EntidadNoEncontradaException, AccesoDatosException, DatoVacioException, ValorInvalidoException {
		Habitacion h = habitacionDao.find(numero);
		
		if(h!= null) {
			
			HabitacionDTO dtoH = HotelMapper.aDTO(h);
			return dtoH;
			
		} else
			throw new EntidadNoEncontradaException("No se ha encontrado la habitación.");
		
	}

	//desde el Dao llamo a findAll y lo paso a stream, de ahi hago el mappeo a DTO y lo devuelvo en forma de lista usando  collect
	@Override

	public List<HabitacionDTO> obtenerListaHabitacion() throws AccesoDatosException, DatoVacioException, ValorInvalidoException {
		
		return this.habitacionDao.findAll().stream().map(HotelMapper::aDTO).collect(Collectors.toList());
	}
		
	@Override
	public void registrarServicio(String nombre, String descripcion, float precio, boolean estadoVigente)
			throws EntidadDuplicadaException, ValorInvalidoException, DatoVacioException, AccesoDatosException {
		Servicio servicio = new Servicio(nombre, descripcion, precio, estadoVigente);
		this.servicioDao.create(servicio);
	}

	@Override
	public void modificarServicio(String nombre, String descripcion, float precio, boolean estadoVigente) 
			throws EntidadNoEncontradaException, ValorInvalidoException, DatoVacioException, AccesoDatosException {
		Servicio servicio = servicioDao.find(nombre);

		servicio.setDescripcion(descripcion);
		servicio.setPrecio(precio);
		servicio.setEstadoVigente(estadoVigente);

		servicioDao.update(servicio);
	}

	@Override
	public void eliminarServicio(String nombre) throws EntidadNoEncontradaException, AccesoDatosException, EntidadEnUsoException{
		servicioDao.remove(nombre);
	}

	@Override
	public ServicioDTO obtenerServicio(String nombre) throws EntidadNoEncontradaException, DatoVacioException, ValorInvalidoException, AccesoDatosException {
		Servicio servicio = servicioDao.find(nombre);		
		return HotelMapper.aDTO(servicio);
	}

	@Override
	public List<ServicioDTO> obtenerListaServicios() throws DatoVacioException, ValorInvalidoException, AccesoDatosException {
		List<Servicio> servicios = servicioDao.findAll();
		List<ServicioDTO> serviciosDTO = new ArrayList<>();

		for (Servicio s : servicios) {
			serviciosDTO.add(HotelMapper.aDTO(s));
		}

		return serviciosDTO;
	}

	// -------------------------------------------------------------------- PLANES

	@Override
	public void eliminarPlan(String nombre) throws EntidadNoEncontradaException, AccesoDatosException, EntidadEnUsoException {
		// remove(String) ya lanza EntidadNoEncontradaException si el plan no existe
		planDao.remove(nombre);
	}

	@Override
	public PlanDTO obtenerPlan(String nombre) throws EntidadNoEncontradaException, AccesoDatosException, DatoVacioException, ValorInvalidoException {
		Plan plan = planDao.find(nombre);
		return HotelMapper.aDTO(plan);
	}

	@Override
	public void registrarPlan(ArrayList<ServicioDTO> listaServicios, float costo, String nombre, boolean esActivo)
			throws EntidadDuplicadaException, DatoVacioException, ValorInvalidoException, AccesoDatosException {
		List<Servicio> servicios = new ArrayList<>();
		for (ServicioDTO a : listaServicios) {
			servicios.add(HotelMapper.aModelo(a));
		}
		Plan plan = new Plan(servicios, costo, nombre, esActivo);
		planDao.create(plan);
	}

	@Override
	public void modificarPlan(ArrayList<ServicioDTO> listaServicios, float costo, String nombre, boolean esActivo)
			throws EntidadNoEncontradaException, ValorInvalidoException, DatoVacioException, AccesoDatosException {

		planDao.find(nombre); // lanza EntidadNoEncontradaException si el plan no existe

		List<Servicio> servicios = new ArrayList<>();
		for (ServicioDTO a : listaServicios) {
			servicios.add(HotelMapper.aModelo(a));
		}
		planDao.update(new Plan(servicios, costo, nombre, esActivo));
	}

	@Override
	public float calcPrecioServicios(ArrayList<ServicioDTO> listaServicios) {
		float precioRecom = 0;
		for (ServicioDTO a : listaServicios) {
			precioRecom += a.getPrecio();
		}
		precioRecom -= precioRecom / 10;
		return precioRecom;
	}

	@Override
	public List<PlanDTO> obtenerListaPlan() throws AccesoDatosException, ValorInvalidoException, DatoVacioException, EntidadDuplicadaException {
		List<Plan> planes = planDao.findAll();
		List<PlanDTO> planesDTO = new ArrayList<>();
		for (Plan p : planes) {
			planesDTO.add(HotelMapper.aDTO(p));
		}
		return planesDTO;
	}


	// ----------------------------------------------------------------- RESERVAS


	@Override
	public void realizarReserva(List<HabitacionDTO> obtenerListaHabitacion, PlanDTO plan, ClienteDTO cliente,
			LocalDate fechaDeEntrada, LocalDate fechaSalida, PagoDTO pagoSeña, float costoTotal)
			throws DatoVacioException, ValorInvalidoException, EntidadDuplicadaException {
		List<Habitacion> habitaciones = new ArrayList<>();
	    for (HabitacionDTO habitacionDTO : obtenerListaHabitacion)
	        habitaciones.add(HotelMapper.aModelo(habitacionDTO));

	    Plan planModelo = HotelMapper.aModelo(plan);
	    Cliente clienteModelo = HotelMapper.aModelo(cliente);
	    Pago pagoModelo = HotelMapper.aModelo(pagoSeña);

	    // el número de reserva y el id del pago los asigna la bd
	    Reserva reserva = new Reserva(habitaciones, planModelo, clienteModelo,
	            fechaDeEntrada, fechaSalida, pagoModelo, 0);
	    reserva.setCostoTotal(costoTotal);

	    try {
	    	ReservaDAO reservaDAO = new ReservaDAOJDBC();
			reservaDAO.create(reserva);
	    } catch (EntidadDuplicadaException | AccesoDatosException e) {
	        throw new ValorInvalidoException("No se pudo guardar la reserva: " + e.getMessage());
	    }
	}
	
	@Override
	public void modificarReserva(ReservaDTO reserva) throws DatoVacioException, ValorInvalidoException, EntidadNoEncontradaException, EntidadDuplicadaException {

	    ReservaDAO reservaDAO = new ReservaDAOJDBC();
	    Reserva reservaEncontrada;

	    // busco la reserva guardada en la BD
	    try {
	        reservaEncontrada = reservaDAO.find(reserva.getNumeroReserva());
	    } catch (AccesoDatosException e) {
	        throw new ValorInvalidoException(e.getMessage());
	    }

	    LocalDate hoy = LocalDate.now();

	    if (hoy.isBefore(reservaEncontrada.getFechaEntrada())) {
	        // NO EMPEZO se puede cambiar todo
	        List<Habitacion> habitaciones = new ArrayList<>();
	        for (HabitacionDTO h : reserva.getHabitaciones())
	            habitaciones.add(HotelMapper.aModelo(h));

	        reservaEncontrada.setHabitaciones(habitaciones);
	        reservaEncontrada.setPlan(HotelMapper.aModelo(reserva.getPlan()));
	        reservaEncontrada.setFechaEntrada(reserva.getFechaEntrada());
	        reservaEncontrada.setFechaSalida(reserva.getFechaSalida());
	        reservaEncontrada.setCostoTotal(reserva.getCostoTotal());

	    } else if (hoy.isBefore(reservaEncontrada.getFechaSalida())) {
	        // EN CURSO solo se puede pasar a un plan de mayor precio
	        if (reserva.getPlan().getCosto() <= reservaEncontrada.getPlan().getCosto())
	            throw new ValorInvalidoException("El plan seleccionado debe ser de mayor precio que el plan actual.");

	        reservaEncontrada.setCostoTotal(reservaEncontrada.getCostoTotal()
	                + (reserva.getPlan().getCosto() - reservaEncontrada.getPlan().getCosto()));
	        reservaEncontrada.setPlan(HotelMapper.aModelo(reserva.getPlan()));

	    } else {
	        // TERMINO
	        throw new ValorInvalidoException("No se puede modificar una reserva que ya finalizó.");
	    }

	    //guardo en la BD
	    try {
	        reservaDAO.update(reservaEncontrada);
	    } catch (AccesoDatosException e) {
	        throw new ValorInvalidoException(e.getMessage());
	    }
	}
		
	

	@Override
	public void eliminarReserva(int numeroReserva) throws EntidadNoEncontradaException, ValorInvalidoException {
		ReservaDAO reservaDAO = new ReservaDAOJDBC();

	    try {
	        reservaDAO.remove(numeroReserva);
	    } catch (AccesoDatosException e) {
	        throw new ValorInvalidoException(e.getMessage());
	    }
	}

	@Override
	public ReservaDTO obtenerReserva(int numeroReserva) throws EntidadNoEncontradaException, FormatoInvalidoException, AccesoDatosException {
	    ReservaDAO reservaDAO = new ReservaDAOJDBC();

	    try {
	        Reserva reserva = reservaDAO.find(numeroReserva);
	        return HotelMapper.aDTO(reserva);

	    } catch (ValorInvalidoException | DatoVacioException e) {
	        throw new FormatoInvalidoException(e.getMessage());
	    }
	}

	@Override
	public List<ReservaDTO> obtenerListaReserva() {

	    ReservaDAO reservaDAO = new ReservaDAOJDBC();
	    List<ReservaDTO> reservasDTO = new ArrayList<>();

	    try {
	        for (Reserva r : reservaDAO.findAll())
	            reservasDTO.add(HotelMapper.aDTO(r));

	    } catch (AccesoDatosException | DatoVacioException e) {
	        throw new RuntimeException("Error al obtener las reservas: " + e.getMessage(), e);
	    }

	    return reservasDTO;
	}

	@Override
public List<HabitacionDTO> buscarDisponibilidad(LocalDate fechaEntrada, LocalDate fechaSalida)
        throws FechaInvalidaException {


    if (!fechaEntrada.isBefore(fechaSalida))
        throw new FechaInvalidaException("la fecha de entrada debe ser anterior a la fecha de salida");

    ReservaDAO reservaDAO = new ReservaDAOJDBC();
    HabitacionDAO habitacionDAO = new HabitacionDAOJDBC();

    List<HabitacionDTO> disponibles = new ArrayList<>();

    try {
        List<Reserva> reservas = reservaDAO.findAll();
        List<Habitacion> habitaciones = habitacionDAO.findAll();

        List<Integer> numerosOcupados = reservas.stream()
                .filter(reserva -> !reserva.isEstadoCancelacion())
                .filter(reserva -> fechaEntrada.isBefore(reserva.getFechaSalida())
                        && fechaSalida.isAfter(reserva.getFechaEntrada()))
                .flatMap(reserva -> reserva.getHabitaciones().stream())
                .map(Habitacion::getNumero)
                .collect(Collectors.toList());

        for (Habitacion habitacion : habitaciones)
            if (!numerosOcupados.contains(habitacion.getNumero()))
               disponibles.add(HotelMapper.aDTO(habitacion));

    } catch (AccesoDatosException | DatoVacioException e) {
        throw new RuntimeException("Error al buscar disponibilidad: " + e.getMessage(), e);
    }

    return disponibles;
}
	
	@Override
	public float calcularCostoReserva(List<HabitacionDTO> habitaciones, LocalDate fechaEntrada, LocalDate fechaSalida, PlanDTO plan){ //contar los fakin dias
		int costoHabitaciones = habitaciones.stream() .mapToInt (habitacion -> habitacion.getCosto()) .sum();

		long dias = Duration.between(fechaEntrada.atStartOfDay(), fechaSalida.atStartOfDay()).toDays();
		
		return (costoHabitaciones + plan.getCosto())* (float) dias; //un plan tiene un costo fijo, independiente de la cantidad de dias????
	}
	

}