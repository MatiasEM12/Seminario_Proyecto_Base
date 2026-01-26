package ar.edu.unrn.seminario.accesos;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Beneficiario;

public interface BeneficiarioDAO {
	abstract void create(Beneficiario beneficiario) throws DAOException;

	void update(Beneficiario beneficiario) throws DAOException;
	
	void remove(String codigo)throws DAOException;

	void remove(Beneficiario beneficiario)throws DAOException;

	Beneficiario find(String codigo)throws DataNullException, DAOException;

	List<Beneficiario> findAll()throws DAOException;

	ArrayList<Beneficiario> findBienDonacion(String codDonacion)throws DataNullException,DAOException;

	ArrayList<Beneficiario> findBienVisita(String codVisita)throws DataNullException,DAOException;
	
	List<Beneficiario> findALLTipo(String tipo)throws DataNullException,DAOException;
}
