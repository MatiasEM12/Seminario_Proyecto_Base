package ar.edu.unrn.seminario.accesos;

import java.sql.SQLException;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataIntException;
import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataListException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Beneficiario;

public interface BeneficiarioDAO {
	void create(Beneficiario beneficiario) throws DAOException;

	void update(Beneficiario beneficiario) throws DAOException;
	
	void remove(String codigo)throws DAOException;

	void remove(Beneficiario beneficiario)throws DAOException;

	Beneficiario find(String codigo)throws DataNullException, DAOException, DataLengthException, DataIntException, DataListException;

	List<Beneficiario> findAll()throws DAOException, DataLengthException, DataIntException, DataListException;
	
	int obtenerCantidadBeneficiarios() throws SQLException;
	int obtenerMaximoBeneficiarios() throws SQLException;
	 public Beneficiario findCompleto(String codigo)throws DAOException, DataLengthException, DataIntException, DataListException ;
}
