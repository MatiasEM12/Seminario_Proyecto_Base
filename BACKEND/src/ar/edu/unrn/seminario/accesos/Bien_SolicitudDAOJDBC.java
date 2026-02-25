package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Bien;

public class Bien_SolicitudDAOJDBC implements Bien_SolicitudDAO {

    private BienDAO bienDAO = new BienDAOJDBC();

    @Override
    public void create(String codBien, String codSolicitud) throws DAOException {

        final String SQL = "INSERT INTO bien_solicitud (codBien, codSolicitud) VALUES (?, ?)";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            st.setString(1, codBien);
            st.setString(2, codSolicitud);

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error CREATE Bien_Solicitud: " + e.getMessage() + ".BS100"+ e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void update(String codBien, String codSolicitud) throws DAOException {

        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE bien_solicitud SET codSolicitud = ? WHERE codBien = ?"
            );

            ps.setString(1, codSolicitud);
            ps.setString(2, codBien);

            int filas = ps.executeUpdate();

            if (filas == 0) {
                throw new DAOException(
                    "No se actualizó ningún registro en bien_solicitud (codBien=" + codBien + ")"
                );
            }

        } catch (SQLException e) {
            throw new DAOException("Error al actualizar Bien_Solicitud BS200"+ e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public Bien find(String codBien, String codSolicitud) throws DAOException, DataNullException {

        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT codBien FROM bien_solicitud WHERE codBien = ? AND codSolicitud = ?"
            );

            ps.setString(1, codBien);
            ps.setString(2, codSolicitud);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return bienDAO.find(rs.getString("codBien"));
            }

        } catch (SQLException e) {
            throw new DAOException("Error al buscar Bien_Solicitud BS300"+ e);
        } finally {
            ConnectionManager.disconnect();
        }

        return null;
    }

    @Override
    public List<Bien> findAll() throws DAOException, DataNullException {

        ArrayList<Bien> bienes = new ArrayList<>();

        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT codBien FROM bien_solicitud"
            );

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                bienes.add(
                    bienDAO.find(rs.getString("codBien"))
                );
            }

        } catch (SQLException e) {
            throw new DAOException("Error en findAll Bien_Solicitud BS400"+e);
        } finally {
            ConnectionManager.disconnect();
        }

        return bienes;
    }

    @Override
    public List<Bien> findAllBySolicitud(String codSolicitud) throws DAOException, DataNullException {

        final String SQL = "SELECT codBien FROM bien_solicitud WHERE codSolicitud = ?";
        ArrayList<Bien> bienes = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            st.setString(1, codSolicitud);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    bienes.add(bienDAO.find(rs.getString("codBien")));
                }
            }

        } catch (Exception e) {
            throw new DAOException("Error findAllBySolicitud: " + e.getMessage() + ".BS500"+ e);
        } finally {
            ConnectionManager.disconnect();
        }

        return bienes;
    }
}