package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.AccesoDatosException;
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadDuplicadaException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;
import ar.edu.unrn.seminario.modelo.Habitacion;


public interface HabitacionDao {
	
	
	public void create(Habitacion habitacion) throws EntidadDuplicadaException, AccesoDatosException;
		
	public void update(Habitacion habitacion) throws EntidadNoEncontradaException, AccesoDatosException;
	
	public void remove(int numero) throws EntidadNoEncontradaException, AccesoDatosException;
	
	public void remove(Habitacion habitacion) throws EntidadNoEncontradaException, AccesoDatosException;
		
	public Habitacion find(int numero) throws AccesoDatosException, DatoVacioException, ValorInvalidoException;
		
	public List<Habitacion> findAll() throws AccesoDatosException, DatoVacioException, ValorInvalidoException;
		
}
