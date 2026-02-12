package ar.edu.unrn.seminario.accesos;

import java.sql.SQLException;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.modelo.OrdenEntrega;
import ar.edu.unrn.seminario.modelo.OrdenRetiro;

public interface OrdenEntregaDAO {
	

	
		void create(OrdenEntrega orden)throws DAOException;

		void update(OrdenEntrega orden)throws DAOException;

		void remove(String codigo)throws DAOException;

		void remove(OrdenEntrega orden)throws DAOException;

		OrdenEntrega find(String codigo)throws DAOException;

		List<OrdenEntrega> findAll()throws DAOException;
		
		int obtenerCantidadOE() throws SQLException;
	
		int obtenerMaximoOrdenEntrega() throws SQLException;
}
