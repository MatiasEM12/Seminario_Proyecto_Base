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
import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.Coordenada;
import ar.edu.unrn.seminario.modelo.Visita;

public class VisitaDAOJDBC implements VisitaDao{
BienDAO biendao= new BienDAOJDBC();

@Override
public void create(Visita visita)
        throws DataNullException, DataLengthException, DAOException {

    try {
        Connection conn = ConnectionManager.getConnection();
        PreparedStatement statement = conn.prepareStatement(
            "INSERT INTO visitas " +
            "(codigo, tipo, observaciones, estado, FechaVisita, codOrdenRetiro, codOrdenEntrega) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)"
        );

        // Datos básicos
        statement.setString(1, visita.getCodigo());
        statement.setString(2, visita.getTipo());
        statement.setString(3, visita.getObservaciones());
        statement.setString(4, visita.getEstado());
        statement.setDate(5, java.sql.Date.valueOf(visita.getFechaVisita()));
 

        // Relación con Orden (solo una)
        if (visita.getCodOrdenRetiro() != null) {
            statement.setString(6, visita.getCodOrdenRetiro());
            statement.setNull(7, java.sql.Types.VARCHAR);

        } else if (visita.getCodOrdenEntrega() != null) {
            statement.setNull(8, java.sql.Types.VARCHAR);
            statement.setString(9, visita.getCodOrdenEntrega());

        } else {
            throw new DAOException("La Visita debe pertenecer a una OrdenRetiro o una OrdenEntrega");
        }

        int cantidad = statement.executeUpdate();
        if (cantidad <= 0) {
            throw new DAOException("No se insertó la Visita");
        }

    } catch (SQLException e) {
        throw new DAOException(
            "Error al procesar consulta (INSERT Visita): " + e.getMessage() + ".codigo VT100"
        );
    } finally {
        ConnectionManager.disconnect();
    }
}


	@Override
	public void update(Visita visita) throws DAOException{
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(
				    "UPDATE visitas SET FechaVisita=?, observaciones=?, tipo=?, codOrdenRetiro=? WHERE codigo=?"
				);

			java.sql.Date fechaSQL = java.sql.Date.valueOf(visita.getFechaVisita());
			
			statement.setDate(1, fechaSQL);
			statement.setString(2, visita.getObservaciones());
			statement.setString(3, visita.getTipo());
			statement.setObject(4, visita.getRetiro());
			statement.setObject(5, visita.getCodigo());
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				// System.out.println("Modificando " + cantidad + " registros");
			} else {
				throw new DAOException("Error al actualizar");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
			throw new DAOException("Error al procesar consulta (INSERT Visita): " + e.getMessage()+".codigo VT200");
	    } finally {
	        ConnectionManager.disconnect();
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
	public Visita find(String codigo) throws DataNullException, DataLengthException, DAOException {
	   
		Visita visita=null;
		
		
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement(
				    "SELECT codigo, FechaVisita, observaciones, tipo, codOrdenRetiro " +
				    "FROM visitas WHERE codigo = ?"
				);

			sent.setString(1, codigo);
			ResultSet rs = sent.executeQuery();
			if (rs.next()) {
				
				LocalDate localDate = rs.getDate("FechaVisita").toLocalDate();
				visita=new Visita(localDate,rs.getString("observaciones"),rs.getString("tipo"),rs.getString("ordenRetiro")
						,biendao.findBienVisita("codigo"),rs.getString("codigo"));
				
			}
		}
		catch(SQLException e){
			throw new DAOException("Error al procesar consulta"+ e.getMessage()+".codigo VT500");
		}
		catch (Exception e) {
			throw new DAOException("Error inesperado: " + e.getMessage()+".codigo VT501");
		} 
		finally {
			ConnectionManager.disconnect();
		}	 
		return visita;

	}



	@Override
	public List<Visita> findAll() throws DataNullException, DataLengthException, DAOException {
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
	        throws DataNullException, DataLengthException, DAOException {

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
	        throws DataNullException, DataLengthException, DAOException {

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
	                
	                return Integer.parseInt(maxCodigo.substring(1));
	            }
	        }
	    } finally {
	        ConnectionManager.disconnect();
	    }
	    return 0;
	}

}
