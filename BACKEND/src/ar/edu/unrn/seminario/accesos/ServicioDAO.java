package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.EntidadDuplicadaException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.modelo.Servicio;

public interface ServicioDAO {
	void create (Servicio servicio) throws EntidadDuplicadaException;
	
	void update (Servicio servicio);
	
	void remove (String nombre);
	
	void remove (Servicio servicio);
	
	Servicio find (String nombre) throws EntidadNoEncontradaException;
	
	List<Servicio> findAll();
}
