package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.modelo.Ubicacion;



public interface UbicacionDAO {

	void create(Ubicacion ubicacion);

	void update(Ubicacion ubicacion);

	void remove(Long id);
	
	void remove(String codigo);

	void remove(Ubicacion ubicacion);

	Ubicacion find(String codigo);

	List<Ubicacion> findAll();
}
