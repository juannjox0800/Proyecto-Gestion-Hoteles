package ar.edu.unrn.seminario.accesos;

import java.sql.SQLException;
import java.util.List;

import ar.edu.unrn.seminario.exception.DatoVacioException;
import ar.edu.unrn.seminario.exception.EntidadDuplicadaException;
import ar.edu.unrn.seminario.exception.EntidadNoEncontradaException;
import ar.edu.unrn.seminario.exception.ValorInvalidoException;
import ar.edu.unrn.seminario.modelo.Plan;


public interface PlanDAO {

	void create(Plan plan) throws EntidadDuplicadaException;

	void update(Plan plan) throws  EntidadNoEncontradaException;

	void remove(Plan plan) throws  EntidadNoEncontradaException;

	void remove(String nombres) throws  EntidadNoEncontradaException;

	Plan find(String nombre) throws  EntidadNoEncontradaException;

	List<Plan> findAll();

}
