package ar.edu.unrn.seminario.accesos;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.modelo.Bien;

public interface BienDAO {

	void create(Bien bien);

	void update(Bien bien);

	void remove(Long id);
	
	void remove(String codigo);

	void remove(Bien bien);

	Bien find(String codigo);

	List<Bien> findAll();

	ArrayList<Bien> findBienDonacion(String codDonacion);

	ArrayList<Bien> findBienVisita(String codVisita);
}
