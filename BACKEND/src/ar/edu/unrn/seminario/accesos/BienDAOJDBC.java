package ar.edu.unrn.seminario.accesos;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Bien;

public class BienDAOJDBC implements BienDAO {

    @Override
    public void create(Bien bien) throws DAOException {

        final String SQL =
            "INSERT INTO bien(codigo,tipo,nombre,peso,descripcion,nivelNecesidad,fechaVencimiento,talle,material) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            st.setString(1, bien.getCodigo());
            st.setString(2, bien.getTipo());
            st.setString(3, bien.getNombre());

            if (bien.getPeso() != null) st.setDouble(4, bien.getPeso());
            else st.setNull(4, Types.DOUBLE);

            st.setString(5, bien.getDescripcion());
            st.setInt(6, bien.getNivelNecesidad());

            if (bien.getFechaVencimiento() != null) st.setDate(7, Date.valueOf(bien.getFechaVencimiento()));
            else st.setNull(7, Types.DATE);

            if (bien.getTalle() != null) st.setDouble(8, bien.getTalle());
            else st.setNull(8, Types.DOUBLE);

            if (bien.getMaterial() != null) st.setString(9, bien.getMaterial());
            else st.setNull(9, Types.VARCHAR);

            int filas = st.executeUpdate();
            if (filas <= 0) throw new DAOException("No se insertó Bien. B100");

        } catch (SQLException e) {
            throw new DAOException("Error INSERT Bien: " + e.getMessage() + ".B101"+ e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void update(Bien bien) throws DAOException {

        final String SQL =
            "UPDATE bien SET tipo=?, nombre=?, peso=?, descripcion=?, nivelNecesidad=?, " +
            "fechaVencimiento=?, talle=?, material=? WHERE codigo=?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            st.setString(1, bien.getTipo());
            st.setString(2, bien.getNombre());

            if (bien.getPeso() != null) st.setDouble(3, bien.getPeso());
            else st.setNull(3, Types.DOUBLE);

            st.setString(4, bien.getDescripcion());
            st.setInt(5, bien.getNivelNecesidad());

            if (bien.getFechaVencimiento() != null) st.setDate(6, Date.valueOf(bien.getFechaVencimiento()));
            else st.setNull(6, Types.DATE);

            if (bien.getTalle() != null) st.setDouble(7, bien.getTalle());
            else st.setNull(7, Types.DOUBLE);

            if (bien.getMaterial() != null) st.setString(8, bien.getMaterial());
            else st.setNull(8, Types.VARCHAR);

            st.setString(9, bien.getCodigo());

            int filas = st.executeUpdate();
            if (filas <= 0) throw new DAOException("No se encontró Bien para actualizar. B200");

        } catch (SQLException e) {
            throw new DAOException("Error UPDATE Bien: " + e.getMessage() + ".B201"+ e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void remove(String codigo) throws DAOException {

        final String SQL = "DELETE FROM bien WHERE codigo=?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            st.setString(1, codigo);

            int filas = st.executeUpdate();
            if (filas <= 0) throw new DAOException("No se encontró Bien para eliminar. B300");

        } catch (SQLException e) {
            throw new DAOException("Error DELETE Bien: " + e.getMessage() + ".B301"+ e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public Bien find(String codigo) throws DataNullException, DAOException {

        if (codigo == null || codigo.isBlank()) {
            throw new DataNullException("Código inválido. B502");
        }

        final String SQL =
            "SELECT codigo,tipo,nombre,peso,descripcion,nivelNecesidad,fechaVencimiento,talle,material " +
            "FROM bien WHERE codigo = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            st.setString(1, codigo);

            try (ResultSet rs = st.executeQuery()) {
                if (!rs.next()) return null;

                Date d = rs.getDate("fechaVencimiento");
                LocalDate fecha = (d != null) ? d.toLocalDate() : null;

                Double peso = (Double) rs.getObject("peso");
                Double talle = (Double) rs.getObject("talle");

                return new Bien(
                    rs.getString("codigo"),
                    rs.getString("tipo"),
                    peso,
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    fecha,
                    talle,
                    rs.getString("material")
                );
            }

        } catch (Exception e) {
            throw new DAOException("Error FIND Bien: " + e.getMessage() + ".B500"+ e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public List<Bien> findAll() throws DAOException {

        final String SQL =
            "SELECT codigo,tipo,nombre,peso,descripcion,nivelNecesidad,fechaVencimiento,talle,material " +
            "FROM bien";

        List<Bien> bienes = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Date d = rs.getDate("fechaVencimiento");
                LocalDate fecha = (d != null) ? d.toLocalDate() : null;

                Double peso = (Double) rs.getObject("peso");
                Double talle = (Double) rs.getObject("talle");

                Bien b = new Bien(
                    rs.getString("codigo"),
                    rs.getString("tipo"),
                    peso,
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    fecha,
                    talle,
                    rs.getString("material")
                );

                bienes.add(b);
            }

        } catch (Exception e) {
            throw new DAOException("Error FIND ALL Bien: " + e.getMessage() + ".B600"+ e);
        } finally {
            ConnectionManager.disconnect();
        }

        return bienes;
    }

    @Override
    public ArrayList<Bien> findBienVisita(String codVisita) throws DataNullException, DAOException {

        if (codVisita == null || codVisita.isBlank()) {
            throw new DataNullException("Código visita inválido. B702");
        }

        final String SQL =
            "SELECT b.codigo,b.tipo,b.nombre,b.peso,b.descripcion,b.nivelNecesidad,b.fechaVencimiento,b.talle,b.material " +
            "FROM bien b " +
            "JOIN Bien_Visita bv ON b.codigo = bv.codBien " +
            "WHERE bv.codVisita = ?";

        ArrayList<Bien> bienes = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            st.setString(1, codVisita);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {

                    Date d = rs.getDate("fechaVencimiento");
                    LocalDate fecha = (d != null) ? d.toLocalDate() : null;

                    Double peso = (Double) rs.getObject("peso");
                    Double talle = (Double) rs.getObject("talle");

                    Bien b = new Bien(
                        rs.getString("codigo"),
                        rs.getString("tipo"),
                        peso,
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        fecha,
                        talle,
                        rs.getString("material")
                    );

                    bienes.add(b);
                }
            }

        } catch (Exception e) {
            throw new DAOException("Error findBienVisita: " + e.getMessage() + ".B700"+ e);
        } finally {
            ConnectionManager.disconnect();
        }

        return bienes;
    }

   
    @Override
    public ArrayList<Bien> findBienDonacion(String codDonacion) throws DataNullException, DAOException {

        if (codDonacion == null || codDonacion.isBlank()) {
            throw new DataNullException("Código donación inválido. B802");
        }

        final String SQL =
            "SELECT b.codigo,b.tipo,b.nombre,b.peso,b.descripcion,b.nivelNecesidad,b.fechaVencimiento,b.talle,b.material " +
            "FROM bien b " +
            "JOIN Bien_Donacion bd ON b.codigo = bd.codBien " +
            "WHERE bd.codDonacion = ?";

        ArrayList<Bien> bienes = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            st.setString(1, codDonacion);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {

                    Date d = rs.getDate("fechaVencimiento");
                    LocalDate fecha = (d != null) ? d.toLocalDate() : null;

                    Double peso = (Double) rs.getObject("peso");
                    Double talle = (Double) rs.getObject("talle");

                    Bien b = new Bien(
                        rs.getString("codigo"),
                        rs.getString("tipo"),
                        peso,
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        fecha,
                        talle,
                        rs.getString("material")
                    );

                    bienes.add(b);
                }
            }

        } catch (Exception e) {
            throw new DAOException("Error findBienDonacion: " + e.getMessage() + ".B800"+ e);
        } finally {
            ConnectionManager.disconnect();
        }

        return bienes;
    }

    @Override
    public void remove(Bien bien) throws DAOException {
        if (bien == null) {
            throw new DAOException("Bien nulo. B402");
        }
        this.remove(bien.getCodigo()); // reutiliza el remove(String)
    }

    @Override
    public List<Bien> findALLTipo(String tipo) throws DataNullException, DAOException {

        if (tipo == null || tipo.isBlank()) {
            throw new DataNullException("Tipo inválido. B902");
        }

        // Si el tipo usa vencimiento, traemos solo vigentes.
        boolean usaVencimiento =
                tipo.equalsIgnoreCase("Alimento") ||
                tipo.equalsIgnoreCase("Medicamento");

        final String SQL = usaVencimiento
            ? "SELECT codigo,tipo,nombre,peso,descripcion,nivelNecesidad,fechaVencimiento,talle,material " +
              "FROM bien " +
              "WHERE tipo = ? AND fechaVencimiento IS NOT NULL AND fechaVencimiento >= ?"
            : "SELECT codigo,tipo,nombre,peso,descripcion,nivelNecesidad,fechaVencimiento,talle,material " +
              "FROM bien " +
              "WHERE tipo = ?";

        List<Bien> bienes = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            st.setString(1, tipo);
            if (usaVencimiento) {
                st.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            }

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {

                    // NULL real (no convertir a 0)
                    Double peso = (Double) rs.getObject("peso");
                    Double talle = (Double) rs.getObject("talle");

                    java.sql.Date d = rs.getDate("fechaVencimiento");
                    LocalDate fecha = (d != null) ? d.toLocalDate() : null;

                    Bien b = new Bien(
                        rs.getString("codigo"),
                        rs.getString("tipo"),
                        peso,
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        fecha,
                        talle,
                        rs.getString("material")
                    );

                    bienes.add(b);
                }
            }

        } catch (Exception e) {
            throw new DAOException("Error FIND ALL por tipo: " + e.getMessage() + ".B900"+ e);
        } finally {
            ConnectionManager.disconnect();
        }

        return bienes;
    }
    @Override
    public int obtenerCantidadBienes() throws SQLException {
        final String sql = "SELECT COUNT(*) FROM bien";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);
            return 0;

        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public int obtenerMaximoBienes() throws SQLException {
      
        final String sql = "SELECT MAX(codigo) FROM bien";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String maxCodigo = rs.getString(1);
                if (maxCodigo == null || maxCodigo.isBlank()) return 0;

                // "B00012" -> 12
                return Integer.parseInt(maxCodigo.substring(1));
            }
            return 0;

        } finally {
            ConnectionManager.disconnect();
        }
    }

	@Override
	public void remove(Long id) {
		// TODO Auto-generated method stub
		
	}
}



	