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
import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataListException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Visita;

public class VisitaDAOJDBC implements VisitaDao {

    private BienDAO biendao = new BienDAOJDBC();

    @Override
    public void create(Visita visita)
            throws DataNullException, DataLengthException, DAOException {

        final String SQL =
            "INSERT INTO visitas " +
            "(codigo, tipo, observaciones, estado, FechaVisita, codOrdenRetiro, codOrdenEntrega) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            st.setString(1, visita.getCodigo());
            st.setString(2, visita.getTipo());
            st.setString(3, visita.getObservaciones());
            st.setString(4, visita.getEstado());
            st.setDate(5, Date.valueOf(visita.getFechaVisita()));

            // NULL para el que NO corresponde
            if (visita.getCodOrdenRetiro() == null) st.setNull(6, Types.VARCHAR);
            else st.setString(6, visita.getCodOrdenRetiro());

            if (visita.getCodOrdenEntrega() == null) st.setNull(7, Types.VARCHAR);
            else st.setString(7, visita.getCodOrdenEntrega());

            if (st.executeUpdate() <= 0) {
                throw new DAOException("No se insertó la visita");
            }

        } catch (SQLException e) {
            throw new DAOException("Error INSERT visita VT100: " + e.getMessage()+e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void update(Visita visita) throws DAOException {

        final String SQL =
            "UPDATE visitas SET FechaVisita=?, observaciones=?, tipo=?, estado=?, " +
            "codOrdenRetiro=?, codOrdenEntrega=? WHERE codigo=?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            st.setDate(1, Date.valueOf(visita.getFechaVisita()));
            st.setString(2, visita.getObservaciones());
            st.setString(3, visita.getTipo());
            st.setString(4, visita.getEstado());

            // ✅ NULL correcto según corresponda
            if (visita.getCodOrdenRetiro() == null) st.setNull(5, Types.VARCHAR);
            else st.setString(5, visita.getCodOrdenRetiro());

            if (visita.getCodOrdenEntrega() == null) st.setNull(6, Types.VARCHAR);
            else st.setString(6, visita.getCodOrdenEntrega());

            st.setString(7, visita.getCodigo());

            if (st.executeUpdate() <= 0) {
                throw new DAOException("No se actualizó la visita");
            }

        } catch (SQLException e) {
            throw new DAOException("Error UPDATE visita VT200: " + e.getMessage()+e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void remove(String codigo) throws DAOException {

        final String SQL = "DELETE FROM visitas WHERE codigo = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            st.setString(1, codigo);

            int cantidad = st.executeUpdate();
            if (cantidad <= 0) {
                throw new DAOException("No se encontró la visita con ese código. VT300");
            }

        } catch (SQLException e) {
            throw new DAOException("Error al Eliminar Visita. VT301: " + e.getMessage()+ e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void remove(Visita visita) throws DAOException {
        if (visita == null) return;
        remove(visita.getCodigo());
    }

    @Override
    public Visita find(String codigo)
            throws DataNullException, DataLengthException, DAOException,
                   DataDateException, DataEmptyException, DataListException {

        final String SQL =
            "SELECT codigo, FechaVisita, observaciones, tipo, estado, codOrdenRetiro, codOrdenEntrega " +
            "FROM visitas WHERE codigo = ?";

        /*
         * Justificación (para comentar):
         * Usamos try-with-resources para cerrar automáticamente Connection/PreparedStatement/ResultSet.
         * Si estos recursos no se cierran, MySQL acumula conexiones abiertas y termina en
         * "Too many connections". El cierre automático evita fugas de recursos y estabiliza el sistema.
         */
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            st.setString(1, codigo);

            try (ResultSet rs = st.executeQuery()) {
                if (!rs.next()) return null;

                LocalDate fecha = rs.getDate("FechaVisita").toLocalDate();

                String codRet = rs.getString("codOrdenRetiro");
                String codEnt = rs.getString("codOrdenEntrega");

                // regla: si entrega NULL => es retiro. si retiro NULL => es entrega
                String codOrden = (codEnt != null) ? codEnt : codRet;

                if (codOrden == null) {
                    throw new DAOException("Visita " + codigo + " sin orden asociada (ret/ent NULL). VT501");
                }

                Visita visita = new Visita(
                    fecha,
                    rs.getString("observaciones"),
                    rs.getString("tipo"),
                    codOrden,
                    biendao.findBienVisita(codigo),
                    rs.getString("codigo")
                );

                // ✅ tu constructor no cargaba estado, lo seteamos
                visita.setEstado(rs.getString("estado"));

                return visita;
            }

        } catch (SQLException e) {
            throw new DAOException("Error FIND visita VT500: " + e.getMessage()+ e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public List<Visita> findAll()
            throws DataNullException, DataLengthException, DAOException,
                   DataDateException, DataEmptyException, DataListException {

        final String SQL = "SELECT codigo FROM visitas";
        List<Visita> visitas = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                String codigo = rs.getString("codigo");
                // ✅ cambio mínimo: NO llamar this.find() dentro del while
                Visita v = this.find(codigo);
                if (v != null) visitas.add(v);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al procesar consulta: " + e.getMessage() + ". VT600"+ e);
        } finally {
            ConnectionManager.disconnect();
        }

        return visitas;
    }

    @Override
    public ArrayList<Visita> findAllOrdenRetiro(String codOrdenRetiro)
            throws DataNullException, DataLengthException, DAOException,
                   DataDateException, DataEmptyException, DataListException {

        final String SQL = "SELECT codigo FROM visitas WHERE codOrdenRetiro = ?";
        ArrayList<Visita> visitas = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            st.setString(1, codOrdenRetiro);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    String codigo = rs.getString("codigo");
                    Visita v = this.find(codigo); // ya es try-with-resources adentro
                    if (v != null) visitas.add(v);
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Error FIND ALL visita por retiro VT700: " + e.getMessage()+ e);
        } finally {
            ConnectionManager.disconnect();
        }

        return visitas;
    }

    @Override
    public ArrayList<Visita> findAllOrdenEntrega(String codOrdenEntrega)
            throws DataNullException, DataLengthException, DAOException,
                   DataDateException, DataEmptyException, DataListException {

        final String SQL = "SELECT codigo FROM visitas WHERE codOrdenEntrega = ?";
        ArrayList<Visita> visitas = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            st.setString(1, codOrdenEntrega);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    String codigo = rs.getString("codigo");
                    Visita v = this.find(codigo);
                    if (v != null) visitas.add(v);
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Error FIND ALL visita por entrega VT710: " + e.getMessage()+e);
        } finally {
            ConnectionManager.disconnect();
        }

        return visitas;
    }

    @Override
    public int obtenerCantidadVisitas() throws SQLException {

        final String SQL = "SELECT COUNT(*) FROM visitas";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } finally {
            ConnectionManager.disconnect();
        }

        return 0;
    }

    @Override
    public int obtenerMaximoVisitas() throws SQLException {

        final String SQL = "SELECT MAX(codigo) FROM visitas";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL);
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