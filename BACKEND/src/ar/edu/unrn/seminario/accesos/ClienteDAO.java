package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.AccesoDatosException;
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadDuplicadaException;
import ar.edu.unrn.seminario.exception.EntidadEnUsoException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;
import ar.edu.unrn.seminario.modelo.Cliente;

public interface ClienteDAO {
	
    void create (Cliente cliente) throws EntidadDuplicadaException, AccesoDatosException;
	
	void update (Cliente cliente) throws EntidadNoEncontradaException, AccesoDatosException;
	
	void remove (int dni) throws EntidadNoEncontradaException, AccesoDatosException, EntidadEnUsoException;
	
	void remove (Cliente cliente) throws EntidadNoEncontradaException, AccesoDatosException, EntidadEnUsoException;
	
	Cliente find (int dni) throws EntidadNoEncontradaException, DatoVacioException, ValorInvalidoException, AccesoDatosException;
	
	List<Cliente> findAll() throws ValorInvalidoException, DatoVacioException, AccesoDatosException;
}
