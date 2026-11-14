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
		} finally {ConnectionManager.disconnect();}
	}	
	@Override
	public List<Plan> findAll() throws SQLException, ValorInvalidoException,
			DatoVacioException, EntidadDuplicadaException {
		String sql = "SELECT p.nombre_plan, p.costo, p.estado_activo, "
				+ "s.nombre_servicio, s.descripcion, s.precio, s.estado_vigente "
				+ "FROM plan p "
				+ "JOIN plan_servicio ps ON p.nombre_plan = ps.plan_nombre "
				+ "JOIN servicio s ON ps.nombre_servicio = s.nombre_servicio";

		List<Plan> planes = new ArrayList<>();

		try (PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(sql);
		     ResultSet rs = statement.executeQuery()) {

			while (rs.next()) {
				String nombrePlan = rs.getString("nombre_plan");

				Servicio servicio = new Servicio(
						rs.getString("nombre_servicio"),
						rs.getString("descripcion"),
						rs.getFloat("precio"),
						rs.getBoolean("estado_vigente"));

				Plan plan = planes.stream()
						.filter(p -> p.getNombre().equals(nombrePlan))
						.findFirst()
						.orElse(null);

				if (plan == null) {
					planes.add(new Plan(
							List.of(servicio),
							rs.getFloat("costo"),
							nombrePlan,
							rs.getBoolean("estado_activo")));
				} else {
					plan.agregarServicio(servicio);
				}
			}

		} finally {
			ConnectionManager.disconnect();
		}

		return planes;
	}
	
	
}