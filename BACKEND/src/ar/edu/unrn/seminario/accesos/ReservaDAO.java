package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.AccesoDatosException;
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadDuplicadaException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;
import ar.edu.unrn.seminario.modelo.Reserva;

public interface ReservaDAO {
	void create (Reserva reserva )throws AccesoDatosException, EntidadDuplicadaException;
	
	void update (Reserva reserva) throws AccesoDatosException, EntidadNoEncontradaException;
	
	void remove (int numero) throws AccesoDatosException, EntidadNoEncontradaException;
	
	Reserva find (int numero) throws AccesoDatosException, EntidadNoEncontradaException, ValorInvalidoException, DatoVacioException;
	
	List <Reserva> findAll() throws DatoVacioException, DatoVacioException;
}

