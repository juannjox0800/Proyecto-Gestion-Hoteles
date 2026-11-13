package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ar.edu.unrn.seminario.modelo.Cama;
import ar.edu.unrn.seminario.modelo.Habitacion;

public class HabitacionDAOJDBC implements HabitacionDao{

	
	//como es de creacion voy a usar la sentencia SQL de INSERT, entonces me conviene usar la clase PreparedStatement
	public void create(Habitacion habitacion) {
		
		try {
			
			Connection conexion = ConnectionManager.getConnection();
			String insertH = "INSERT INTO habitacion(numero_habitacion, tipoHabitacion, costo, estado) VALUES (? , ? , ?, ?)";
			PreparedStatement pstat = conexion.prepareStatement(insertH);
			
			pstat.setInt(1, habitacion.getNumero());
			pstat.setString(2, habitacion.getTipoHabitacion());
			pstat.setInt(3, habitacion.getCosto());
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
			
			
		}finally {
			
			ConnectionManager.disconnect();
		}
		
	}
	
	public void update(Habitacion habitacion) {
		
		try {
			
			Connection conexion = ConnectionManager.getConnection();
			
			String updateH = "UPDATE habitacion SET tipoHabitacion = ?, costo = ?, estado = ? WHERE numero_habitacion = ?";
			PreparedStatement pstatH = conexion.prepareStatement(updateH);
			
			pstatH.setString(1, habitacion.getTipoHabitacion());
			pstatH.setInt(2, habitacion.getCosto());
			pstatH.setString(3, habitacion.getEstado());
			pstatH.setInt(4, habitacion.getNumero());
			
			pstatH.executeUpdate();
			
			//elimino listado original y agrego el listado de camas nuevo
			String deleteCama = "DELETE FROM cama WHERE numero_habitacion = ?";
			PreparedStatement pstatDeleteCama = conexion.prepareStatement(deleteCama);
			
			pstatDeleteCama.setInt(1, habitacion.getNumero());
			
			pstatDeleteCama.executeUpdate();
			
			String insertCama = "INSERT INTO cama(tipo, numero_habitacion) VALUES(?, ?)";
			PreparedStatement pstatCama = conexion.prepareStatement(insertCama);
			
			for(Cama cama : habitacion.getCamas()) {
				
				pstatCama.setString(1, cama.getTipo());
				pstatCama.setInt(2, habitacion.getNumero());
				
				pstatCama.executeUpdate();
			}
			
			
		}catch(SQLException e) {
			
		}finally {
			ConnectionManager.disconnect();
		}
		
		
	}
	
	public void remove(int numero) {
		
		try {
			
			Connection conexion = ConnectionManager.getConnection();
			
			String removeCama = "DELETE FROM cama WHERE numero_habitacion = ?";
			PreparedStatement pstatCama = conexion.prepareStatement(removeCama);
			
			pstatCama.setInt(1, numero);
			pstatCama.executeUpdate();
			
			String removeH = "DELETE FROM habitacion WHERE numero_habitacion = ?";
			PreparedStatement pstatH = conexion.prepareStatement(removeH);
			
			pstatH.setInt(1, numero);
			pstatH.executeUpdate();
			
			
		}catch(SQLException e) {
			
		}finally {
			ConnectionManager.disconnect();
		}
		
	}
	
	public void remove(Habitacion habitacion) {
		remove(habitacion.getNumero());
	}
	
	public Habitacion find(int numero) {
		Habitacion habitacion = null;
		try {
			
			Connection conexion = ConnectionManager.getConnection();
			
			String findH = "SELECT * FROM habitacion WHERE numero_habitacion = ?";
			PreparedStatement pstatH = conexion.prepareStatement(findH);
			
			pstatH.setInt(1, numero);
			
			ResultSet resultado = pstatH.executeQuery();
			
			if(resultado.next()) {
				habitacion = new Habitacion(resultado.getInt("numero_habitacion"), resultado.getString("tipoHabitacion"), resultado.getInt("costo"));
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
			
		}finally {
			ConnectionManager.disconnect();
		}
		return habitacion;
	}
	
	public List<Habitacion> findAll();
}
