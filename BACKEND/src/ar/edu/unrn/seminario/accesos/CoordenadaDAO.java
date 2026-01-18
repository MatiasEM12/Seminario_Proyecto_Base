package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Coordenada;


public interface CoordenadaDAO {
	void create(Coordenada coordenada)throws DAOException;

	void update(Coordenada coordenada)throws DAOException;

	void remove(Long id);
	
	void remove(String codigo)throws DataNullException, DAOException;

	void remove(Coordenada coordenada)throws DAOException;

	Coordenada find(String codigo)throws DataNullException,DAOException;

	List<Coordenada> findAll()throws DAOException;

}
