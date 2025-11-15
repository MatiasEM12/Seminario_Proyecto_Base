package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.StateChangeException;
import ar.edu.unrn.seminario.modelo.OrdenRetiro;

public interface OrdenRetiroDao {
	void create(OrdenRetiro orden);

	void update(OrdenRetiro orden);

	void remove(String codigo);

	void remove(OrdenRetiro orden);

	OrdenRetiro find(String username) throws DataNullException, DataLengthException, StateChangeException;

	List<OrdenRetiro> findAll() throws DataNullException, DataLengthException, StateChangeException;
}
