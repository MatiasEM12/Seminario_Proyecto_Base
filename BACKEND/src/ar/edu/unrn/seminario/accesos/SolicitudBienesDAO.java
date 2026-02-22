package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataIntException;
import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Beneficiario;
import ar.edu.unrn.seminario.modelo.Bien;

public interface SolicitudBienesDAO {
	void create(String codBien,String codBeneficiario, boolean activo) throws DAOException;

	void update(String codBeneficiario, boolean activo) throws DAOException;
	
	void remove(String codBien)throws DAOException;



	List<Bien> findAllBienesActivos()throws DAOException, DataLengthException, DataIntException, DataNullException;
	List<Bien> findAllBienesBeneficiario(String codBeneficiario)throws DAOException, DataLengthException, DataIntException, DataNullException;
}
