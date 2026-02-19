package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataListException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.Coordenada;
import ar.edu.unrn.seminario.modelo.Visita;

public class VisitaDAOJDBC implements VisitaDao{
BienDAO biendao= new BienDAOJDBC();
@Override
public void create(Visita visita)
        throws DataNullException, DataLengthException, DAOException {

    try (Connection conn = ConnectionManager.getConnection();
         PreparedStatement st = conn.prepareStatement(
                 "INSERT INTO visitas " +
                 "(codigo, tipo, observaciones, estado, FechaVisita, codOrdenRetiro, codOrdenEntrega) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?)"
         )) {

        st.setString(1, visita.getCodigo());
        st.setString(2, visita.getTipo());
        st.setString(3, visita.getObservaciones());
        st.setString(4, visita.getEstado());
        st.setDate(5, Date.valueOf(visita.getFechaVisita()));

        st.setString(6, visita.getCodOrdenRetiro() != null ? visita.getCodOrdenRetiro() : "");
        st.setString(7, visita.getCodOrdenEntrega() != null ? visita.getCodOrdenEntrega() : "");

        if (st.executeUpdate() <= 0) {
            throw new DAOException("No se insertó la visita");
        }

    } catch (SQLException e) {
        throw new DAOException("Error INSERT visita VT100: " + e.getMessage()+ e);
    }
}

@Override
public void update(Visita visita) throws DAOException {
    try (Connection conn = ConnectionManager.getConnection();
         PreparedStatement st = conn.prepareStatement(
                 "UPDATE visitas SET FechaVisita=?, observaciones=?, tipo=?, estado=?, " +
                 "codOrdenRetiro=?, codOrdenEntrega=? WHERE codigo=?"
         )) {

        st.setDate(1, Date.valueOf(visita.getFechaVisita()));
        st.setString(2, visita.getObservaciones());
        st.setString(3, visita.getTipo());
        st.setString(4, visita.getEstado());

        // Permite NULL en ambos campos
        st.setString(5, visita.getCodOrdenRetiro());
        st.setString(6, visita.getCodOrdenEntrega());

        st.setString(7, visita.getCodigo());

        if (st.executeUpdate() <= 0) {
            throw new DAOException("No se actualizó la visita");
        }

    } catch (SQLException e) {
        throw new DAOException("Error UPDATE visita VT200: " + e.getMessage()+ e);
    }
}

	@Override
	public void remove(String codigo) throws DAOException{
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM visita WHERE codigo = ?"
		        );

		        statement.setString(1, codigo);

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("Visita eliminada correctamente.");
		        } else {
		        	throw new DAOException("No se encontró la visita con ese código.");
		        }
			
		}catch(SQLException e) {
			throw new DAOException("Error al Eliminar Visita"+".codigo VT300");
		}finally {
	        ConnectionManager.disconnect();
	    }

	}

	@Override
	public void remove(Visita visita) throws DAOException{
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM visita WHERE codigo = ?"
		        );

		        statement.setString(1, visita.getCodigo());

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("Visita eliminada correctamente.");
		        } else {
		        	throw new DAOException("No se encontró la visitacon ese código.");
		        }
			
		}catch(SQLException e) {
			throw new DAOException("Error al Eliminar visita"+".codigo VT400");
		}finally {
	        ConnectionManager.disconnect();
	    }

	}

	@Override
	public Visita find(String codigo)
	        throws DataNullException, DataLengthException, DAOException, DataDateException, DataEmptyException, DataListException {

	    Visita visita = null;

	    try {
	        Connection conn = ConnectionManager.getConnection();
	        PreparedStatement st = conn.prepareStatement(
	            "SELECT codigo, FechaVisita, observaciones, tipo, codOrdenRetiro, codOrdenEntrega " +
	            "FROM visitas WHERE codigo = ?"
	        );

	        st.setString(1, codigo);
	        ResultSet rs = st.executeQuery();

	        if (rs.next()) {

	            LocalDate fecha = rs.getDate("FechaVisita").toLocalDate();
	            String codOrden =
	                rs.getString("codOrdenRetiro") != null
	                ? rs.getString("codOrdenRetiro")
	                : rs.getString("codOrdenEntrega");

	            visita = new Visita(
	                fecha,
	                rs.getString("observaciones"),
	                rs.getString("tipo"),
	                codOrden,
	                biendao.findBienVisita(codigo),
	                rs.getString("codigo")
	            );
	        }

	    } catch (SQLException e) {
	        throw new DAOException("Error FIND visita VT500"+e);
	    } finally {
	        ConnectionManager.disconnect();
	    }

	    return visita;
	}


	@Override
	public List<Visita> findAll() throws DataNullException, DataLengthException, DAOException, DataDateException, DataEmptyException, DataListException {
	    List<Visita> visitas = new ArrayList<>();

	    String sqlVisitas =
	        "SELECT v.codigo, v.fechaVisita, v.observaciones, v.tipo, v.ordenRetiro " +
	        "FROM Visita v";


	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement stVisitas = conn.prepareStatement(sqlVisitas);
	         ResultSet rs = stVisitas.executeQuery()) {
	        while (rs.next()) {
	
	        	String codigo = rs.getString("codigo");
	        	Visita v = this.find(codigo);

	            visitas.add(v);
	           
	        }

	    } catch (SQLException e) {
	    	throw new DAOException("Error al procesar consulta: " + e.getMessage()+".codigo VT600");
	        // TODO: lanzar tu excepción propia (DataAccessException, etc.)
	    } finally {
	        ConnectionManager.disconnect();
	    }

	    return visitas;
	}

	@Override
	public ArrayList<Visita> findAllOrdenRetiro(String codOrdenRetiro)
	        throws DataNullException, DataLengthException, DAOException, DataDateException, DataEmptyException, DataListException {

	    ArrayList<Visita> visitas = new ArrayList<>();

	    try {
	        Connection conn = ConnectionManager.getConnection();
	        PreparedStatement st = conn.prepareStatement(
	            "SELECT codigo FROM visitas WHERE codOrdenRetiro = ?"
	        );
	        st.setString(1, codOrdenRetiro);

	        ResultSet rs = st.executeQuery();

	        while (rs.next()) {
	            String codigoVisita = rs.getString("codigo");
	            Visita v = this.find(codigoVisita);
	            visitas.add(v);
	        }

	    } catch (SQLException e) {
	        throw new DAOException(
	            "Error al procesar consulta: " + e.getMessage() + ".codigo VT700"
	        );
	    } finally {
	        ConnectionManager.disconnect();
	    }

	    return visitas;
	}
	@Override
	public ArrayList<Visita> findAllOrdenEntrega(String codOrdenEntrega)
	        throws DataNullException, DataLengthException, DAOException, DataDateException, DataEmptyException, DataListException {

	    ArrayList<Visita> visitas = new ArrayList<>();

	    try {
	        Connection conn = ConnectionManager.getConnection();
	        PreparedStatement st = conn.prepareStatement(
	            "SELECT codigo FROM visitas WHERE codOrdenEntrega = ?"
	        );
	        st.setString(1, codOrdenEntrega);

	        ResultSet rs = st.executeQuery();

	        while (rs.next()) {
	            String codigoVisita = rs.getString("codigo");
	            Visita v = this.find(codigoVisita);
	            visitas.add(v);
	        }

	    } catch (SQLException e) {
	        throw new DAOException(
	            "Error al procesar consulta: " + e.getMessage() + ".codigo VT700"
	        );
	    } finally {
	        ConnectionManager.disconnect();
	    }

	    return visitas;
	}

	public int obtenerCantidadVisitas() throws SQLException {
	    String sql = "SELECT COUNT(*) FROM visitas";

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
	public int obtenerMaximoVisitas() throws SQLException {
	    String sql = "SELECT MAX(codigo) FROM visitas";

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
