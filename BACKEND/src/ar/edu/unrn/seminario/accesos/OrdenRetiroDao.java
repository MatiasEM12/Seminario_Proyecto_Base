package ar.edu.unrn.seminario.accesos;

import java.util.List;
import ar.edu.unrn.seminario.modelo.OrdenRetiro;

public interface OrdenRetiroDao {
	void create(OrdenRetiro orden);

	void update(OrdenRetiro orden);

	void remove(String codigo);

	void remove(OrdenRetiro orden);

	OrdenRetiro find(String codigo);

	List<OrdenRetiro> findAll();
}
