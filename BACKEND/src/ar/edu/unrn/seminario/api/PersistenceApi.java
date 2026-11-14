package ar.edu.unrn.seminario.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.accesos.PlanDAO;
import ar.edu.unrn.seminario.accesos.PlanDAOJDBC;
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
import ar.edu.unrn.seminario.modelo.Plan;
import ar.edu.unrn.seminario.modelo.Servicio;

public class PersistenceApi implements IApi {

	private PlanDAO planDao;
	private ServicioDAO servicioDao;

	public PersistenceApi() {
		planDao = new PlanDAOJDBC();
		servicioDao = new ServicioDAOJDBC();
	}

	// ---------------------------------------------------------------- CLIENTES

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
		// TODO Reemplazar por la consulta al DAO. Mientras tanto, lista vacia (nunca null).
		return new ArrayList<>();
	}

	// ------------------------------------------------------------- HABITACIONES

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
		// TODO Reemplazar por la consulta al DAO. Mientras tanto, lista vacia (nunca null).
		return new ArrayList<>();
	}

	// ---------------------------------------------------------------- SERVICIOS

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

	// -------------------------------------------------------------------- PLANES

	@Override
	public void eliminarPlan(String nombre) throws EntidadNoEncontradaException {
		// remove(String) ya lanza EntidadNoEncontradaException si el plan no existe
		planDao.remove(nombre);
	}

	@Override
	public PlanDTO obtenerPlan(String nombre) throws EntidadNoEncontradaException {
		Plan plan = planDao.find(nombre);
		return HotelMapper.aDTO(plan);
	}

	@Override
	public void registrarPlan(ArrayList<ServicioDTO> listaServicios, float costo, String nombre, boolean esActivo)
			throws EntidadDuplicadaException, DatoVacioException, ValorInvalidoException {
		List<Servicio> servicios = new ArrayList<>();
		for (ServicioDTO a : listaServicios) {
			servicios.add(HotelMapper.aModelo(a));
		}
		Plan plan = new Plan(servicios, costo, nombre, esActivo);
		planDao.create(plan);
	}

	@Override
	public void modificarPlan(ArrayList<ServicioDTO> listaServicios, float costo, String nombre, boolean esActivo)
			throws EntidadNoEncontradaException, ValorInvalidoException, DatoVacioException {

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
	public List<PlanDTO> obtenerListaPlan() {
		List<Plan> planes = planDao.findAll();
		List<PlanDTO> planesDTO = new ArrayList<>();
		for (Plan p : planes) {
			planesDTO.add(HotelMapper.aDTO(p));
		}
		return planesDTO;
	}

	@Override
	public PlanDTO buscarPlan(String nombrePlan) throws EntidadNoEncontradaException {
		// TODO Auto-generated method stub
		return null;
	}

	// ----------------------------------------------------------------- RESERVAS

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
		// TODO Reemplazar por la consulta al DAO. Mientras tanto, lista vacia (nunca null).
		return new ArrayList<>();
	}

	@Override
	public List<HabitacionDTO> buscarDisponibilidad(LocalDate fechaEntrada, LocalDate fechaSalida)
			throws FechaInvalidaException {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public void RegistrarPago(String tipoPago, int monto, LocalDate fechaPago, int idPago) {
		// TODO Auto-generated method stub

	}

	@Override
	public int calcularSeña(int costoHabitaciones, LocalDate fechaEntrada, LocalDate fechaSalida) {
		// TODO Auto-generated method stub
		return 0;
	}

}