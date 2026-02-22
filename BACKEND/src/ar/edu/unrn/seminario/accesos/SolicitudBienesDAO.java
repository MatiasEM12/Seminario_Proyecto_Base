package ar.edu.unrn.seminario.accesos;

import java.sql.SQLException;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataIntException;
import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataListException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Beneficiario;
import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.SolicitudBien;

public interface SolicitudBienesDAO {
	void create(SolicitudBien solicitud) throws DAOException;

	void updateEstado(String codigoSolicitud, String estado) throws DAOException;

	SolicitudBien find(String codigo) throws DAOException, DataNullException, DataLengthException, DataIntException, DataListException;

	List<SolicitudBien> findAllByBeneficiario(String codBeneficiario)
			throws DAOException, DataNullException, DataLengthException, DataIntException, DataListException;

	List<SolicitudBien> findAllPendientes()
			throws DAOException, DataNullException, DataLengthException, DataIntException, DataListException;

	  public int obtenerMaximoSolicitudes() throws SQLException;
}
