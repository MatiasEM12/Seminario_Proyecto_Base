package ar.edu.unrn.seminario.accesos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Bien;

public interface BienDAO {

	void create(Bien bien) throws DAOException;

	void update(Bien bien) throws DAOException;

	void remove(Long id);
	
	void remove(String codigo)throws DAOException;

	void remove(Bien bien)throws DAOException;

	Bien find(String codigo)throws DataNullException, DAOException;

	List<Bien> findAll()throws DAOException;

	ArrayList<Bien> findBienDonacion(String codDonacion)throws DataNullException,DAOException;

	ArrayList<Bien> findBienVisita(String codVisita)throws DataNullException,DAOException;
	
	List<Bien> findALLTipo(String tipo)throws DataNullException,DAOException;
	
	int obtenerCantidadBienes() throws SQLException;
}
