package ar.edu.unrn.seminario.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.accesos.ClienteDAO;
import ar.edu.unrn.seminario.accesos.ClienteDAOJDBC;
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
import ar.edu.unrn.seminario.modelo.Cliente;

public class PersistenceApi implements IApi{
	
private ClienteDAO clienteDao; 
public PersistenceApi() {
	clienteDao = new ClienteDAOJDBC();
}

	@Override
	public void registrarCliente(String nombre, String apellido, String contacto, int dni) throws EntidadDuplicadaException, DatoVacioException, ValorInvalidoException {
		// TODO Auto-generated method stub
		Cliente cliente=  new Cliente (nombre, apellido,contacto, dni);
		this.clienteDao.create(cliente);
		
	}

	@Override
	public void modificarCLiente(int dni, String nombre, String apellido, String contacto) throws EntidadNoEncontradaException, DatoVacioException, ValorInvalidoException{
		// TODO Auto-generated method stub
		Cliente cliente = clienteDao.find(dni);
		cliente.setApellido(apellido);
		cliente.setContacto(contacto);
		cliente.setNombre(nombre);
		 clienteDao.update(cliente);
	}
	
	@Override
	public void eliminarCliente(int dni) throws EntidadNoEncontradaException {
		Cliente cliente = clienteDao.find(dni);
		clienteDao.remove(cliente);
		
	}

	@Override
	public boolean existeCliente(int dni) {
		try {
			clienteDao.find(dni);
			return true;
		} catch (EntidadNoEncontradaException e) {
			return false;
		}	
	}

	@Override
	public ClienteDTO obtenerCliente(int dni) throws EntidadNoEncontradaException, FormatoInvalidoException {
		Cliente cliente = clienteDao.find(dni);
		return HotelMapper.aDTO(cliente);
	}

	@Override
	public List<ClienteDTO> obtenerListaClientes() {
		List<Cliente> clientes = clienteDao.findAll();
		List<ClienteDTO> clientesDTO = new ArrayList<>();
		for (Cliente c : clientes) {
			clientesDTO.add(HotelMapper.aDTO(c));
		}
		return clientesDTO;
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
