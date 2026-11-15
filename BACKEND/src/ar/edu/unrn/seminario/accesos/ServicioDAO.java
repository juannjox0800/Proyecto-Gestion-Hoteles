package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadDuplicadaException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;
import ar.edu.unrn.seminario.modelo.Servicio;

public interface ServicioDAO {
	void create (Servicio servicio) throws EntidadDuplicadaException;
	
	void update (Servicio servicio) throws EntidadNoEncontradaException;
	
	void remove (String nombre) throws EntidadNoEncontradaException;
	
	void remove (Servicio servicio) throws EntidadNoEncontradaException;
	
	Servicio find (String nombre) throws EntidadNoEncontradaException, ValorInvalidoException, DatoVacioException;
	
	List<Servicio> findAll() throws ValorInvalidoException, DatoVacioException;
}
