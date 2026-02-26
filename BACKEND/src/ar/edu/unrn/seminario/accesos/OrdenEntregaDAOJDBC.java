package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.modelo.Beneficiario;
import ar.edu.unrn.seminario.modelo.OrdenEntrega;
import ar.edu.unrn.seminario.modelo.SolicitudBien;
import ar.edu.unrn.seminario.modelo.Visita;
import ar.edu.unrn.seminario.modelo.Voluntario;

public class OrdenEntregaDAOJDBC implements OrdenEntregaDAO {

    private final VisitaDao visitaDAO = new VisitaDAOJDBC();
    private final VoluntarioDAO voluntarioDAO = new VoluntarioDAOJDBC();
    private final SolicitudBienesDAO solicitudDAO = new SolicitudBienesJDBC();

    @Override
    public void create(OrdenEntrega orden) throws DAOException {

        final String SQL =
            "INSERT INTO ordenEntrega " +
            "(codigo, estado, FechaCreacion, FechaProgramada, codVoluntario, codBeneficiario, codSolicitud) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            st.setString(1, orden.getCodigo());
            st.setString(2, orden.getEstadoString());
            st.setDate(3, Date.valueOf(orden.getFechaEmision()));

            // FechaProgramada NO puede ser NULL
            if (orden.getFechaHoraProgramada() == null) {
                throw new DAOException("FechaProgramada no puede ser NULL. OE100");
            }
            st.setDate(4, Date.valueOf(orden.getFechaHoraProgramada()));

            if (orden.getVoluntario() != null) st.setString(5, orden.getVoluntario().getCodigo());
            else st.setNull(5, Types.VARCHAR);

            st.setString(6, orden.getBeneficiario().getCodigo());
            st.setString(7, orden.getSolicitud().getCodigo());

            int filas = st.executeUpdate();
            if (filas <= 0) throw new DAOException("No se insertó OrdenEntrega. OE101");

        } catch (SQLException e) {
            throw new DAOException("Error INSERT OrdenEntrega: " + e.getMessage() + ".OE102"+ e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void update(OrdenEntrega orden) throws DAOException {

        final String SQL =
            "UPDATE ordenEntrega SET estado=?, FechaProgramada=?, codVoluntario=? WHERE codigo=?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            st.setString(1, orden.getEstadoString());

            // FechaProgramada NO puede ser NULL
            if (orden.getFechaHoraProgramada() == null) {
                throw new DAOException("FechaProgramada no puede ser NULL. OE200");
            }
            st.setDate(2, Date.valueOf(orden.getFechaHoraProgramada()));

            if (orden.getVoluntario() != null) st.setString(3, orden.getVoluntario().getCodigo());
            else st.setNull(3, Types.VARCHAR);

            st.setString(4, orden.getCodigo());

            int filas = st.executeUpdate();
            if (filas <= 0) throw new DAOException("No se actualizó OrdenEntrega. OE201");

        } catch (SQLException e) {
            throw new DAOException("Error UPDATE OrdenEntrega: " + e.getMessage() + ".OE202"+e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void remove(String codigo) throws DAOException {

        final String SQL = "DELETE FROM ordenEntrega WHERE codigo = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            st.setString(1, codigo);

            int filas = st.executeUpdate();
            if (filas <= 0) throw new DAOException("OrdenEntrega inexistente. OE300");

        } catch (SQLException e) {
            throw new DAOException("Error DELETE OrdenEntrega: " + e.getMessage() + ".OE301"+ e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void remove(OrdenEntrega orden) throws DAOException {
        if (orden == null) return;
        remove(orden.getCodigo());
    }

    @Override
    public OrdenEntrega find(String codigo) throws DAOException {

        final String SQL =
            "SELECT codigo, estado, FechaCreacion, FechaProgramada, codVoluntario, codBeneficiario, codSolicitud " +
            "FROM ordenEntrega WHERE codigo = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            st.setString(1, codigo);

            try (ResultSet rs = st.executeQuery()) {
                if (!rs.next()) return null;

                String estado = rs.getString("estado");

                LocalDate fechaCreacion = rs.getDate("FechaCreacion").toLocalDate();

                Date dProg = rs.getDate("FechaProgramada");
                if (dProg == null) {
                    throw new DAOException("OrdenEntrega " + codigo + " tiene FechaProgramada NULL. OE400");
                }
                LocalDate fechaProg = dProg.toLocalDate();

                
                BeneficiarioDAO beneficiarioDAO = new BeneficiarioDAOJDBC();
                Beneficiario beneficiario = beneficiarioDAO.find(rs.getString("codBeneficiario"));

                Voluntario voluntario = null;
                String codVol = rs.getString("codVoluntario");
                if (codVol != null) voluntario = voluntarioDAO.find(codVol);

                String codSolicitud = rs.getString("codSolicitud");
                SolicitudBien solicitud = solicitudDAO.find(codSolicitud);
                if (solicitud == null) {
                    throw new DAOException("OrdenEntrega " + codigo + " tiene solicitud inexistente: " + codSolicitud + ". OE401");
                }

                ArrayList<Visita> visitas = visitaDAO.findAllOrdenEntrega(codigo);

                return new OrdenEntrega(
                    fechaCreacion,
                    estado,
                    codigo,
                    fechaProg,
                    visitas,
                    solicitud,
                    beneficiario,
                    voluntario
                );
            }

        } catch (DAOException e) {
            throw e;
        } catch (SQLException e) {
            throw new DAOException("Error FIND OrdenEntrega: " + e.getMessage() + ".OE402"+ e);
        } catch (Exception e) {
            throw new DAOException("Error FIND OrdenEntrega: " + e.getMessage() + ".OE403"+ e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public List<OrdenEntrega> findAll() throws DAOException {

        final String SQL = "SELECT codigo FROM ordenEntrega";
        List<String> codigos = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) codigos.add(rs.getString("codigo"));

        } catch (SQLException e) {
            throw new DAOException("Error FIND ALL OrdenEntrega: " + e.getMessage() + ".OE600"+e);
        } finally {
            ConnectionManager.disconnect();
        }

        ArrayList<OrdenEntrega> ordenes = new ArrayList<>();
        for (String cod : codigos) {
            OrdenEntrega oe = this.find(cod);
            if (oe != null) ordenes.add(oe);
        }
        return ordenes;
    }

    @Override
    public List<OrdenEntrega> findAllByBeneficiario(String codBeneficiario) throws DAOException {

        final String SQL = "SELECT codigo FROM ordenEntrega WHERE codBeneficiario = ?";
        List<String> codigos = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            st.setString(1, codBeneficiario);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) codigos.add(rs.getString("codigo"));
            }

        } catch (SQLException e) {
            throw new DAOException("Error FIND ALL OE por beneficiario: " + e.getMessage() + ".OE610"+e);
        } finally {
            ConnectionManager.disconnect();
        }

        ArrayList<OrdenEntrega> ordenes = new ArrayList<>();
        for (String cod : codigos) {
            OrdenEntrega oe = this.find(cod);
            if (oe != null) ordenes.add(oe);
        }
        return ordenes;
    }

    @Override
    public int obtenerCantidadOE() throws SQLException {
        final String sql = "SELECT COUNT(*) FROM ordenEntrega";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;

        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public int obtenerMaximoOrdenEntrega() throws SQLException {
        final String sql = "SELECT MAX(codigo) FROM ordenEntrega";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String maxCodigo = rs.getString(1);
                if (maxCodigo != null) {
                    // "OE00012" -> 12
                    return Integer.parseInt(maxCodigo.substring(2));
                }
            }
            return 0;

        } finally {
            ConnectionManager.disconnect();
        }
    }
}