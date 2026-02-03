package ar.edu.unrn.seminario.accesos;

import java.sql.SQLException;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataListException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;
import ar.edu.unrn.seminario.modelo.Donacion;


public interface DonacionDAO {

	void create(Donacion donacion) throws DataNullException;

	void update(Donacion donacion);

	void remove(Long id);
	
	void remove(String codigo);

	void remove(Donacion donacion);

	Donacion find(String codigo);
	Donacion  findPorOrdenPedido(String codigoOrdenPedido) throws DataNullException;

	List<Donacion> findAll() throws DataNullException, DataEmptyException, DataObjectException, DataDateException, DAOException, DataLengthException, DataListException;
	List<Donacion> findAllPendiente() throws DataNullException, DataEmptyException, DataObjectException, DataDateException, DAOException, DataLengthException, DataListException;
	 
	int obtenerCantidadDonaciones() throws SQLException;

}
