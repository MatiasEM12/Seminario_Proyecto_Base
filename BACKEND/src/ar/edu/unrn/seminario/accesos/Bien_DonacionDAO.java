package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataDoubleException;
import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.StateChangeException;
import ar.edu.unrn.seminario.modelo.Bien;

public interface Bien_DonacionDAO {
	void create(String codBien, String codDonacion) throws DAOException;



	void remove(Long id);
	
	void remove(String codigoDonacion);


	List<Bien> findAll();

	List<Bien> findDonacion(String codDonacion) throws DataNullException, DataDoubleException, DAOException, StateChangeException, DataLengthException, DataDateException;

	void update(String codBienNuevo, String codBienViejo, String codDonacion) throws DAOException;

}
