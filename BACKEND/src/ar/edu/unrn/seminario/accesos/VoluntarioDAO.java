package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.modelo.Voluntario;

public interface VoluntarioDAO {
	
	void create(Voluntario voluntario);

	void update(Voluntario voluntario);

	void remove(Long id);
	
	void remove(String codigo);

	void remove(Voluntario voluntario);

	Voluntario find(String codigo);

	List<Voluntario> findAll();
}
