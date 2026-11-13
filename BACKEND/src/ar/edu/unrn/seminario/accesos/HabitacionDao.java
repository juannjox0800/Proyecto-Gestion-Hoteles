package ar.edu.unrn.seminario.accesos;

import java.util.List;
import ar.edu.unrn.seminario.modelo.Habitacion;


public interface HabitacionDao {
	
	
	public void create(Habitacion habitacion);
		
	public void update(Habitacion habitacion);
	
	public void remove(int numero);
	
	public void remove(Habitacion habitacion);
		
	public Habitacion find(int numero);
		
	public List<Habitacion> findAll();
		
}
