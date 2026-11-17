package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import ar.edu.unrn.seminario.exception.AccesoDatosException;
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadDuplicadaException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;
import ar.edu.unrn.seminario.modelo.Cama;
import ar.edu.unrn.seminario.modelo.Habitacion;

public class HabitacionDAOJDBC implements HabitacionDao{

	
	//como es de creacion voy a usar la sentencia SQL de INSERT, entonces me conviene usar la clase PreparedStatement
	public void create(Habitacion habitacion) throws EntidadDuplicadaException, AccesoDatosException {
		
		try {
			
			Connection conexion = ConnectionManager.getConnection();
			String insertH = "INSERT INTO habitacion(numero_habitacion, tipoHabitacion, costo, estado) VALUES (? , ? , ?, ?)";
			PreparedStatement pstat = conexion.prepareStatement(insertH);
			
			pstat.setInt(1, habitacion.getNumero());
			pstat.setString(2, habitacion.getTipoHabitacion());
			pstat.setFloat(3, habitacion.getCosto());
			pstat.setString(4, habitacion.getEstado());
			
			pstat.executeUpdate();
			
			String insertCama = "INSERT INTO cama(tipo, numero_habitacion) VALUES(?, ?)";
			PreparedStatement pstatCama = conexion.prepareStatement(insertCama);
			
			//por cada cama que tenga la habitacion voy a agregar una fila a la tabla 
			for(Cama cama : habitacion.getCamas()) {
				
				pstatCama.setString(1, cama.getTipo());
				pstatCama.setInt(2, habitacion.getNumero());
				
				pstatCama.executeUpdate();
			}
			
		}catch(SQLException e) {
			if(e.getErrorCode() == 1062) {
				throw new EntidadDuplicadaException("Ya existe una habitación con ese número. Intente de nuevo.");
			}else {
				throw new AccesoDatosException("Error al crear la habitación.", e);
			}
			
			
		}finally {
			
			ConnectionManager.disconnect();
		}
		
	}
	
	public void update(Habitacion habitacion) throws EntidadNoEncontradaException, AccesoDatosException {
		Connection conexion = null;
		try {
			
			conexion = ConnectionManager.getConnection();
			conexion.setAutoCommit(false);
			
			String updateH = "UPDATE habitacion SET tipoHabitacion = ?, costo = ?, estado = ? WHERE numero_habitacion = ?";
			PreparedStatement pstatH = conexion.prepareStatement(updateH);
			
			pstatH.setString(1, habitacion.getTipoHabitacion());
			pstatH.setFloat(2, habitacion.getCosto());
			pstatH.setString(3, habitacion.getEstado());
			pstatH.setInt(4, habitacion.getNumero());
			
			pstatH.executeUpdate();
			
			//elimino listado original y agrego el listado de camas nuevo
			String deleteCama = "DELETE FROM cama WHERE numero_habitacion = ?";
			PreparedStatement pstatDeleteCama = conexion.prepareStatement(deleteCama);
			
			pstatDeleteCama.setInt(1, habitacion.getNumero());
			
			int filasAfectadas = pstatDeleteCama.executeUpdate();
			
			if(filasAfectadas == 0) {
				conexion.rollback();
				throw new EntidadNoEncontradaException("La habitación que quiere modificar no se encuentra en el sistema.");
			}
			
			String insertCama = "INSERT INTO cama(tipo, numero_habitacion) VALUES(?, ?)";
			PreparedStatement pstatCama = conexion.prepareStatement(insertCama);
			
			for(Cama cama : habitacion.getCamas()) {
				
				pstatCama.setString(1, cama.getTipo());
				pstatCama.setInt(2, habitacion.getNumero());
				
				pstatCama.executeUpdate();
			}
			
			conexion.commit();  //indica q todo salio bien
			
		}catch(SQLException e) {
			
			try {
				if (conexion != null) conexion.rollback();
			}catch(SQLException e1) {
				throw new AccesoDatosException("Error al revertir la transaccion de modificacion.", e1);
			}
			throw new AccesoDatosException("Error al intentar modificar la habitacion.", e);
			
		}finally {
			
			try {
				if(conexion != null) conexion.setAutoCommit(true);
				
			}catch(SQLException e) {
				throw new AccesoDatosException("Error al restaurar autoCommit", e);
			}
			ConnectionManager.disconnect();
		}
		
		
	}
	
	public void remove(int numero) throws EntidadNoEncontradaException, AccesoDatosException {
		
		Connection conexion = null;
		try {
			
			conexion = ConnectionManager.getConnection();
			conexion.setAutoCommit(false);
			
			String removeCama = "DELETE FROM cama WHERE numero_habitacion = ?";
			PreparedStatement pstatCama = conexion.prepareStatement(removeCama);
			
			pstatCama.setInt(1, numero);
			pstatCama.executeUpdate();
			
			String removeH = "DELETE FROM habitacion WHERE numero_habitacion = ?";
			PreparedStatement pstatH = conexion.prepareStatement(removeH);
			pstatH.setInt(1, numero);
			
			int filasAfectadas = pstatH.executeUpdate();
			
			if(filasAfectadas == 0) {
				conexion.rollback();
				throw new EntidadNoEncontradaException("La habitación que quiere eliminar no se encuentra en el sistema.");
				
			}
			
			conexion.commit();
						
		}catch(SQLException e) {
			try {
				if(conexion != null) conexion.rollback();
			}catch(SQLException e1) {
				throw new AccesoDatosException("Error al revertir la transaccion de eliminacion.");
			}
			if(e.getErrorCode() == 1451) {
				throw new AccesoDatosException("No se puede eliminar la habitacion.", e);
			}
			throw new AccesoDatosException("Error al intentar eliminar la habitacion", e);
		}finally {
			try {
				if(conexion != null) conexion.setAutoCommit(true);
			}catch(SQLException e) {
				throw new AccesoDatosException("Error al restaurar autoCommit", e);
			}
			ConnectionManager.disconnect();
		}
		
	}
	
	public void remove(Habitacion habitacion) throws EntidadNoEncontradaException, AccesoDatosException {
		remove(habitacion.getNumero());
	}
	
	public Habitacion find(int numero) throws AccesoDatosException, DatoVacioException, ValorInvalidoException {
		Habitacion habitacion = null;
		try {
			
			Connection conexion = ConnectionManager.getConnection();
			
			String findH = "SELECT * FROM habitacion WHERE numero_habitacion = ?";
			PreparedStatement pstatH = conexion.prepareStatement(findH);
			
			pstatH.setInt(1, numero);
			
			ResultSet resultado = pstatH.executeQuery();
			
			if(resultado.next()) {
				habitacion = new Habitacion(resultado.getInt("numero_habitacion"), resultado.getString("tipoHabitacion"), resultado.getFloat("costo"));
				habitacion.setEstado(resultado.getString("estado"));
				
				
				String findCama = "SELECT c.tipo FROM cama c WHERE c.numero_habitacion = ?";
				PreparedStatement pstatCama = conexion.prepareStatement(findCama);
				pstatCama.setInt(1, numero);
				
				ResultSet resultadoCamas = pstatCama.executeQuery();
				
				while(resultadoCamas.next()) {

					Cama cama = new Cama(resultadoCamas.getString("tipo"));	
					habitacion.getCamas().add(cama);
				}			
			}

		}catch(SQLException e) {
			throw new AccesoDatosException("Error al intentar encontrar la habitacion.");
		}finally {
			ConnectionManager.disconnect();
		}
		return habitacion;
	}
	

	public List<Habitacion> findAll() throws AccesoDatosException, DatoVacioException, ValorInvalidoException{
		
		List<Habitacion> habitaciones = new ArrayList<>();
		
		try {
			Connection conexion = ConnectionManager.getConnection();
			
			String findAllH = "SELECT * FROM habitacion";
			PreparedStatement pstatH = conexion.prepareStatement(findAllH);
			ResultSet resultadoH = pstatH.executeQuery();
			
			while(resultadoH.next()) {
				
				Habitacion h = new Habitacion(resultadoH.getInt("numero_habitacion"), resultadoH.getString("tipoHabitacion"), resultadoH.getFloat("costo"));
				h.setEstado(resultadoH.getString("estado"));
				
				String findCama = "SELECT c.tipo FROM cama c WHERE c.numero_habitacion = ?";
				PreparedStatement pstatCama = conexion.prepareStatement(findCama);
				pstatCama.setInt(1, h.getNumero());
				
				ResultSet resultadoCama = pstatCama.executeQuery();
				
				while(resultadoCama.next()) {
					
					Cama cama = new Cama(resultadoCama.getString("tipo"));	
					h.getCamas().add(cama);
					
				}
				habitaciones.add(h);
				
			}
			
			
		}catch(SQLException e) {
			
			throw new AccesoDatosException("Error al intentar encontrar las habitaciones."); 
		}finally {
			ConnectionManager.disconnect();
		}
	
		return habitaciones;
	}
}
