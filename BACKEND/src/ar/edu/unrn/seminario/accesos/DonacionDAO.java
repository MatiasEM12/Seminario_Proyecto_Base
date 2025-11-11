package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.modelo.Donacion;


public interface DonacionDAO {

	void create(Donacion donacion);

	void update(Donacion donacion);

	void remove(Long id);
	
	void remove(String codigo);

	void remove(Donacion donacion);

	Donacion find(String codigo);

	List<Donacion> findAll();

}
