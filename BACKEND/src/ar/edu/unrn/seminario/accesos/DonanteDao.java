package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.modelo.Donante;


public interface DonanteDao {
	void create(Donante Donante);

	void update(Donante Donante);

	void remove(String username);

	void remove(Donante Donante);

	Donante find(String codigo);

	List<Donante> findAll();
}
