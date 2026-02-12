package ar.edu.unrn.seminario.accesos;

import java.sql.SQLException;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.modelo.OrdenPedido;

public interface OrdenPedidoDao {
	void create(OrdenPedido Usuario)throws DAOException;

	void update(OrdenPedido Usuario)throws DAOException;

	void remove(String id)throws DAOException;

	void remove(OrdenPedido Usuario)throws DAOException;

	OrdenPedido find(String username)throws DAOException;

	List<OrdenPedido> findAll() throws DAOException;

	int obtenerCantidadOP() throws SQLException;
	
	int obtenerMaximoOrdenPedido() throws SQLException;
}
