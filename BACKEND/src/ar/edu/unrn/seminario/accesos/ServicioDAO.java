package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.AccesoDatosException;
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadDuplicadaException;
import ar.edu.unrn.seminario.exception.EntidadEnUsoException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;
import ar.edu.unrn.seminario.modelo.Servicio;

public interface ServicioDAO {
	void create (Servicio servicio) throws EntidadDuplicadaException, AccesoDatosException;
	
	void update (Servicio servicio) throws EntidadNoEncontradaException, AccesoDatosException;
	
	void remove (String nombre) throws EntidadNoEncontradaException, AccesoDatosException, EntidadEnUsoException;
	
	void remove (Servicio servicio) throws EntidadNoEncontradaException, AccesoDatosException, EntidadEnUsoException;
	
	Servicio find (String nombre) throws EntidadNoEncontradaException, ValorInvalidoException, DatoVacioException, AccesoDatosException;
	
	List<Servicio> findAll() throws ValorInvalidoException, DatoVacioException, AccesoDatosException;
}
