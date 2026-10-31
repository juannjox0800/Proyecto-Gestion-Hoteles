package ar.edu.unrn.seminario.api;

import java.time.*;
import java.util.*;
import ar.edu.unrn.seminario.mapper.HotelMapper;
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
import ar.edu.unrn.seminario.modelo.Cliente;
import ar.edu.unrn.seminario.modelo.Habitacion;
import ar.edu.unrn.seminario.modelo.Plan;
import ar.edu.unrn.seminario.modelo.Reserva;
import ar.edu.unrn.seminario.modelo.Servicio;

public class MemoryApi implements IApi {
	private List<Cliente> clientes = new ArrayList<>();
	private List<Habitacion> habitaciones = new ArrayList<>();
	private List<Plan> planes = new ArrayList<>();
	private List<Reserva> reservas = new ArrayList<>(); 
	private List<Servicio> servicios = new ArrayList<>();
	
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
		if (!planExiste(nombre)) {
			if (nombre!=null&(!nombre.trim().isEmpty())&listaServicios!=null) {
				if (costo>0) {
				ArrayList<Servicio> servicios = new ArrayList<>();
				for (ServicioDTO dto : listaServicios) {
					servicios.add(HotelMapper.aModelo(dto));
				}
				Plan planadd= new Plan(servicios,costo ,nombre, false);
				this.planes.add(planadd);
				}else throw new ValorInvalidoException("valor invalido");
			}else throw new DatoVacioException("faltan datos");
		}else throw new EntidadDuplicadaException("Ya existe esta entidad");
	}
	public boolean planExiste(String nombre) {
		boolean s =false;
	for (Plan a : this.planes) {
		if (a.getNombre().equals(nombre)) s = true;
	}
		return s;
		
	}

	@Override
	public void modificarPlan(ArrayList<ServicioDTO> listaServicios, int costo,
			String nombre) {
		if (planExiste(nombre)){
				if ( costo>0){
					if (!nombre.isEmpty() & nombre!=null&listaServicios!=null) {
						ArrayList<Servicio> servicios = new ArrayList<>();
						for (ServicioDTO dto : listaServicios) {
							servicios.add(HotelMapper.aModelo(dto));
						}
					
						for (Plan a : this.planes) {
							if (a.getNombre().equals(nombre)) {
								a.setServicios(servicios);
								a.setCosto(costo);
								a.setNombre(nombre);
							}
						}
					}else throw new DatoVacioException("faltan datos");
				} else throw new ValorInvalidoException("valor no valido ");
		
		}else throw new EntidadNoEncontradaException("Plan no existe");
		

	
	}
	@Override
	public void eliminarPlan(String nombre) {
		if (planExiste(nombre)) {
			this.planes.removeIf(p -> p.getNombre().equals(nombre));
		}else throw new EntidadNoEncontradaException("Plan no existe");

		
	}

	@Override
	public PlanDTO obtenerPlan(String nombre) {
		
			for(Plan p : this.planes) {
				if (p.getNombre().equals(nombre)) {
					return HotelMapper.aDTO(p);
				}
			
		}
		throw new EntidadNoEncontradaException("Plan no existe");

		
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
