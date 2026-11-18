package ar.edu.unrn.seminario.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.FechaInvalidaException;
import ar.edu.unrn.seminario.exception.FormatoInvalidoException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;
import ar.edu.unrn.seminario.mapper.HotelMapper;
import ar.edu.unrn.seminario.modelo.Cliente;
import ar.edu.unrn.seminario.modelo.Habitacion;
import ar.edu.unrn.seminario.modelo.Pago;
import ar.edu.unrn.seminario.modelo.Plan;
import ar.edu.unrn.seminario.modelo.Reserva;

public class PersistenceApi implements IApi{

	@Override
	public void registrarCliente(String nombre, String apellido, String contacto, int dni)
			throws EntidadDuplicadaException, DatoVacioException, ValorInvalidoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificarCLiente(int dni, String nombre, String apellido, String contacto)
			throws EntidadNoEncontradaException, DatoVacioException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarCliente(int dni) throws EntidadNoEncontradaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existeCliente(int dni) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ClienteDTO obtenerCliente(int dni) throws EntidadNoEncontradaException, FormatoInvalidoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClienteDTO> obtenerListaClientes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registrarHabitacion(int numero, String tipoHabitacion, String estado, int costo, List<String> camas)
			throws EntidadDuplicadaException, DatoVacioException, ValorInvalidoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean modificarHabitacion(int numero, int numeroNuevo, String tipoHabitacion, String estado, int costo,
			List<String> camas) throws DatoVacioException, EntidadNoEncontradaException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eliminarHabitacion(int numero) throws EntidadNoEncontradaException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HabitacionDTO obtenerHabitacion(int numero) throws EntidadNoEncontradaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HabitacionDTO> obtenerListaHabitacion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registrarServicio(String nombre, String descripcion, float precio, boolean estadoVigente)
			throws EntidadDuplicadaException, ValorInvalidoException, DatoVacioException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificarServicio(String nombre, String descripcion, float precio, boolean estadoVigente)
			throws EntidadNoEncontradaException, ValorInvalidoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarServicio(String nombre) throws EntidadNoEncontradaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServicioDTO obtenerServicio(String nombre) throws EntidadNoEncontradaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ServicioDTO> obtenerListaServicios() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registrarPlan(ArrayList<ServicioDTO> listaServicios, float costo, String nombre, boolean esActivo)
			throws EntidadDuplicadaException, DatoVacioException, ValorInvalidoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificarPlan(ArrayList<ServicioDTO> listaServicios, float costo, String nombre, boolean esActivo)
			throws EntidadNoEncontradaException, ValorInvalidoException, DatoVacioException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarPlan(String nombre) throws EntidadNoEncontradaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PlanDTO obtenerPlan(String nombre) throws EntidadNoEncontradaException {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void RegistrarPago(String tipoPago, int monto, LocalDate fechaPago, int idPago) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float calcularCostoReserva(List<HabitacionDTO> habitaciones, LocalDate fechaEntrada, LocalDate fechaSalida, PlanDTO plan) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float calcPrecioServicios(ArrayList<ServicioDTO> listaServicios) {
		// TODO Auto-generated method stub
		return 0;
	}

}
