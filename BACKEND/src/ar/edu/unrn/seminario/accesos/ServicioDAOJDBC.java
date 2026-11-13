package ar.edu.unrn.seminario.accesos;

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
