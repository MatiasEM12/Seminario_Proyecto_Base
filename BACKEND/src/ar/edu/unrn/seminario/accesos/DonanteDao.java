package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.modelo.Donante;

public interface DonanteDao {
	void create(Donante Usuario);

	void update(Donante Usuario);

	void remove(Long id);

	void remove(Donante Usuario);

	Donante find(String username);

	List<Donante> findAll();

}