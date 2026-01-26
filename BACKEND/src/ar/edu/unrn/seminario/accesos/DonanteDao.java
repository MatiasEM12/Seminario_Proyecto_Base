package ar.edu.unrn.seminario.accesos;

import java.sql.SQLException;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;
import ar.edu.unrn.seminario.modelo.Donante;

public interface DonanteDao {
	void create(Donante Usuario) throws DAOException;

	void update(Donante Usuario) throws DAOException;

	void remove(Long id);
	
	void remove(String codigo) throws DAOException, DataNullException;
	
	void remove(Donante Usuario) throws DAOException, DataNullException;

	Donante find(String codigo) throws DataNullException, DataEmptyException, DataObjectException, DataDateException, DAOException;


	List<Donante> findAll() throws DAOException;
	public int obtenerCantidadDonantes() throws SQLException ;
	


}