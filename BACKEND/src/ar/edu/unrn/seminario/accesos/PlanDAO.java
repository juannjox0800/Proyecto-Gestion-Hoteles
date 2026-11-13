package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.modelo.Plan;


public interface PlanDAO {

	void create(Plan plan);

	void update(Plan plan);

	void remove(Plan plan);

	void remove(String nombres);

	Plan find(String nombre);

	List<Plan> findAll();

}
