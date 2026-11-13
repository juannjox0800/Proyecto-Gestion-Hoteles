package ar.edu.unrn.seminario.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.accesos.ServicioDAO;
import ar.edu.unrn.seminario.accesos.ServicioDAOJDBC;
import ar.edu.unrn.seminario.dto.ClienteDTO;
import ar.edu.unrn.seminario.dto.HabitacionDTO;
import ar.edu.unrn.seminario.dto.PagoDTO;
import ar.edu.unrn.seminario.dto.PlanDTO;
import ar.edu.unrn.seminario.dto.ReservaDTO;
import ar.edu.unrn.seminario.dto.ServicioDTO;
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadDuplicadaException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.FechaInvalidaException;
import ar.edu.unrn.seminario.exception.FormatoInvalidoException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;
import ar.edu.unrn.seminario.mapper.HotelMapper;
import ar.edu.unrn.seminario.modelo.Servicio;

public class PersistenceApi implements IApi{
	
	private ServicioDAO servicioDao;
	
	public PersistenceApi(){
		servicioDao = new ServicioDAOJDBC();
	}

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
		Servicio servicio = new Servicio(nombre, descripcion, precio, estadoVigente);
		this.servicioDao.create(servicio);
	}

	@Override
	public void modificarServicio(String nombre, String descripcion, float precio, boolean estadoVigente)
			throws EntidadNoEncontradaException, ValorInvalidoException {
		
		Servicio servicio = servicioDao.find(nombre);
		
		servicio.setDescripcion(descripcion);
		servicio.setPrecio(precio);
		servicio.setEstadoVigente(estadoVigente);
		
		servicioDao.update(servicio);
	}

	@Override
	public void eliminarServicio(String nombre) throws EntidadNoEncontradaException {
		Servicio servicio = servicioDao.find(nombre);
		
		servicioDao.remove(servicio);
	}

	@Override
	public ServicioDTO obtenerServicio(String nombre) throws EntidadNoEncontradaException {
		Servicio servicio = servicioDao.find(nombre);		
		return HotelMapper.aDTO(servicio);
	}

	@Override
	public List<ServicioDTO> obtenerListaServicios() {
		List<Servicio> servicios = servicioDao.findAll();
		List<ServicioDTO> serviciosDTO = new ArrayList<>();
		
		for (Servicio s : servicios) {
			serviciosDTO.add(HotelMapper.aDTO(s));
		}
		
		return serviciosDTO;
	}

	@Override
	public void registrarPlan(ArrayList<ServicioDTO> listaServicios, int costo, String nombre, boolean esActivo)
			throws EntidadDuplicadaException, DatoVacioException, ValorInvalidoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificarPlan(ArrayList<ServicioDTO> listaServicios, int costo, String nombre, boolean esActivo)
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
	public void guardarListaPLan(List<PlanDTO> listaNueva) throws DatoVacioException, ValorInvalidoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void realizarReserva(List<HabitacionDTO> obtenerListaHabitacion, PlanDTO plan, ClienteDTO cliente,
			LocalDate fechaDeEntrada, LocalDate fechaSalida, PagoDTO pagoSeña, int costoTotal)
			throws DatoVacioException, ValorInvalidoException {
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
	public ReservaDTO obtenerReserva(int numeroReserva) throws EntidadNoEncontradaException, FormatoInvalidoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReservaDTO> obtenerListaReserva() {
		// TODO Auto-generated method stub
		return null;
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
	public PlanDTO buscarPlan(String nombrePlan) throws EntidadNoEncontradaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int calcularSeña(int costoHabitaciones, LocalDate fechaEntrada, LocalDate fechaSalida) {
		// TODO Auto-generated method stub
		return 0;
	}

}
