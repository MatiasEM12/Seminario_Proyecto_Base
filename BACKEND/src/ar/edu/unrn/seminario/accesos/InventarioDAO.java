package ar.edu.unrn.seminario.accesos;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.modelo.Bien;


public interface InventarioDAO {
	
	 void create(String codBien,String tipoBien, boolean disponible) throws DAOException;

	void update(String codBien, String tipoBien, boolean disponible) throws DAOException;

	void remove(String codBien) throws DAOException;

	Bien findBien(String codBien) throws DAOException;

	List<Bien> findAll() throws DAOException;

	List<Bien> findBienesDisponibles() throws DAOException;

	List<Bien> findBienesNoDisponibles() throws DAOException;
	

}
