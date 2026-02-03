package ar.edu.unrn.seminario.accesos;

import java.sql.SQLException;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataExistsException;
import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;
import ar.edu.unrn.seminario.modelo.Usuario;

public interface UsuarioDao {
	void create(Usuario Usuario)throws DAOException, DataExistsException;

	void update(Usuario Usuario)throws DAOException;

	void remove(Long id);

	void remove(Usuario Usuario)throws DAOException;

	Usuario find(String username)throws DAOException;

	List<Usuario> findAll() throws DataNullException, DAOException, DataObjectException, DataLengthException;
    int obtenerCantidadUsuarios() throws SQLException;
}