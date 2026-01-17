package ar.edu.unrn.seminario.accesos;

import java.util.List;
import ar.edu.unrn.seminario.exception.*;


import ar.edu.unrn.seminario.modelo.Vehiculo;

public interface VehiculoDAO {

	void create(Vehiculo vehiculo) throws DAOException;

	void update(Vehiculo vehiculo) throws DAOException;
	
	void remove(String matricula) throws DAOException;

	Vehiculo find(String matricula)throws DAOException;

	List<Vehiculo> findAll()throws DAOException;
}
