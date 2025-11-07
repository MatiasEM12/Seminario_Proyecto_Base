package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Visita;

public interface VisitaDao {
	void create(Visita visita);

	void update(Visita visita);

	void remove(String id);

	void remove(Visita visita );

	Visita find(String username) throws DataNullException, DataLengthException;

	List<Visita> findAll() throws DataNullException, DataLengthException;
}
