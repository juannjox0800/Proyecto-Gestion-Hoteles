package ar.edu.unrn.seminario.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ar.edu.unrn.seminario.dto.ClienteDTO;
import ar.edu.unrn.seminario.dto.HabitacionDTO;
import ar.edu.unrn.seminario.dto.CamaDTO;
import ar.edu.unrn.seminario.dto.HuespedDTO;
import ar.edu.unrn.seminario.dto.PagoDTO;
import ar.edu.unrn.seminario.dto.PlanDTO;
import ar.edu.unrn.seminario.dto.ReservaDTO;
import ar.edu.unrn.seminario.dto.ServicioDTO;
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;
import ar.edu.unrn.seminario.modelo.Cliente;
import ar.edu.unrn.seminario.modelo.Habitacion;
import ar.edu.unrn.seminario.modelo.Cama;
import ar.edu.unrn.seminario.modelo.Huesped;
import ar.edu.unrn.seminario.modelo.Pago;
import ar.edu.unrn.seminario.modelo.Plan;
import ar.edu.unrn.seminario.modelo.Reserva;
import ar.edu.unrn.seminario.modelo.Servicio;

public class HotelMapper {
	
	//mapper de Cliente
	public static ClienteDTO aDTO (Cliente c) {
		if (c == null)
			return null;
		return new ClienteDTO(c.getNombre(), c.getApellido(), c.getContacto(), c.getDni());
	}
	
	public static Cliente aModelo (ClienteDTO c) {
		if (c == null)
			return null;
		return new Cliente(c.getNombre(), c.getApellido(), c.getContacto(), c.getDni());
	}
	
	//mapper de Habitacion
	public static HabitacionDTO aDTO (Habitacion h) {
		if (h == null)
			return null;
		HabitacionDTO dto = new HabitacionDTO(h.getNumero(), h.getTipoHabitacion(), h.getCosto());
		dto.setEstado(h.getEstado()); 
		
		if (h.getCamas() != null) {
			List<CamaDTO> camasDTO = h.getCamas().stream()
					.map(cama -> new CamaDTO(cama.getTipo()))
					.collect(Collectors.toList());
			
			dto.setCamas(camasDTO);
		}
		
		return dto;
	}
	
	public static Habitacion aModelo (HabitacionDTO h)  throws DatoVacioException, ValorInvalidoException {
		if (h == null) {
			return null;
		}
		return new Habitacion(h.getNumero(), h.getTipoHabitacion(), h.getCosto());
	
	}
	
	//mapper de Servicio
	public static ServicioDTO aDTO (Servicio s) {
		if (s == null)
			return null;
		return new ServicioDTO(s.getNombre(), s.getDescripcion(), s.getPrecio(), s.isEstadoVigente());
	}
	
	public static Servicio aModelo (ServicioDTO s) throws DatoVacioException, ValorInvalidoException {
		if (s == null)
			return null;
		return new Servicio(s.getNombre(), s.getDescripcion(), s.getPrecio(), s.isEstadoVigente());
	}
	
	//mapper de Plan
	public static PlanDTO aDTO (Plan p) {
		if (p == null)
			return null;
		
		List<ServicioDTO> serviciosDTO = (p.getServicios() != null) ?
											p.getServicios()
											.stream()
											.map(HotelMapper :: aDTO)
											.collect(Collectors.toList())
											: new ArrayList<>();
		
		return new PlanDTO(serviciosDTO, p.getCosto(), p.getNombre(),p.getEstadoActivo());
	}
	
	public static Plan aModelo (PlanDTO p) throws DatoVacioException, ValorInvalidoException{
		if (p == null)
			return null;
		
		List<Servicio> serviciosModelo = new ArrayList<>();
		
		if (p.getServicios() != null) {
			for (ServicioDTO dto : p.getServicios()) {
				serviciosModelo.add(HotelMapper.aModelo(dto));
			}
		}
		return new Plan(serviciosModelo,p.getCosto(), p.getNombre(), p.isEstadoActivo());
	}
	
	//mapper de Pago
	public static PagoDTO aDTO (Pago p) {
		if (p == null)
			return null;
		return new PagoDTO(p.getTipoPago(), p.getMonto(), p.getFechaPago(), p.getIdPago());
	}
	
	public static Pago aModelo (PagoDTO p) {
		if (p == null)
			return null;
		return new Pago(p.getTipoPago(), p.getMonto(), p.getFechaPago(), p.getIdPago());
	}
	
	//mapper de Huesped
	public static HuespedDTO aDTO (Huesped h) {
		if (h == null)
			return null;
		return new HuespedDTO(h.getNombre(), h.getApellido(), h.getDni());
	}
	
	public static Huesped aModelo (HuespedDTO h) {
		if (h == null)
			return null;
		return new Huesped(h.getNombre(), h.getApellido(), h.getDni());
	}
	
	//mapper de Reserva
	public static ReservaDTO aDTO (Reserva r) {
		if (r == null)
			return null;
		List <HabitacionDTO> habitacionesDTO = (r.getHabitaciones() != null) ?
												r.getHabitaciones().stream()
												.map(HotelMapper :: aDTO)
												.collect(Collectors.toList())
												: new ArrayList<>();
		
		List <HuespedDTO> huespedesDTO = (r.getHuespedes() != null) ?
											r.getHuespedes().stream()
											.map(HotelMapper :: aDTO)
											.collect(Collectors.toList())
											: new ArrayList<>();
		
		return new ReservaDTO(habitacionesDTO,
								HotelMapper.aDTO(r.getPlan()),
								HotelMapper.aDTO(r.getCliente()),
								r.getFechaEntrada(), r.getFechaSalida(),
								HotelMapper.aDTO(r.getPagoSeña()),
								huespedesDTO);
	}
	
	public static Reserva aModelo (ReservaDTO r) throws DatoVacioException, ValorInvalidoException{
		if (r == null)
			return null;
		
		List <Habitacion> habitacionesModelo = new ArrayList<>();
		if (r.getHabitaciones() != null) {
			for (HabitacionDTO habitacion : r.getHabitaciones()) {
				habitacionesModelo.add(HotelMapper.aModelo(habitacion));
			}
		}
		
		List <Huesped> huespedesModelo = new ArrayList<>();
		if (r.getHuespedes() != null) {
			for (HuespedDTO huesped : r.getHuespedes()) {
				huespedesModelo.add(HotelMapper.aModelo(huesped));
			}
		}
		
		return new Reserva(habitacionesModelo,
							HotelMapper.aModelo(r.getPlan()),
							HotelMapper.aModelo(r.getCliente()),
							r.getFechaEntrada(), r.getFechaSalida(),
							HotelMapper.aModelo(r.getPagoSeña()),
							r.getNumeroReserva(),
							huespedesModelo);
	}
}

