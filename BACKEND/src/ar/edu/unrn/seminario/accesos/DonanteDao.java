package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;
import ar.edu.unrn.seminario.modelo.Donante;

public interface DonanteDao {
	void create(Donante Usuario);

	void update(Donante Usuario);

	void remove(Long id);

	void remove(Donante Usuario);

	Donante find(String codigo) throws DataEmptyException, DataObjectException, DataNullException, DataDateException;


	List<Donante> findAll() throws DataEmptyException, DataObjectException, DataNullException, DataDateException;

}