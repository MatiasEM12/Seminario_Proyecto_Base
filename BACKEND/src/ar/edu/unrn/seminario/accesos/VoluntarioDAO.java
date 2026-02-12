package ar.edu.unrn.seminario.accesos;

import java.sql.SQLException;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.modelo.Voluntario;

public interface VoluntarioDAO {
	
	void create(Voluntario voluntario)throws DAOException;

	void update(Voluntario voluntario)throws DAOException;

	void remove(Long id);
	
	void remove(String codigo)throws DAOException;

	void remove(Voluntario voluntario)throws DAOException;

	Voluntario find(String codigo)throws DAOException;

	List<Voluntario> findAll()throws DAOException;
	
	int obtenerCantidadVoluntarios() throws SQLException;
	int obtenerMaximoVoluntarios() throws SQLException;
}
