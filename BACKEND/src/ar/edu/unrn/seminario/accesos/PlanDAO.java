package ar.edu.unrn.seminario.accesos;

import java.sql.SQLException;
import java.util.List;

import ar.edu.unrn.seminario.exception.AccesoDatosException;
import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadDuplicadaException;
import ar.edu.unrn.seminario.exception.EntidadEnUsoException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;
import ar.edu.unrn.seminario.modelo.Plan;


public interface PlanDAO {

	void create(Plan plan) throws EntidadDuplicadaException, AccesoDatosException;

	void update(Plan plan) throws  EntidadNoEncontradaException, AccesoDatosException;

	void remove(Plan plan) throws  EntidadNoEncontradaException, AccesoDatosException, EntidadEnUsoException;

	void remove(String nombres) throws  EntidadNoEncontradaException, AccesoDatosException, EntidadEnUsoException;

	Plan find(String nombre) throws  EntidadNoEncontradaException, AccesoDatosException, DatoVacioException, ValorInvalidoException;

	List<Plan> findAll() throws AccesoDatosException, ValorInvalidoException, DatoVacioException, EntidadDuplicadaException;

}
