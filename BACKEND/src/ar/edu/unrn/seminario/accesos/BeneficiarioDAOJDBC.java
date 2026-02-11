package ar.edu.unrn.seminario.accesos;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;
import ar.edu.unrn.seminario.modelo.Beneficiario;
import ar.edu.unrn.seminario.modelo.Ubicacion;

public class BeneficiarioDAOJDBC implements BeneficiarioDAO {

    private final UbicacionDAO ubicacionDAO;

    public BeneficiarioDAOJDBC() {
        this.ubicacionDAO = new UbicacionDAOJDBC(); // ajustá si se llama distinto
    }

    @Override
    public void create(Beneficiario b) {
        if (b == null) return;

        final String SQL =
            "INSERT INTO beneficiario " +
            "(codigo, nombre, apellido, dni, contacto, Fecha_Nacimiento, username, tipo, aCargo, prioridad, coUbicacion, activo) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            // BD exige NOT NULL en todo
            String codUbic = b.getUbicacion().getCodigo();

            st.setString(1, b.getCodigo());
            st.setString(2, b.getNombre());
            st.setString(3, b.getApellido());
            st.setString(4, b.getDni());
            st.setString(5, b.getContacto());
            st.setDate(6, Date.valueOf(b.getFecha_nac())); // si en Persona se llama distinto, ajustá getter
            st.setString(7, b.getUsername());

            // Estos campos existen en la tabla pero NO están en tu modelo Beneficiario.
            // Ponemos defaults razonables:
            st.setString(8, "BENE");  // tipo (ajustá si usás otra convención)
            st.setInt(9, 0);          // aCargo (0/1)
            st.setInt(10, 1);         // prioridad (1..n)
            st.setString(11, codUbic);
            st.setInt(12, 1);         // activo

            st.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al procesar consulta (INSERT beneficiario): " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void update(Beneficiario b) {
        if (b == null) return;

        final String SQL =
            "UPDATE beneficiario SET " +
            "nombre = ?, apellido = ?, dni = ?, contacto = ?, Fecha_Nacimiento = ?, username = ?, coUbicacion = ? " +
            "WHERE codigo = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            st.setString(1, b.getNombre());
            st.setString(2, b.getApellido());
            st.setString(3, b.getDni());
            st.setString(4, b.getContacto());
            st.setDate(5, Date.valueOf(b.getFecha_nac())); // ajustar getter si difiere
            st.setString(6, b.getUsername());
            st.setString(7, b.getUbicacion().getCodigo());
            st.setString(8, b.getCodigo());

            st.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al procesar consulta (UPDATE beneficiario): " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void remove(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) return;

        final String SQL = "DELETE FROM beneficiario WHERE codigo = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            st.setString(1, codigo);
            st.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al procesar consulta (DELETE beneficiario): " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void remove(Beneficiario b) {
        if (b == null) return;
        remove(b.getCodigo());
    }

    @Override
    public Beneficiario find(String codigo) throws DAOException, DataLengthException {
        if (codigo == null || codigo.trim().isEmpty()) return null;

        final String SQL =
            "SELECT codigo, nombre, apellido, dni, contacto, Fecha_Nacimiento, username, coUbicacion " +
            "FROM beneficiario WHERE codigo = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            st.setString(1, codigo);

            try (ResultSet rs = st.executeQuery()) {
                if (!rs.next()) return null;

                String cod = rs.getString("codigo");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String dni = rs.getString("dni");
                String contacto = rs.getString("contacto");
                LocalDate fechaNac = rs.getDate("Fecha_Nacimiento").toLocalDate();
                String username = rs.getString("username");
                String codUbic = rs.getString("coUbicacion");

                Ubicacion ubic = (ubicacionDAO != null) ? ubicacionDAO.find(codUbic) : null;

                Beneficiario bene = new Beneficiario(nombre, apellido, fechaNac, dni, contacto, ubic, username);

                // ✅ Para conservar el código real de BD:
                bene.setCodigoDesdeBD(cod);

                return bene;
            }

        } catch (SQLException e) {
            System.out.println("Error al procesar consulta (SELECT beneficiario): " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (DataEmptyException | DataObjectException | DataNullException | DataDateException e) {
            System.out.println("Error reconstruyendo Beneficiario desde BD: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public List<Beneficiario> findAll() throws DAOException, DataLengthException {
        final String SQL = "SELECT codigo FROM beneficiario";
        List<Beneficiario> lista = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                String cod = rs.getString("codigo");
                Beneficiario b = find(cod);
                if (b != null) lista.add(b);
            }

        } catch (SQLException e) {
            System.out.println("Error al procesar consulta (SELECT ALL beneficiario): " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionManager.disconnect();
        }

        return lista;
    }
    public int obtenerCantidadBeneficiarios() throws SQLException {
	    String sql = "SELECT COUNT(*) FROM beneficiario";

	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        if (rs.next()) {
	            return rs.getInt(1);  // devuelve el COUNT(*)
	        }
	    }finally {
			ConnectionManager.disconnect();
		}
	    return 0;
	}

}