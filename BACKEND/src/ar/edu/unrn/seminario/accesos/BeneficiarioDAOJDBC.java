package ar.edu.unrn.seminario.accesos;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataIntException;
import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataListException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;
import ar.edu.unrn.seminario.modelo.Beneficiario;
import ar.edu.unrn.seminario.modelo.Ubicacion;

public class BeneficiarioDAOJDBC implements BeneficiarioDAO {

    private final UbicacionDAO ubicacionDAO;
    private OrdenEntregaDAO ordenEntega=new OrdenEntregaDAOJDBC();
    private SolicitudBienesDAO solicitud=new SolicitudBienesJDBC();
    public BeneficiarioDAOJDBC() {
        this.ubicacionDAO = new UbicacionDAOJDBC();
    }

    public void create(Beneficiario b) throws DAOException {

        if (b == null) {
            throw new DAOException("Beneficiario nulo");
        }

        final String SQL =
            "INSERT INTO beneficiario " +
            "(codigo, nombre, apellido, dni, contacto, Fecha_Nacimiento, username, aCargo, prioridad, coUbicacion, activo) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

            st.setString(1, b.getCodigo());
            st.setString(2, b.getNombre());
            st.setString(3, b.getApellido());
            st.setString(4, b.getDni());
            st.setString(5, b.getContacto());
            st.setDate(6, Date.valueOf(b.getFecha_nac()));
            st.setString(7, b.getUsername());

            // 🔥 ESTOS DOS ESTABAN MAL
            st.setInt(8, b.getCantAcargo());
            st.setInt(9, b.getPrioridad());

            st.setString(10, b.getUbicacion().getCodigo());
            st.setInt(11, 1); // activo

            st.executeUpdate();

            System.out.println("Beneficiario insertado: " + b.getCodigo());

        } catch (SQLException e) {
            throw new DAOException(
                "Error al procesar INSERT Beneficiario: " + e.getMessage()+
                e
            );
        } finally {
            ConnectionManager.disconnect();
        }
    }


    @Override
    public void update(Beneficiario b) {
        if (b == null) return;

        final String SQL =
            "UPDATE beneficiario SET " +
            "nombre = ?, apellido = ?, dni = ?, contacto = ?, Fecha_Nacimiento = ?, username = ?, aCargo = ?, prioridad = ?,coUbicacion = ? " +
            "WHERE codigo = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement st = conn.prepareStatement(SQL)) {

        	 st.setString(1, b.getCodigo());
             st.setString(2, b.getNombre());
             st.setString(3, b.getApellido());
             st.setString(4, b.getDni());
             st.setString(5, b.getContacto());
             st.setDate(6, Date.valueOf(b.getFecha_nac())); 
             st.setString(7, b.getUsername());
             st.setInt(9, b.getCantAcargo());     
             st.setInt(10, b.getPrioridad());         
             st.setString(11, b.getUbicacion().getCodigo());
             st.setInt(12, 1);         // activo
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
    public Beneficiario find(String codigo) throws DAOException, DataLengthException, DataIntException, DataListException {
        if (codigo == null || codigo.trim().isEmpty()) return null;

        final String SQL =
            "SELECT codigo, nombre, apellido, dni, contacto, Fecha_Nacimiento, username, coUbicacion,aCargo,prioridad " +
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
                int aCargo = Integer.parseInt("aCargo");
                int prioridad= Integer.parseInt("prioridad");

                Ubicacion ubic = (ubicacionDAO != null) ? ubicacionDAO.find(codUbic) : null;

                Beneficiario bene = new Beneficiario(nombre, apellido, fechaNac, dni, contacto, ubic, username,prioridad,aCargo);

            
                bene.setCodigoDesdeBD(cod);
                bene.setOrdenesEntrega(
                	    new ArrayList<>(this.ordenEntega.findAllByBeneficiario(cod))
                	);
                
                bene.setSolicitudBienes(
                	    new ArrayList<>(this.solicitud.findAllByBeneficiario(cod))
                	);
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
    public List<Beneficiario> findAll() throws DAOException, DataLengthException, DataIntException, DataListException {
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
    public int obtenerMaximoBeneficiarios() throws SQLException {
        String sql = "SELECT MAX(codigo) FROM beneficiario";

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