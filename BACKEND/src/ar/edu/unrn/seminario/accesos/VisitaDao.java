package ar.edu.unrn.seminario.accesos;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Visita;

public interface VisitaDao {
	void create(Visita visita)throws DAOException;

	void update(Visita visita)throws DAOException;

	void remove(String codigo)throws DAOException;

	void remove(Visita visita )throws DAOException;

	Visita find(String username) throws DataNullException, DataLengthException, DAOException;

	List<Visita> findAll() throws DataNullException, DataLengthException, DAOException;

	ArrayList<Visita> findAll(String codOrdenRetiro) throws DataNullException, DataLengthException, DAOException;
}
