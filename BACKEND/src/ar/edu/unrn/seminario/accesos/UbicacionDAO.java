package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Ubicacion;



public interface UbicacionDAO {

	void create(Ubicacion ubicacion) throws DAOException;

	void update(Ubicacion ubicacion)throws DAOException;

	void remove(Long id);
	
	void remove(String codigo)throws DAOException, DataNullException;

	void remove(Ubicacion ubicacion)throws DAOException, DataNullException;

	Ubicacion find(String codigo)throws DAOException;

	List<Ubicacion> findAll()throws DAOException;
}
