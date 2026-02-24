package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataIntException;
import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataListException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Beneficiario;
import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.SolicitudBien;

public class SolicitudBienesJDBC implements SolicitudBienesDAO {
	private BeneficiarioDAO beneficiarioDAO = new BeneficiarioDAOJDBC();
	private BienDAO bienDAO = new BienDAOJDBC();
	private Bien_SolicitudDAO bien_solicitud = new Bien_SolicitudDAOJDBC();
	@Override
	public void create(SolicitudBien solicitud) throws DAOException {

	    try {
	        Connection conn = ConnectionManager.getConnection();

	        //  insertar solicitud
	        PreparedStatement st = conn.prepareStatement(
	            "INSERT INTO solicitudBien (codigoSolicitud, codigoBeneficiario, estado) " +
	            "VALUES (?, ?, ?)"
	        );

	        st.setString(1, solicitud.getCodigo());
	        st.setString(2, solicitud.getBeneficiario().getCodigo());
	        st.setString(3, solicitud.getEstado());
	        st.executeUpdate();

	        // insertar bienes solicitados
	      
	        for (Bien b : solicitud.getBienesSolicitados()) {
	            this.bien_solicitud.create(b.getCodigo(), solicitud.getCodigo());
	        }

	        

	    } catch (SQLException e) {
	        throw new DAOException("Error CREATE SolicitudBien: " + e.getMessage());
	    } finally {
	        ConnectionManager.disconnect();
	    }
	}

	@Override
	public void updateEstado(String codigoSolicitud, String estado) throws DAOException {
	    try {
	        Connection conn = ConnectionManager.getConnection();
	        PreparedStatement st = conn.prepareStatement(
	            "UPDATE solicitudBien SET estado = ? WHERE codigoSolicitud = ?"
	        );

	        st.setString(1, estado);
	        st.setString(2, codigoSolicitud);

	        if (st.executeUpdate() <= 0) {
	            throw new DAOException("Solicitud inexistente");
	        }

	    } catch (SQLException e) {
	        throw new DAOException("Error UPDATE SolicitudBien: " + e.getMessage());
	    } finally {
	        ConnectionManager.disconnect();
	    }
	}

	@Override
	public SolicitudBien find(String codigo) throws DAOException, DataNullException, DataLengthException, DataIntException, DataListException {

	    try {
	        Connection conn = ConnectionManager.getConnection();

	        PreparedStatement st = conn.prepareStatement(
	            "SELECT codigoBeneficiario, estado FROM solicitudBien WHERE codigoSolicitud = ?"
	        );
	        st.setString(1, codigo);

	        ResultSet rs = st.executeQuery();
	        if (!rs.next()) return null;

	        Beneficiario b = beneficiarioDAO.find(rs.getString("codigoBeneficiario"));

	        // bienes
	        PreparedStatement stBien = conn.prepareStatement(
	            "SELECT codBien FROM bien_solicitud WHERE codigoSolicitud = ?"
	        );
	        stBien.setString(1, codigo);

	        ResultSet rsBien = stBien.executeQuery();
	        ArrayList<Bien> bienes = new ArrayList<>();

	        while (rsBien.next()) {
	            bienes.add(bienDAO.find(rsBien.getString("codBien")));
	        }

	        return new SolicitudBien(codigo, b, bienes, rs.getString("estado"));

	    } catch (SQLException e) {
	        throw new DAOException("Error FIND SolicitudBien: " + e.getMessage());
	    } finally {
	        ConnectionManager.disconnect();
	    }
	}
	
	@Override
	public List<SolicitudBien> findAllByBeneficiario(String codBeneficiario)
	        throws DAOException, DataNullException, DataLengthException,
	               DataIntException, DataListException {

	    if (codBeneficiario == null || codBeneficiario.isBlank()) {
	        throw new DataNullException("Código de beneficiario inválido");
	    }

	    ArrayList<SolicitudBien> solicitudes = new ArrayList<>();

	    try {
	        Connection conn = ConnectionManager.getConnection();

	        PreparedStatement st = conn.prepareStatement(
	            "SELECT codigoSolicitud " +
	            "FROM solicitudBien " +
	            "WHERE codigoBeneficiario = ?"
	        );

	        st.setString(1, codBeneficiario);
	        ResultSet rs = st.executeQuery();

	        while (rs.next()) {
	            solicitudes.add(this.find(rs.getString("codigoSolicitud")));
	        }

	    } catch (SQLException e) {
	        throw new DAOException(
	            "Error FIND ALL SolicitudBien por Beneficiario: " + e.getMessage()
	        );
	    } finally {
	        ConnectionManager.disconnect();
	    }

	    return solicitudes;
	}
	@Override
	public List<SolicitudBien> findAllPendientes()
	        throws DAOException, DataNullException, DataLengthException,
	               DataIntException, DataListException {

	    ArrayList<SolicitudBien> solicitudes = new ArrayList<>();

	    try {
	        Connection conn = ConnectionManager.getConnection();

	        PreparedStatement st = conn.prepareStatement(
	            "SELECT codigoSolicitud " +
	            "FROM solicitudBien " +
	            "WHERE estado = ?"
	        );

	        st.setString(1, "Pendiente");

	        ResultSet rs = st.executeQuery();

	        while (rs.next()) {
	            solicitudes.add(this.find(rs.getString("codigoSolicitud")));
	        }

	    } catch (SQLException e) {
	        throw new DAOException(
	            "Error FIND ALL SolicitudBien Pendientes: " + e.getMessage()
	        );
	    } finally {
	        ConnectionManager.disconnect();
	    }

	    return solicitudes;
	}
	   public int obtenerMaximoSolicitudes() throws SQLException {
	        String sql = "SELECT MAX(codigoSolicitud) FROM solicitudBien";

	        try (Connection conn = ConnectionManager.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            if (rs.next()) {
	                String maxCodigo = rs.getString(1);

	                if (maxCodigo != null) {
	                   
	                    return Integer.parseInt(maxCodigo.substring(2));
	                }
	            }
	        } finally {
	            ConnectionManager.disconnect();
	        }
	        return 0;
	    }
}
