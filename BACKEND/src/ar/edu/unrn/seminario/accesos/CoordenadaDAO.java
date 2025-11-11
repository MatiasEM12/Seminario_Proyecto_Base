package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.modelo.Coordenada;


public interface CoordenadaDAO {
	void create(Coordenada coordenada);

	void update(Coordenada coordenada);

	void remove(Long id);
	
	void remove(String codigo);

	void remove(Coordenada coordenada);

	Coordenada find(String codigo);

	List<Coordenada> findAll();

}
