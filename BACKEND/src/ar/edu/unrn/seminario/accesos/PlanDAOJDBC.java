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
	public void create(Plan plan) throws EntidadDuplicadaException{
		String sql = "INSERT INTO Plan(nombre_plan, costo,  estado_activo) VALUES (?, ?, ?, ?)";
		
		try(PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(sql)){
					statement.setString(1, plan.getNombre());
					statement.setFloat(2,plan.getCosto());
					statement.setBoolean(3, plan.isEstadoActivo());
					statement.executeUpdate();
					ConnectionManager.disconnect();
		}catch (SQLException e) {//errorcode 1062 es duplicado
					if(e.getErrorCode()==1062) throw new EntidadDuplicadaException("Ya existe un plan con el nombre: " + plan.getNombre());
					throw new RuntimeException("Error al registrar el plan", e);
		}
	}

	
	
	
	@Override
	public void update(Plan plan) throws  EntidadNoEncontradaException {
		String sql =  "UPDATE plan SET costo=?, estado_activo=? WHERE nombre_plan";
		
		try(PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(sql))
		{
					statement.setFloat(1,plan.getCosto());
					statement.setBoolean(2, plan.isEstadoActivo());
					statement.setString(3, plan.getNombre());
					statement.executeUpdate();
					
					int filas= statement.executeUpdate();
					
					if(filas==0)throw new EntidadNoEncontradaException("No se encontro el plan: "+plan.getNombre());
					
					ConnectionManager.disconnect();
		}catch(SQLException e) {throw new RuntimeException("Error al modificar el plan", e);
		}
	}
	

	
	
	@Override
	public void remove(Plan plan) throws  EntidadNoEncontradaException {
		if(plan==null||plan.getNombre().trim().isEmpty()) 
					throw new EntidadNoEncontradaException("plan nulo ingresa otro");
		
		remove(plan.getNombre());
	}
	
	
	
	@Override
	public void remove(String nombre) throws  EntidadNoEncontradaException {
		String sql = "DELETE FROM plan where nombre_plan=?";
		
		try(PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(sql))
		{
			statement.setString(1, nombre);
			int filas= statement.executeUpdate();
			if(filas==0)throw new EntidadNoEncontradaException("No se encontro el plan: "+nombre);
		}catch(SQLException e) {throw new RuntimeException("Error al eliminar el plan", e);

		}
	}

	
	
	
	@Override
	public Plan find(String nombre) throws EntidadNoEncontradaException {

	String sql = "SELECT p.nombre_plan, p.costo, p.estado_activo, "
			+ "s.nombre_servicio, s.descripcion, s.precio, s.estado_vigente "
			+ "FROM plan p "
			+ "JOIN plan_servicio ps ON p.nombre_plan = ps.plan_nombre "
			+ "JOIN servicio s ON ps.nombre_servicio = s.nombre_servicio "
			+ "WHERE p.nombre_plan = ?";
	Plan plan =null;

	try (PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(sql)) {
		statement.setString(1, nombre);
	
		try (ResultSet rs = statement.executeQuery()) {
			String nombrePlan = null;
			float costo = 0;
			boolean activo = false;
			boolean encontrado = false;
			List<Servicio> servicios = new ArrayList<>();
			try {
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
			plan =new Plan(servicios, costo, nombrePlan, activo);
				}catch(ValorInvalidoException|DatoVacioException e) {
					JOptionPane.showMessageDialog(null,"error,"+ e.toString());
			if (!encontrado) {
				throw new EntidadNoEncontradaException("No se encontró el plan: " + nombre);
			}
			
		}
		} }catch(SQLException e) {
			
		}
	return plan;
	}	
	@Override
	public List<Plan> findAll()  {
		String sql = "SELECT p.nombre_plan, p.costo, p.estado_activo, "
					  + "s.nombre_servicio, s.descripcion, s.precio, s.estado_vigente "
					  + "FROM plan p "
					  + "JOIN plan_servicio ps ON p.nombre_plan = ps.plan_nombre "
					  + "JOIN servicio s ON ps.nombre_servicio = s.nombre_servicio";

		List<Plan> planes = new ArrayList<>();

		try (PreparedStatement statement = ConnectionManager.getConnection().prepareStatement(sql);ResultSet rs = statement.executeQuery()) 
		{
					while (rs.next())
					{
							String nombrePlan = rs.getString("nombre_plan");
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
			JOptionPane.showMessageDialog(null,"error al retirar todos los elementos"+ e.toString());
		}catch( ValorInvalidoException| DatoVacioException| EntidadDuplicadaException e) {	
			JOptionPane.showMessageDialog(null,"Valores invalidos en datos a retirar"+ e.toString());
		}
		return planes;
	}
	
	
}