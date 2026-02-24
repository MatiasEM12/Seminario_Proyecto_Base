package ar.edu.unrn.seminario.accesos;

import java.sql.SQLException;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.modelo.OrdenRetiro;

public interface OrdenRetiroDao {
	void create(OrdenRetiro orden)throws DAOException;

	void update(OrdenRetiro orden)throws DAOException;

	void remove(String codigo)throws DAOException;

	void remove(OrdenRetiro orden)throws DAOException;

	OrdenRetiro find(String codigo)throws DAOException;

	List<OrdenRetiro> findAll()throws DAOException;
	
	int obtenerCantidadOR() throws SQLException;
	
	int obtenerMaximoOrdenRetiro() throws SQLException;
	public List<OrdenRetiro> findAllByVoluntario(String codVoluntario) throws DAOException;
}
