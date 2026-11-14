package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.EntidadDuplicadaException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.modelo.Cliente;

public interface ClienteDAO {
void create (Cliente cliente) throws EntidadDuplicadaException;
	
	void update (Cliente cliente) throws EntidadNoEncontradaException;
	
	void remove (int dni);
	
	void remove (Cliente cliente);
	
	Cliente find (int dni) throws EntidadNoEncontradaException;
	
	List<Cliente> findAll();
}
