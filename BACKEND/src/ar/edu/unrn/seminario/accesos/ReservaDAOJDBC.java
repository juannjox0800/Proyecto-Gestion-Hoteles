package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import ar.edu.unrn.seminario.exception.AccesoDatosException;
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadDuplicadaException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;
import ar.edu.unrn.seminario.modelo.Cliente;
import ar.edu.unrn.seminario.modelo.Habitacion;
import ar.edu.unrn.seminario.modelo.Huesped;
import ar.edu.unrn.seminario.modelo.Pago;
import ar.edu.unrn.seminario.modelo.Plan;
import ar.edu.unrn.seminario.modelo.Reserva;

public class ReservaDAOJDBC implements ReservaDAO{

	ClienteDAO clienteDAO = new ClienteDAOJDBC();
	PlanDAO planDAO = new PlanDAOJDBC();
	HabitacionDao habitacionDAO = new HabitacionDAOJDBC();
	@Override
	public void create(Reserva reserva) throws AccesoDatosException, EntidadDuplicadaException {
	    try {
	        Connection conex = ConnectionManager.getConnection();

	        // reserva
	        PreparedStatement statement = conex.prepareStatement(
	                "INSERT INTO reserva(estado_cancelacion, costoTotal, fecha_salida, fecha_entrada, plan_nombre, dni_cliente) "
	                + "VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

	        statement.setBoolean(1, reserva.isEstadoCancelacion());
	        statement.setFloat(2, reserva.getCostoTotal());
	        statement.setDate(3, java.sql.Date.valueOf(reserva.getFechaSalida()));
	        statement.setDate(4, java.sql.Date.valueOf(reserva.getFechaEntrada()));
	        statement.setString(5, reserva.getPlan().getNombre());
	        statement.setInt(6, reserva.getCliente().getDni());
	        statement.executeUpdate();

	        ResultSet claves = statement.getGeneratedKeys();
	        if (claves.next())
	            reserva.setNumeroReserva(claves.getInt(1));

	        claves.close();
	        statement.close();

	        // reserva_habitacion
	        insertarHabitaciones(conex, reserva);

	        // pago seña
	        insertarPago(conex, reserva.getPagoSeña(), reserva.getNumeroReserva(), "seña");

	    } catch (SQLException e) {
	        if (e.getErrorCode() == 1062)
	            throw new EntidadDuplicadaException("Ya existe una habitación repetida en la reserva");

	        throw new AccesoDatosException("Error al intentar crear la reserva", e);
	    } finally {
	        ConnectionManager.disconnect();
	    }
	}
                           
	public void update(Reserva reserva) throws AccesoDatosException, EntidadNoEncontradaException {
		try {
			Connection conex = ConnectionManager.getConnection();
		
			PreparedStatement statement = conex.prepareStatement( "UPDATE reserva SET estado_cancelacion = ?, costoTotal = ?, fecha_salida = ?, "  + "fecha_entrada = ?, plan_nombre = ? WHERE numero_reserva = ?");
        
			statement.setBoolean(1, reserva.isEstadoCancelacion());
			statement.setFloat(2, reserva.getCostoTotal());
			statement.setDate(3, java.sql.Date.valueOf(reserva.getFechaSalida()));
			statement.setDate(4, java.sql.Date.valueOf(reserva.getFechaEntrada()));
			statement.setString(5, reserva.getPlan().getNombre());
			statement.setInt(6, reserva.getNumeroReserva());
        
			int filas = statement.executeUpdate();
			statement.close();
		
			if (filas== 0) 
			throw new EntidadNoEncontradaException ("No se encontro la reserva con el numero: " + reserva.getNumeroReserva());
	
			//borro tabla de reserva_habitacion antigua
			statement = conex.prepareStatement("DELETE FROM Reserva_Habitacion WHERE numero_reserva = ?");
			statement.setInt(1, reserva.getNumeroReserva());
			statement.executeUpdate();
			statement.close();
		
			//creo la nueva tabla reserva_habitacion 
			insertarHabitaciones(conex, reserva);
			
			  Pago pagoFinal = reserva.getPagoFinal();
		      if (pagoFinal != null)
		          insertarPago(conex, pagoFinal, reserva.getNumeroReserva(), "final");
        
		} catch (SQLException e) {
			throw new AccesoDatosException("Error al intentar modificar la reserva", e);
		} finally {
			ConnectionManager.disconnect();
		}
	}

	public void remove(int numero) throws AccesoDatosException, EntidadNoEncontradaException {
		 try {
			   Connection conex = ConnectionManager.getConnection();

		        // pagos de la reserva 
		        PreparedStatement statement = conex.prepareStatement("DELETE FROM pago WHERE numero_reserva = ?");
		        statement.setInt(1, numero);
		        statement.executeUpdate();
		        statement.close();

		        // habitaciones de la reserva
		        statement = conex.prepareStatement("DELETE FROM Reserva_Habitacion WHERE numero_reserva = ?");
		        statement.setInt(1, numero);
		        statement.executeUpdate();
		        statement.close();

		        // la reserva 
		        statement = conex.prepareStatement("DELETE FROM reserva WHERE numero_reserva = ?");
		        statement.setInt(1, numero);

		        int filas = statement.executeUpdate();
		        statement.close();

		        if (filas == 0)
		            throw new EntidadNoEncontradaException("No se encontro la reserva a eliminar numero: " + numero);
			} catch (SQLException e) {
				throw new AccesoDatosException("Error al intentar eliminar la reserva", e);
			} finally {
				ConnectionManager.disconnect();
			}
		
	}

	@Override
	public void remove(Reserva reserva)
			throws AccesoDatosException, EntidadNoEncontradaException, ValorInvalidoException, AccesoDatosException {
		if(reserva != null)
			this.remove(reserva.getNumeroReserva());
	}

	public Reserva find(int numero)
			throws AccesoDatosException, EntidadNoEncontradaException, ValorInvalidoException, DatoVacioException {
		Reserva reserva = null;
		
		boolean cancelada;
	    float costoTotal;
	    LocalDate fechaEntrada;
	    LocalDate fechaSalida;
	    String planNombre;
	    int dni;
	    List<Integer> numerosHabitacion = new ArrayList<>();
	    Pago pagoSeña = null;
	    Pago pagoFinal = null;
	    
		try {
			Connection conex = ConnectionManager.getConnection();
			
			 // reserva
			PreparedStatement statement = conex.prepareStatement("SELECT * FROM reserva WHERE numero_reserva = ?");
			statement.setInt(1, numero);
			
			ResultSet rs = statement.executeQuery();
			
			 if (!rs.next())
		            throw new EntidadNoEncontradaException("No se encontró la reserva: " + numero);
			 
			 cancelada = rs.getBoolean("estado_cancelacion");
		     costoTotal =rs.getFloat("costoTotal");
		     fechaSalida = rs.getDate("fecha_salida").toLocalDate();
		     fechaEntrada = rs.getDate("fecha_entrada").toLocalDate();
		     planNombre = rs.getString("plan_nombre");
		     dni = rs.getInt("dni_cliente");
		     
		     rs.close();
		     statement.close();
		     
		     // numeros de las habitaciones
		     statement = conex.prepareStatement( "SELECT numero_habitacion FROM Reserva_Habitacion WHERE numero_reserva = ?");
		     statement.setInt(1, numero);
		     rs = statement.executeQuery();

		     while (rs.next())
		    	 numerosHabitacion.add(rs.getInt("numero_habitacion"));

		     rs.close();
		     statement.close();
		     
		  // pagos seña y final
		     statement = conex.prepareStatement("SELECT * FROM pago WHERE numero_reserva = ?");
		     statement.setInt(1, numero);
		     rs = statement.executeQuery();

		     while (rs.next()) {
		    	 Pago pago = new Pago(rs.getString("tipo_pago"), rs.getFloat("monto"),
		         rs.getDate("fecha_pago").toLocalDate(), rs.getInt("id_pago"));
		    	 
		    	 if ("seña".equals(rs.getString("motivo")))
		                pagoSeña = pago;
		            else if ("final".equals(rs.getString("motivo")))
		                pagoFinal = pago;
		        }

		    } catch (SQLException e) {
		        throw new AccesoDatosException("Error al intentar encontrar la reserva", e);
		    } finally {
		        ConnectionManager.disconnect();
		    }
	 // objetos relacionados, con los find de sus DAOs
	 Cliente cliente = clienteDAO.find(dni);
	 Plan plan = planDAO.find(planNombre);
	 
	 List<Habitacion> habitaciones = new ArrayList<>();
	 for (int numeroHabitacion : numerosHabitacion)
		 habitaciones.add(habitacionDAO.find(numeroHabitacion));
	 
	 // armo la reserva
	 reserva = new Reserva(habitaciones, plan, cliente, fechaEntrada, fechaSalida, pagoSeña, numero);
	 reserva.setPagoFinal(pagoFinal);
	 reserva.setCostoTotal(costoTotal);
	 reserva.setEstadoCancelacion(cancelada);
	    
		return reserva;
	}
	
	public List<Reserva> findAll() throws DatoVacioException, DatoVacioException, AccesoDatosException {
		List<Integer> numeros = new ArrayList<>();

	    try {
	        Connection conex = ConnectionManager.getConnection();
	        PreparedStatement statement = conex.prepareStatement("SELECT numero_reserva FROM reserva");
	        ResultSet rs = statement.executeQuery();

	        while (rs.next())
	            numeros.add(rs.getInt("numero_reserva"));

	        rs.close();
	        statement.close();
	    }  catch (SQLException e) {
			throw new AccesoDatosException("Error al intentar encontrar las reservas", e);
		} finally {
			ConnectionManager.disconnect();
		}

	    List<Reserva> reservas = new ArrayList<>();
	    for (int numero : numeros) {
	        try {
	            reservas.add(find(numero));
	        } catch (EntidadNoEncontradaException e) {
	        	
	        } catch (ValorInvalidoException e) {
	            throw new AccesoDatosException("Datos inválidos en la reserva numero" + numero, e);
	        }
	    }

	    return reservas;
	}
	
	private void insertarHabitaciones(Connection conex, Reserva reserva) throws SQLException {
	    PreparedStatement statement = conex.prepareStatement("INSERT INTO Reserva_Habitacion(numero_habitacion, numero_reserva) VALUES (?, ?)");

	    for (Habitacion h : reserva.getHabitaciones()) {
	        statement.setInt(1, h.getNumero());
	        statement.setInt(2, reserva.getNumeroReserva());
	        statement.executeUpdate();
	    }
	    statement.close();
	   
	}

	private void insertarPago(Connection conex, Pago pago, int numeroReserva, String motivo) throws SQLException {
	    if (pago == null)
	        return;

	    PreparedStatement statement = conex.prepareStatement("INSERT INTO pago(monto, fecha_pago, tipo_pago, motivo, numero_reserva) VALUES (?, ?, ?, ?, ?)",
	            Statement.RETURN_GENERATED_KEYS);

	    statement.setFloat(1, pago.getMonto());
	    statement.setDate(2, java.sql.Date.valueOf(pago.getFechaPago()));
	    statement.setString(3, pago.getTipoPago());
	    statement.setString(4, motivo);
	    statement.setInt(5, numeroReserva);
	    statement.executeUpdate();

	    ResultSet claves = statement.getGeneratedKeys();
	    if (claves.next())
	        pago.setIdPago(claves.getInt(1));

	    claves.close();
	    statement.close();
	}

	
}
