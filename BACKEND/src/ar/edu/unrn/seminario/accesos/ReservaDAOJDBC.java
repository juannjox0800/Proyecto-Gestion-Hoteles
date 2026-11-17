package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.AccesoDatosException;
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadDuplicadaException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;
import ar.edu.unrn.seminario.modelo.Reserva;

public class ReservaDAOJDBC implements ReservaDAO{

	public void create(Reserva reserva) throws AccesoDatosException, EntidadDuplicadaException {
		// TODO Auto-generated method stub
		
	}

	public void update(Reserva reserva) throws AccesoDatosException, EntidadNoEncontradaException {
		// TODO Auto-generated method stub
		
	}

	public void remove(int numero) throws AccesoDatosException, EntidadNoEncontradaException {
		// TODO Auto-generated method stub
		
	}

	public Reserva find(int numero)
			throws AccesoDatosException, EntidadNoEncontradaException, ValorInvalidoException, DatoVacioException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Reserva> findAll() throws DatoVacioException, DatoVacioException {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
