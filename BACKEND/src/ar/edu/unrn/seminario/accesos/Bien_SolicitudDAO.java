package ar.edu.unrn.seminario.accesos;

import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Bien;

public interface Bien_SolicitudDAO {

	
	 void create(String codBien, String codSolicitud) throws DAOException;

	    void update( String codBien, String codSolicitud) throws DAOException;

	    Bien find(String codBien, String codSolicitud) throws DAOException, DataNullException;

	    List<Bien> findAll() throws DAOException, DataNullException;

	    List<Bien> findAllBySolicitud(String codSolicitud) throws DAOException, DataNullException;
	
	
}
