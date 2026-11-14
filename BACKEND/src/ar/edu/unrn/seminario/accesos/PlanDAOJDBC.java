package ar.edu.unrn.seminario.accesos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import ar.edu.unrn.seminario.exception.*;
import ar.edu.unrn.seminario.modelo.Plan;
import ar.edu.unrn.seminario.modelo.Servicio;

public class PlanDAOJDBC implements PlanDAO {
//utilice try-with-resoures
	@Override
	public void create(Plan plan) throws EntidadDuplicadaException, SQLException{
		String sql = "INSERT INTO Plan(nombre_plan, costo,  estado_activo) VALUES (?, ?, ?, ?)";
		try(PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(sql)){
			statement.setString(1, plan.getNombre());
			statement.setFloat(2,plan.getCosto());
			statement.setBoolean(3, plan.isEstadoActivo());
			statement.executeUpdate();
			
		}catch (SQLException e) {
			if(e.getErrorCode()==1062) {//errorcode 1062 es duplicado
				throw new EntidadDuplicadaException("Ya existe un plan con el nombre: " + plan.getNombre());
				
			} throw new RuntimeException("Error al registrar el plan", e);
		}
		finally {
			ConnectionManager.disconnect();
		}
	}

	@Override
	public void update(Plan plan) throws SQLException, EntidadNoEncontradaException {
		String sql =  "UPDATE plan SET costo=?, estado_activo=? WHERE nombre_plan";
		try(PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(sql)){
			statement.setFloat(1,plan.getCosto());
			statement.setBoolean(2, plan.isEstadoActivo());
			statement.setString(3, plan.getNombre());
			statement.executeUpdate();
			int filas= statement.executeUpdate();
			if(filas==0)throw new EntidadNoEncontradaException("No se encontro el plan: "+plan.getNombre());

		}catch(SQLException e) {
			 throw new RuntimeException("Error al modificar el plan", e);
		}finally {
			ConnectionManager.disconnect();
		}
	}
	
/**/
	@Override
	public void remove(Plan plan) throws SQLException, EntidadNoEncontradaException {
		if(plan==null)		throw new EntidadNoEncontradaException("plan nulo ingresa otro");
		remove(plan.getNombre());
	}
	@Override
	public void remove(String nombre) throws SQLException, EntidadNoEncontradaException {
		String sql = "DELETE FROM plan where nombre_plan=?";
		try(PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(sql)){
			statement.setString(1, nombre);
			int filas= statement.executeUpdate();
			if(filas==0)throw new EntidadNoEncontradaException("No se encontro el plan: "+nombre);
		}catch(SQLException e) {
			 throw new RuntimeException("Error al eliminar el plan", e);

		}
	}

	
	/*@Override
	public Servicio find(String nombre) throws EntidadNoEncontradaException {
		Servicio servicio = null;
		
		try {
			Connection conex = ConnectionManager.getConnection();
			
			PreparedStatement statement = conex.prepareStatement("SELECT s.nombre_servicio, s.descripcion, s.precio, s.estado_vigente " +
																"FROM servicio s " + "WHERE s.nombre_servicio = ?");
			
			statement.setString(1, nombre);
			
			ResultSet rs = statement.executeQuery();
			
			if (rs.next()) {
				try {
				servicio = new Servicio(rs.getString("nombre_servicio"), rs.getString("descripcion"), rs.getFloat("precio"),rs.getBoolean("estado_vigente"));
				} catch (ValorInvalidoException | DatoVacioException e) {
					
				}
			}
		} catch (SQLException e) {
			throw new EntidadNoEncontradaException("No se encontro el servicio: " + nombre);
		} finally {
			ConnectionManager.disconnect();
		}
		
		return servicio;
	}*/
	@Override
	public Plan find(String nombre) throws SQLException, EntidadNoEncontradaException,
	EntidadDuplicadaException, ValorInvalidoException, DatoVacioException {

String sql = "SELECT p.nombre_plan, p.costo, p.estado_activo, "
		+ "s.nombre_servicio, s.descripcion, s.precio, s.estado_vigente "
		+ "FROM plan p "
		+ "JOIN plan_servicio ps ON p.nombre_plan = ps.plan_nombre "
		+ "JOIN servicio s ON ps.nombre_servicio = s.nombre_servicio "
		+ "WHERE p.nombre_plan = ?";

try (PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(sql)) {
	statement.setString(1, nombre);

	try (ResultSet rs = statement.executeQuery()) {
		String nombrePlan = null;
		float costo = 0;
		boolean activo = false;
		boolean encontrado = false;

		List<Servicio> servicios = new ArrayList<>();

		while (rs.next()) {
			if (!encontrado) {
				nombrePlan = rs.getString("nombre_plan");
				costo = rs.getFloat("costo");
				activo = rs.getBoolean("estado_activo");
				encontrado = true;
			}
			servicios.add(new Servicio(
					rs.getString("nombre_servicio"),
					rs.getString("descripcion"),
					rs.getFloat("precio"),
					rs.getBoolean("estado_vigente")));
		}

		if (!encontrado) {
			throw new EntidadNoEncontradaException("No se encontró el plan: " + nombre);
		}

		return new Plan(servicios, costo, nombrePlan, activo);
	}

} finally {
	ConnectionManager.disconnect();
}
}

	@Override
	public List<Plan> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
/*package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadDuplicadaException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;
import ar.edu.unrn.seminario.modelo.Servicio;

public class ServicioDAOJDBC implements ServicioDAO{

	@Override
	public void create(Servicio servicio) throws EntidadDuplicadaException {
		try {
			Connection conex = ConnectionManager.getConnection();
			
			PreparedStatement statement = conex.prepareStatement("INSERT INTO servicio(nombre_servicio, descripcion, precio, estado_vigente) " + "VALUES (?, ?, ?, ?)");
			
			statement.setString(1, servicio.getNombre());
			statement.setString(2, servicio.getDescripcion());
			statement.setFloat(3, servicio.getPrecio());
			statement.setBoolean(4, servicio.isEstadoVigente());
			
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new EntidadDuplicadaException("Ya existe un servicio con el nombre: " + servicio.getNombre());
			
		} finally {
			ConnectionManager.disconnect();
		}
		
	}

	@Override
	public void update(Servicio servicio) {
		try {
			Connection conex = ConnectionManager.getConnection();
			
			PreparedStatement statement = conex.prepareStatement("UPDATE servicio SET descripcion = ?, precio = ?, estado_vigente = ? WHERE nombre_servicio = ?");
			
			statement.setString(1, servicio.getDescripcion());
			statement.setFloat(2, servicio.getPrecio());
			statement.setBoolean(3, servicio.isEstadoVigente());
			statement.setString(4, servicio.getNombre());
			
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
		} finally {
			ConnectionManager.disconnect();
		}
	}

	@Override
	public void remove(String nombre) {
		try {
			Connection conex = ConnectionManager.getConnection();
			
			PreparedStatement statement = conex.prepareStatement("DELETE FROM servicio WHERE nombre_servicio = ?");
			
			statement.setString(1, nombre);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
		} finally {
			ConnectionManager.disconnect();
		}
	}

	@Override
	public void remove(Servicio servicio) {
		if (servicio != null)
			this.remove(servicio.getNombre());
	}
	
	@Override
	public Servicio find(String nombre) throws EntidadNoEncontradaException {
		Servicio servicio = null;
		
		try {
			Connection conex = ConnectionManager.getConnection();
			
			PreparedStatement statement = conex.prepareStatement("SELECT s.nombre_servicio, s.descripcion, s.precio, s.estado_vigente " +
																"FROM servicio s " + "WHERE s.nombre_servicio = ?");
			
			statement.setString(1, nombre);
			
			ResultSet rs = statement.executeQuery();
			
			if (rs.next()) {
				try {
				servicio = new Servicio(rs.getString("nombre_servicio"), rs.getString("descripcion"), rs.getFloat("precio"),rs.getBoolean("estado_vigente"));
				} catch (ValorInvalidoException | DatoVacioException e) {
					
				}
			}
		} catch (SQLException e) {
			throw new EntidadNoEncontradaException("No se encontro el servicio: " + nombre);
		} finally {
			ConnectionManager.disconnect();
		}
		
		return servicio;
	}
	
	@Override
	public List<Servicio> findAll() {
		List<Servicio> servicios = new ArrayList<Servicio>();
		
		try {
			Connection conex = ConnectionManager.getConnection();
			
			PreparedStatement statement = conex.prepareStatement("SELECT s.nombre_servicio, s.descripcion, s.precio, s.estado_vigente " +
																"FROM servicio s");
			ResultSet rs = statement.executeQuery();
			
			while (rs.next()) {
				try {
					servicios.add(new Servicio(rs.getString("nombre_servicio"), rs.getString("descripcion"), rs.getFloat("precio"), rs.getBoolean("estado_vigente")));
				} catch (ValorInvalidoException | DatoVacioException e) {
					// TODO: handle exception
				}
			}
		} catch (SQLException e) {
			// TODO: handle exception
		} finally {
			ConnectionManager.disconnect();
		}
		return servicios;
	}

}
*/
