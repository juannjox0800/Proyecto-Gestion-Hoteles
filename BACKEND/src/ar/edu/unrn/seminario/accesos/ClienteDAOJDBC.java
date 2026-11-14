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
import ar.edu.unrn.seminario.modelo.Cliente;

public class ClienteDAOJDBC implements ClienteDAO{
	@Override
	public void create(Cliente cliente) throws EntidadDuplicadaException {
		try {
			Connection conex = ConnectionManager.getConnection();

			PreparedStatement statement = conex.prepareStatement(
					"INSERT INTO cliente(dni, nombre, apellido, contacto) VALUES (?, ?, ?, ?)");

			statement.setInt(1, cliente.getDni());
			statement.setString(2, cliente.getNombre());
			statement.setString(3, cliente.getApellido());
			statement.setString(4, cliente.getContacto());

			statement.executeUpdate();
		} catch (SQLException e) {
			throw new EntidadDuplicadaException("El cliente ya existe");
		} finally {
			ConnectionManager.disconnect();
		}
	}
	public void update (Cliente cliente) throws EntidadNoEncontradaException {
	try {
		Connection conex = ConnectionManager.getConnection();

		PreparedStatement statement = conex.prepareStatement(
				"UPDATE cliente SET nombre = ?, apellido = ?, contacto = ? WHERE dni = ?");

		statement.setString(1, cliente.getNombre());
		statement.setString(2, cliente.getApellido());
		statement.setString(3, cliente.getContacto());
		statement.setInt(4, cliente.getDni());

		statement.executeUpdate();
	} catch (SQLException e) {
		
	} finally {
		ConnectionManager.disconnect();
	}
	}
	public void remove (int dni) {
	try {
		Connection conex = ConnectionManager.getConnection();

		PreparedStatement statement = conex.prepareStatement("DELETE FROM cliente WHERE dni = ?");

		statement.setInt(1, dni);

	   statement.executeUpdate();
	} catch (SQLException e) {
		
	} finally {
		ConnectionManager.disconnect();
	}
	}
	public void remove (Cliente cliente) {
		if (cliente!=null)
			this.remove(cliente.getDni());
	}
	
	@Override
	public Cliente find(int dni) throws EntidadNoEncontradaException {
		Cliente cliente = null;

		try {
			Connection conex = ConnectionManager.getConnection();

			PreparedStatement statement = conex.prepareStatement(
					"SELECT c.nombre, c.apellido, c.contacto, c.dni FROM cliente c WHERE c.dni = ?");

			statement.setInt(1, dni);

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				try {
					cliente = new Cliente(rs.getString("nombre"), rs.getString("apellido"),
							rs.getString("contacto"), rs.getInt("dni"));
				} catch (DatoVacioException | ValorInvalidoException e) {
					
				}
			}
		} catch (SQLException e) {
			
		} finally {
			ConnectionManager.disconnect();
		}
		
		if (cliente == null) {
			throw new EntidadNoEncontradaException("No se encontró el cliente con el DNI: " + dni);
		}
		return cliente;
	}
	
	@Override
	public List<Cliente> findAll() {
		List<Cliente> clientes = new ArrayList<Cliente>();

		try {
			Connection conex = ConnectionManager.getConnection();

			PreparedStatement statement = conex.prepareStatement(
					"SELECT c.nombre, c.apellido, c.contacto, c.dni FROM cliente c");

			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				try {
					clientes.add(new Cliente(rs.getString("nombre"), rs.getString("apellido"),
							rs.getString("contacto"), rs.getInt("dni")));
				} catch (DatoVacioException | ValorInvalidoException e) {
				
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.disconnect();
		}
		return clientes;
	}
}

