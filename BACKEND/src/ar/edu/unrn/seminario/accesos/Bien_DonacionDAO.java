package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.DataDoubleException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Bien;

public interface Bien_DonacionDAO {
	void create(String codBien, String codDonacion);



	void remove(Long id);
	
	void remove(String codigoDonacion);


	List<Bien> findAll();

	List<Bien> findDonacion(String codDonacion) throws DataNullException, DataDoubleException;

	void update(String codBienNuevo, String codBienViejo, String codDonacion);

}
