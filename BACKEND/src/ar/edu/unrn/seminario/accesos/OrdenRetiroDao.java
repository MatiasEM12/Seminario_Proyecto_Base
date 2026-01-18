package ar.edu.unrn.seminario.accesos;

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
}
