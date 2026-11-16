package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.AccesoDatosException;
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadDuplicadaException;
import ar.edu.unrn.seminario.exception.EntidadEnUsoException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;
import ar.edu.unrn.seminario.modelo.Cliente;

public class ClienteDAOJDBC implements ClienteDAO{
	@Override
	public void create(Cliente cliente) throws EntidadDuplicadaException, AccesoDatosException {
		try {
			Connection conex = ConnectionManager.getConnection();

			PreparedStatement statement = conex.prepareStatement(
					"INSERT INTO cliente(dni_cliente, nombre, apellido, contacto) VALUES (?, ?, ?, ?)");

			statement.setInt(1, cliente.getDni());
			statement.setString(2, cliente.getNombre());
			statement.setString(3, cliente.getApellido());
			statement.setString(4, cliente.getContacto());

			statement.executeUpdate();
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
			throw new EntidadDuplicadaException("Ya existe un cliente con el dni: " + cliente.getDni() );
			}
			throw new AccesoDatosException("Error al intentar crear el cliente", e);
		} finally {
			ConnectionManager.disconnect();
		}
	}
	
	public void update (Cliente cliente) throws EntidadNoEncontradaException, AccesoDatosException {
	try {
		Connection conex = ConnectionManager.getConnection();

		PreparedStatement statement = conex.prepareStatement(
				"UPDATE cliente SET nombre = ?, apellido = ?, contacto = ? WHERE dni_cliente = ?");

		statement.setString(1, cliente.getNombre());
		statement.setString(2, cliente.getApellido());
		statement.setString(3, cliente.getContacto());
		statement.setInt(4, cliente.getDni());

		int filas = statement.executeUpdate();
		
		if (filas== 0) {
			throw new EntidadNoEncontradaException ("No se encontró el cliente con el dni: " + cliente.getDni());
		}
	} catch (SQLException e) {
		throw new AccesoDatosException("Error al intentar modificar el cliente", e);
	} finally {
		ConnectionManager.disconnect();
	}
	}

	public void remove (int dni) throws EntidadNoEncontradaException, EntidadEnUsoException, AccesoDatosException {
	try {
		Connection conex = ConnectionManager.getConnection();

		PreparedStatement statement = conex.prepareStatement("DELETE FROM cliente WHERE dni_cliente = ?");

		statement.setInt(1, dni);
        int filas = statement.executeUpdate();
        if (filas==0) {
        	throw new EntidadNoEncontradaException("No se encontró el cliente a eliminar con el dni: " + dni);
    		
        }
	} catch (SQLException e) {
		if (e.getErrorCode() == 1451) {
		throw new EntidadEnUsoException("No se puede eliminar el cliente con el dni " + dni + " porque tiene reservas");
		}
		throw new AccesoDatosException("Error al intentar eliminar el cliente", e);
	
	} finally {
		ConnectionManager.disconnect();
	}
	}
	public void remove (Cliente cliente) throws EntidadNoEncontradaException, EntidadEnUsoException, AccesoDatosException {
		if (cliente!=null)
			this.remove(cliente.getDni());
	}
	
	@Override
	public Cliente find(int dni) throws EntidadNoEncontradaException, ValorInvalidoException, DatoVacioException, AccesoDatosException {
		Cliente cliente = null;

		try {
			Connection conex = ConnectionManager.getConnection();

			PreparedStatement statement = conex.prepareStatement(
					"SELECT nombre, apellido, contacto, dni_cliente FROM cliente WHERE dni_cliente = ?");

			statement.setInt(1, dni);

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
					cliente = new Cliente(rs.getString("nombre"), rs.getString("apellido"),
							rs.getString("contacto"), rs.getInt("dni_cliente"));
			}else {
				throw new EntidadNoEncontradaException("No se encontró el cliente con el dni: " + dni);
			}	
			
		} catch (SQLException e) {
			throw new AccesoDatosException("Error al intentar encontrar el cliente", e);
		} finally {
			ConnectionManager.disconnect();
		}
		
		return cliente;
	}
	
	@Override
	public List<Cliente> findAll() throws AccesoDatosException, DatoVacioException, ValorInvalidoException {
		List<Cliente> clientes = new ArrayList<Cliente>();

		try {
			Connection conex = ConnectionManager.getConnection();

			PreparedStatement statement = conex.prepareStatement(
					"SELECT nombre, apellido, contacto, dni_cliente FROM cliente ");

			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				
					clientes.add(new Cliente(rs.getString("nombre"), rs.getString("apellido"),
							rs.getString("contacto"), rs.getInt("dni_cliente")));
			}
			
		} catch (SQLException e) {
			throw new AccesoDatosException("Error al intentar encontrar el cliente", e);
		} finally {
			ConnectionManager.disconnect();
		}
		return clientes;
	}
		}
		

