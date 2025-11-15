package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.StateChangeException;
import ar.edu.unrn.seminario.modelo.OrdenPedido;

public interface OrdenPedidoDao {
	void create(OrdenPedido Usuario);

	void update(OrdenPedido Usuario);

	void remove(String id);

	void remove(OrdenPedido Usuario);

	OrdenPedido find(String username) throws DataNullException, DataLengthException, StateChangeException;

	List<OrdenPedido> findAll() throws StateChangeException, DataNullException, DataLengthException;

}
