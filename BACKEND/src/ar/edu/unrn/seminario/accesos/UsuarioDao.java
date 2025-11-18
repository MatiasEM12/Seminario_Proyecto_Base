package ar.edu.unrn.seminario.accesos;

import java.sql.SQLException;
import java.util.List;

import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Usuario;

public interface UsuarioDao {
	void create(Usuario Usuario);

	void update(Usuario Usuario);

	void remove(Long id);

	void remove(Usuario Usuario);

	Usuario find(String username);

	List<Usuario> findAll() throws DataNullException;
   int obtenerCantidadUsuarios() throws SQLException;
}