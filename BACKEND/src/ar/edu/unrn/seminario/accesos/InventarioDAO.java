package ar.edu.unrn.seminario.accesos;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.modelo.Bien;


public interface InventarioDAO {
	void create(Bien bien) throws DAOException;

	void update(Bien bien) throws DAOException;

	public void cambiarEntrega(String codigo) throws DAOException;

	void remove(Long id);
	
	void remove(String codigo) throws DAOException;

	void remove(Bien bien) throws DAOException;

	Bien find(String codigo)throws DAOException;

	List<Bien> findAll()throws DAOException;

	ArrayList<Bien> findBienDonacion(String codDonacion) throws DAOException;

	ArrayList<Bien> findBienVisita(String codVisita) throws DAOException;
	
	List<Bien> findALLTipo(String tipo) throws DAOException;
}
