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
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Bien;

public class SolicitudBienesJDBC implements SolicitudBienesDAO {

    private InventarioDAO inventarioDao = new InventarioDAOJDBC();
    private BienDAO bienDao = new BienDAOJDBC();

    // =========================
    // CREATE
    // =========================
    @Override
    public void create(String codBien, String codBeneficiario, boolean activo) throws DAOException {

    	//consideramos estas validaciones ya que el Beneficiario deberia elegir que bienes necesita, seleccionando desde los que
    	//esten disponibles en el inventario como si se tratara de una tienda.
    	
        // 1. Verificar que el bien exista en inventario
        if (!((InventarioDAOJDBC) inventarioDao).existe(codBien)) {
            throw new DAOException("El bien no existe en inventario");
        }

        // 2. Verificar que el bien esté disponible
        
        if (!inventarioDao.esDisponible(codBien)) {
            throw new DAOException("El bien no está disponible en inventario");
        }

        // 3. Insertar solicitud
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement st = conn.prepareStatement(
                "INSERT INTO solicitudBien (codigoBien, codigoBeneficiario, disponible) " +
                "VALUES (?, ?, ?)"
            );

            st.setString(1, codBien);
            st.setString(2, codBeneficiario);
            st.setBoolean(3, activo);

            if (st.executeUpdate() <= 0) {
                throw new DAOException("No se pudo crear la solicitud del bien");
            }

        } catch (SQLException e) {
            throw new DAOException("Error INSERT SolicitudBien: " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }
    }

    // =========================
    // UPDATE (activar / desactivar solicitudes del beneficiario)
    // =========================
    @Override
    public void update(String codBeneficiario, boolean activo) throws DAOException {
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement st = conn.prepareStatement(
                "UPDATE solicitudBien SET disponible = ? WHERE codigoBeneficiario = ?"
            );

            st.setBoolean(1, activo);
            st.setString(2, codBeneficiario);

            if (st.executeUpdate() <= 0) {
                throw new DAOException("No se actualizaron solicitudes del beneficiario");
            }

        } catch (SQLException e) {
            throw new DAOException("Error UPDATE SolicitudBien: " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }
    }

    // =========================
    // REMOVE (por bien)
    // =========================
    @Override
    public void remove(String codBien) throws DAOException {
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement st = conn.prepareStatement(
                "DELETE FROM solicitudBien WHERE codigoBien = ?"
            );

            st.setString(1, codBien);

            if (st.executeUpdate() <= 0) {
                throw new DAOException("No existe solicitud para el bien");
            }

        } catch (SQLException e) {
            throw new DAOException("Error DELETE SolicitudBien: " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }
    }

    // =========================
    // FIND ALL BIENES ACTIVOS (solicitados)
    // =========================
    @Override
    public List<Bien> findAllBienesActivos()
            throws DAOException, DataLengthException, DataIntException, DataNullException {

        List<Bien> bienes = new ArrayList<>();

        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement st = conn.prepareStatement(
                "SELECT codigoBien FROM solicitudBien WHERE disponible = 1"
            );

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                bienes.add(bienDao.find(rs.getString("codigoBien")));
            }

        } catch (SQLException e) {
            throw new DAOException("Error FIND ALL Bienes Activos: " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }

        return bienes;
    }

    // =========================
    // FIND BIENES ACTIVOS POR BENEFICIARIO
    // =========================
    @Override
    public List<Bien> findAllBienesBeneficiario(String codBeneficiario)
            throws DAOException, DataLengthException, DataIntException, DataNullException {

        List<Bien> bienes = new ArrayList<>();

        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement st = conn.prepareStatement(
                "SELECT codigoBien FROM solicitudBien " +
                "WHERE codigoBeneficiario = ? AND disponible = 1"
            );

            st.setString(1, codBeneficiario);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                bienes.add(bienDao.find(rs.getString("codigoBien")));
            }

        } catch (SQLException e) {
            throw new DAOException("Error FIND Bienes por Beneficiario: " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }

        return bienes;
    }
}
