package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.*;
import ar.edu.unrn.seminario.modelo.Plan;
import ar.edu.unrn.seminario.modelo.Servicio;

public class PlanDAOJDBC implements PlanDAO {
									//utilice try-with-resoures
	
	
	
	@Override
	public void create(Plan plan) throws EntidadDuplicadaException, AccesoDatosException {
		String sql = "INSERT INTO plan(plan_nombre, costo, estado_activo) VALUES (?, ?, ?)";

		Connection conex = ConnectionManager.getConnection();

		try {
			conex.setAutoCommit(false);                       // transacciones

			try (PreparedStatement statement = conex.prepareStatement(sql)) {
				statement.setString(1, plan.getNombre());
				statement.setFloat(2, plan.getCosto());
				statement.setBoolean(3, plan.isEstadoActivo());
				statement.executeUpdate();
			}

			cargarPlan_servicio(plan.getNombre(), plan.getServicios());

			conex.commit();                                   // guarda td junto

		}
		catch (SQLException e) {
			try {
				conex.rollback();                             // si algo falla reinicia td 
			} 
			catch (SQLException ex) {ex.printStackTrace();}
			
			if (e.getErrorCode() == 1062) throw new EntidadDuplicadaException("Ya existe un plan con el nombre: " + plan.getNombre());
			
			throw new AccesoDatosException("Error al registrar el plan", e);
		} 
		finally {
			try {
				conex.setAutoCommit(true);
			} 
			catch (SQLException e) {
				e.printStackTrace();//para q vea el error en eclipse si pasa
			}
			ConnectionManager.disconnect();
		}
	}
	
	
	@Override
	public void update(Plan plan) throws EntidadNoEncontradaException, AccesoDatosException {
		String sql = "UPDATE plan SET costo = ?, estado_activo = ? WHERE plan_nombre = ?";

		Connection conex = ConnectionManager.getConnection();

		try {
			conex.setAutoCommit(false);                 

			try (PreparedStatement statement = conex.prepareStatement(sql)) {
				statement.setFloat(1, plan.getCosto());
				statement.setBoolean(2, plan.isEstadoActivo());
				statement.setString(3, plan.getNombre());

				int filas = statement.executeUpdate();

				if (filas == 0) {
					throw new EntidadNoEncontradaException("No se encontró el plan: " + plan.getNombre());
				}
			}

			borrarPlan_servicio(plan.getNombre());
			cargarPlan_servicio(plan.getNombre(), plan.getServicios());

			conex.commit();                                   // se guarda todo junto

		} catch (SQLException e) {
			try {
				conex.rollback();                             // si algo falló, se deshace todo
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			throw new AccesoDatosException("Error al modificar el plan", e);

		} finally {
			try {
				conex.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ConnectionManager.disconnect();
		}
	}
	

	
	
	@Override
	public void remove(Plan plan) throws  EntidadNoEncontradaException, AccesoDatosException, EntidadEnUsoException {
		if(plan==null||plan.getNombre().trim().isEmpty()) 
					throw new EntidadNoEncontradaException("Plan nulo. Ingrese otro");
		
		remove(plan.getNombre());
	}
	
	
	
	@Override
	public void remove(String nombre) throws EntidadNoEncontradaException, AccesoDatosException, EntidadEnUsoException {
		String sql = "DELETE FROM plan WHERE plan_nombre = ?";

		Connection conex = ConnectionManager.getConnection();

		try {
			conex.setAutoCommit(false);

			// Primero las filas que dependen del plan...
			borrarPlan_servicio(nombre);

			// ...y después el plan.
			try (PreparedStatement statement = conex.prepareStatement(sql)) {
				statement.setString(1, nombre);
				int filas = statement.executeUpdate();

				if (filas == 0) {
					throw new EntidadNoEncontradaException("No se encontró el plan: " + nombre);
				}
			}

			conex.commit();

		} catch (SQLException e) {
			if (e.getErrorCode() == 1451) {
				throw new EntidadEnUsoException("No se puede eliminar el Plan '" + nombre + "' porque pertenece a una reserva");
			}
			try {
				conex.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			throw new AccesoDatosException("Error al eliminar el plan", e);

		} catch (EntidadNoEncontradaException e) {
			try {
				conex.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			throw e;

		} finally {
			try {
				conex.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ConnectionManager.disconnect();
		}
	}
	
	
	
	@Override
	public Plan find(String nombre) throws EntidadNoEncontradaException, AccesoDatosException, DatoVacioException, ValorInvalidoException {

	String sql = "SELECT p.plan_nombre, p.costo, p.estado_activo, "
			+ "s.nombre_servicio, s.descripcion, s.precio, s.estado_vigente "
			+ "FROM plan p "
			+ "JOIN plan_servicio ps ON p.plan_nombre = ps.plan_nombre "
			+ "JOIN servicio s ON ps.nombre_servicio = s.nombre_servicio "
			+ "WHERE p.plan_nombre = ?";
	Plan plan =null;

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
							nombrePlan = rs.getString("plan_nombre");
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
					
					plan =new Plan(servicios, costo, nombrePlan, activo);
				
		} 
	}catch(SQLException e) {throw new AccesoDatosException("Error al intentar eliminar el servicio", e);}
	
	return plan;
	}	
	@Override
	public List<Plan> findAll() throws AccesoDatosException, ValorInvalidoException, DatoVacioException, EntidadDuplicadaException  {
		String sql = "SELECT p.plan_nombre, p.costo, p.estado_activo, "
					  + "s.nombre_servicio, s.descripcion, s.precio, s.estado_vigente "
					  + "FROM plan p "
					  + "JOIN plan_servicio ps ON p.plan_nombre = ps.plan_nombre "
					  + "JOIN servicio s ON ps.nombre_servicio = s.nombre_servicio";

		List<Plan> planes = new ArrayList<>();

		try (PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(sql);ResultSet rs = statement.executeQuery()) 
		{
					while (rs.next())
					{
							String nombrePlan = rs.getString("plan_nombre");
							Servicio servicio = new Servicio(rs.getString("nombre_servicio"),
							rs.getString("descripcion"),rs.getFloat("precio"),rs.getBoolean("estado_vigente"));
							
							Plan plan = planes.stream().filter(p -> p.getNombre()
										.equals(nombrePlan)).findFirst().orElse(null);

							if (plan == null) {
								planes.add(new Plan(List.of(servicio),rs.getFloat("costo"),
											nombrePlan,rs.getBoolean("estado_activo")));
							} else plan.agregarServicio(servicio);
					}
					ConnectionManager.disconnect();
		} catch(SQLException e) {
			
			throw new AccesoDatosException("error al retirar todos los elementos"+ e.toString());
		}
		return planes;
	}
	
	
	private void cargarPlan_servicio(String nombre_plan, List<Servicio> servicios) throws SQLException {
		String sql = "INSERT INTO plan_servicio(plan_nombre, nombre_servicio) VALUES (?, ?)";

		try (PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(sql)) {
			for (Servicio servicio : servicios) {
				statement.setString(1, nombre_plan);
				statement.setString(2, servicio.getNombre());
				statement.addBatch();       // acumula el insert
			}
			statement.executeBatch();       // manda todos juntos
		}
	}
	
	private void borrarPlan_servicio(String nombre_plan) throws SQLException {
		String sql = "DELETE FROM plan_servicio WHERE plan_nombre = ?";

		try (PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(sql)) {
			statement.setString(1, nombre_plan);
			statement.executeUpdate();
		}
	}
}