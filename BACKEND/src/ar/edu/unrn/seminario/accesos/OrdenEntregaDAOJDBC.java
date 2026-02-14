package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
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
import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.Coordenada;
import ar.edu.unrn.seminario.modelo.Orden;
import ar.edu.unrn.seminario.modelo.OrdenPedido;
import ar.edu.unrn.seminario.modelo.OrdenRetiro;
import ar.edu.unrn.seminario.modelo.Visita;
import ar.edu.unrn.seminario.modelo.Voluntario;
import ar.edu.unrn.seminario.modelo.Orden.EstadoOrden;
import ar.edu.unrn.seminario.modelo.OrdenEntrega;

public class OrdenEntregaDAOJDBC implements OrdenEntregaDAO{
VisitaDao visita;
VoluntarioDAO voluntario;
OrdenPedidoDao op;
	
	@Override
	public void create(OrdenEntrega orden) throws DAOException {
	    try {
	        Connection conn = ConnectionManager.getConnection();
	        PreparedStatement st = conn.prepareStatement(
	            "INSERT INTO ordenEntrega (codigo, estado, FechaCreacion, FechaProgramada, codVoluntario) " +
	            "VALUES (?, ?, ?, ?, ?)"
	        );

	        st.setString(1, orden.getCodigo());
	        st.setString(2, orden.getEstadoString());
	        st.setDate(3, java.sql.Date.valueOf(orden.getFechaEmision()));
	        st.setTimestamp(4, java.sql.Timestamp.valueOf(orden.getFechaHoraProgramada()));
	        st.setString(5, orden.getVoluntario().getCodigo());
	    

	        int filas = st.executeUpdate();
	        if (filas <= 0) {
	            throw new DAOException("No se insertó OrdenEntrega");
	        }

	    } catch (SQLException e) {
	        throw new DAOException("Error INSERT OrdenEntrega: " + e.getMessage() + ".OE100");
	    } finally {
	        ConnectionManager.disconnect();
	    }
	}


	@Override
	public void update(OrdenEntrega orden) throws DAOException {
	    try {
	        Connection conn = ConnectionManager.getConnection();
	        PreparedStatement st = conn.prepareStatement(
	            "UPDATE ordenEntrega SET estado=?, FechaProgramada=?, codVoluntario=? WHERE codigo=?"
	        );

	        st.setString(1, orden.getEstadoString());
	        st.setTimestamp(2, java.sql.Timestamp.valueOf(orden.getFechaHoraProgramada()));
	        st.setString(3, orden.getVoluntario().getCodigo());
	        st.setString(4, orden.getCodigo());

	        if (st.executeUpdate() <= 0) {
	            throw new DAOException("No se actualizó OrdenEntrega");
	        }

	    } catch (SQLException e) {
	        throw new DAOException("Error UPDATE OrdenEntrega: " + e.getMessage() + ".OE200");
	    } finally {
	        ConnectionManager.disconnect();
	    }
	}


	@Override
	public void remove(OrdenEntrega orden) throws DAOException{
		
	}

	@Override
	public void remove(String codigo) throws DAOException {
	    try {
	        Connection conn = ConnectionManager.getConnection();
	        PreparedStatement st = conn.prepareStatement(
	            "DELETE FROM ordenEntrega WHERE codigo = ?"
	        );

	        st.setString(1, codigo);

	        if (st.executeUpdate() <= 0) {
	            throw new DAOException("OrdenEntrega inexistente");
	        }

	    } catch (SQLException e) {
	        throw new DAOException("Error DELETE OrdenEntrega: " + e.getMessage() + ".OE300");
	    } finally {
	        ConnectionManager.disconnect();
	    }
	}


	@Override
	public OrdenEntrega find(String codigo) throws DAOException {
	    OrdenEntrega orden = null;

	    try {
	        Connection conn = ConnectionManager.getConnection();
	        PreparedStatement st = conn.prepareStatement(
	            "SELECT estado, FechaCreacion, FechaProgramada, codVoluntario " +
	            "FROM ordenEntrega WHERE codigo = ?"
	        );
	        st.setString(1, codigo);

	        ResultSet rs = st.executeQuery();
	        if (rs.next()) {

	            LocalDate fechaCreacion =
	                rs.getDate("FechaCreacion").toLocalDate();

	            LocalDateTime fechaProg =
	                rs.getTimestamp("FechaProgramada").toLocalDateTime();

	            orden = new OrdenEntrega(
	                new ArrayList<>(), // bienes se cargan desde visitas
	                null,              // beneficiario viene indirecto
	                fechaCreacion
	            );

	            orden.setFechaHoraProgramada(fechaProg);
	            orden.setEstado(
	                EstadoOrden.valueOf(rs.getString("estado"))
	            );

	            // visitas SOLO por OrdenEntrega
	            orden.setVisitas(
	                visita.findAllOrdenEntrega(codigo)
	            );
	        }

	    } catch (Exception e) {
	        throw new DAOException(
	            "Error FIND OrdenEntrega: " + e.getMessage() + ".OE400"
	        );
	    } finally {
	        ConnectionManager.disconnect();
	    }

	    return orden;
	}



	@Override
	public List<OrdenEntrega> findAll() throws DAOException {
	    ArrayList<OrdenEntrega> ordenes = new ArrayList<>();

	    try {
	        Connection conn = ConnectionManager.getConnection();
	        PreparedStatement st = conn.prepareStatement(
	            "SELECT codigo FROM ordenEntrega"
	        );

	        ResultSet rs = st.executeQuery();
	        while (rs.next()) {
	            ordenes.add(this.find(rs.getString("codigo")));
	        }

	    } catch (SQLException e) {
	        throw new DAOException(
	            "Error al procesar consulta FIND ALL OrdenEntrega: " +
	            e.getMessage() + ".OE600"
	        );
	    } finally {
	        ConnectionManager.disconnect();
	    }

	    return ordenes;
	}

	
	@Override
	public int obtenerCantidadOE() throws SQLException {
	    String sql = "SELECT COUNT(*) FROM ordenEntrega";

	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        if (rs.next()) {
	            return rs.getInt(1);
	        }
	    } finally {
	        ConnectionManager.disconnect();
	    }

	    return 0;
	}
	public int obtenerMaximoOrdenEntrega() throws SQLException {
	    String sql = "SELECT MAX(codigo) FROM ordenEntrega";

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