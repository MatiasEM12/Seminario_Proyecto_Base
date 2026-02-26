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
import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.SolicitudBien;

public class SolicitudBienesJDBC implements SolicitudBienesDAO {

    private BienDAO bienDAO = new BienDAOJDBC();
    private Bien_SolicitudDAO bienSolicitudDAO = new Bien_SolicitudDAOJDBC();



    @Override
    public void create(SolicitudBien solicitud) throws DAOException {
        final String SQL =
            "INSERT INTO solicitudBien (codigoSolicitud, codigoBeneficiario, estado) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

        

            st.setString(1, solicitud.getCodigo());
            st.setString(2, solicitud.getBeneficiario());
            st.setString(3, solicitud.getEstado());
            st.executeUpdate();

            for (Bien b : solicitud.getBienesSolicitados()) {
                bienSolicitudDAO.create(b.getCodigo(), solicitud.getCodigo());
            }

     

        } catch (Exception e) {
            try {
                ConnectionManager.getConnection().rollback(); // investigamos es por si ConnectionManager devuelve siempre la misma conn por thread
            } catch (Exception ignore) {}
            throw new DAOException("Error CREATE SolicitudBien: " + e.getMessage()+ e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    

    @Override
    public void updateEstado(String codigoSolicitud, String estado)
            throws DAOException {

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
    public SolicitudBien find(String codigo)
        throws DAOException, DataNullException, DataLengthException, DataIntException, DataListException {

        final String SQL_SOL = "SELECT codigoBeneficiario, estado FROM solicitudBien WHERE codigoSolicitud = ?";
        final String SQL_BIEN = "SELECT codBien FROM bien_solicitud WHERE codSolicitud = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL_SOL)) {

            st.setString(1, codigo);

            try (ResultSet rs = st.executeQuery()) {
                if (!rs.next()) return null;

                String codBenef = rs.getString("codigoBeneficiario");
                String estado = rs.getString("estado");

                ArrayList<Bien> bienes = new ArrayList<>();
                try (PreparedStatement stB = conn.prepareStatement(SQL_BIEN)) {
                    stB.setString(1, codigo);
                    try (ResultSet rsB = stB.executeQuery()) {
                        while (rsB.next()) {
                            bienes.add(bienDAO.find(rsB.getString("codBien")));
                        }
                    }
                }

                return new SolicitudBien(codigo, codBenef, bienes, estado);
            }

        } catch (Exception e) {
            throw new DAOException("Error FIND SolicitudBien: " + e.getMessage()+ e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    

    @Override
    public List<SolicitudBien> findAllByBeneficiario(String codigoBeneficiario)
            throws DAOException, DataNullException, DataLengthException, DataIntException, DataListException {

        if (codigoBeneficiario == null || codigoBeneficiario.isBlank()) {
            throw new DataNullException("Código de beneficiario inválido");
        }

        final String SQL = "SELECT codigoSolicitud FROM solicitudBien WHERE codigoBeneficiario = ?";
        List<String> codigos = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            st.setString(1, codigoBeneficiario);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    codigos.add(rs.getString("codigoSolicitud"));
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Error FIND ALL SolicitudBien por Beneficiario: " + e.getMessage()+ e);
        } finally {
            ConnectionManager.disconnect();
        }

        ArrayList<SolicitudBien> solicitudes = new ArrayList<>();
        for (String cod : codigos) {
            SolicitudBien s = this.find(cod);
            if (s != null) solicitudes.add(s);
        }

        return solicitudes;
    }

   
    @Override
    public List<SolicitudBien> findAllPendientes()
            throws DAOException, DataNullException, DataLengthException, DataIntException, DataListException {

        final String SQL = "SELECT codigoSolicitud FROM solicitudBien WHERE estado = ?";
        List<String> codigos = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            st.setString(1, "Pendiente");

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    codigos.add(rs.getString("codigoSolicitud"));
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Error FIND ALL SolicitudBien Pendientes: " + e.getMessage()+ e);
        } finally {
            ConnectionManager.disconnect();
        }

        ArrayList<SolicitudBien> solicitudes = new ArrayList<>();
        for (String cod : codigos) {
            SolicitudBien s = this.find(cod);
            if (s != null) solicitudes.add(s);
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