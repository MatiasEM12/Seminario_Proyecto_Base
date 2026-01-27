package ar.edu.unrn.seminario.accesos;

import java.sql.SQLException;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.StateChangeException;
import ar.edu.unrn.seminario.modelo.Rol;

public interface RolDao {
	void create(Rol rol)throws DAOException;

	void update(Rol rol)throws DAOException;

	void remove(Integer codigo)throws DAOException;

	void remove(Rol rol)throws DAOException;

	Rol find(Integer codigo)throws DAOException;

	List<Rol> findAll() throws DataNullException, DAOException;
	
	int obtenerCantidadRoles() throws SQLException;
	

}