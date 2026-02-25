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
import ar.edu.unrn.seminario.modelo.Beneficiario;
import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.Coordenada;
import ar.edu.unrn.seminario.modelo.Orden;
import ar.edu.unrn.seminario.modelo.OrdenPedido;
import ar.edu.unrn.seminario.modelo.OrdenRetiro;
import ar.edu.unrn.seminario.modelo.SolicitudBien;
import ar.edu.unrn.seminario.modelo.Visita;
import ar.edu.unrn.seminario.modelo.Voluntario;
import ar.edu.unrn.seminario.modelo.Orden.EstadoOrden;
import ar.edu.unrn.seminario.modelo.OrdenEntrega;

public class OrdenEntregaDAOJDBC implements OrdenEntregaDAO{
VisitaDao visita= new VisitaDAOJDBC();
VoluntarioDAO voluntario= new VoluntarioDAOJDBC();
OrdenPedidoDao op =new OrdenPedidoDAOJDBC();

SolicitudBienesDAO solicitud= new SolicitudBienesJDBC();
	

@Override
public void create(OrdenEntrega orden) throws DAOException {
    try {
        Connection conn = ConnectionManager.getConnection();
        PreparedStatement st = conn.prepareStatement(
            "INSERT INTO ordenEntrega " +
            "(codigo, estado, FechaCreacion, FechaProgramada, codVoluntario, codBeneficiario,codSolicitud) " +
            "VALUES (?, ?, ?, ?, ?, ?,?)"
        );

        st.setString(1, orden.getCodigo());
        st.setString(2, orden.getEstadoString());
        st.setDate(3, java.sql.Date.valueOf(orden.getFechaEmision()));

        if (orden.getFechaHoraProgramada() != null) {
            st.setDate(4, java.sql.Date.valueOf(orden.getFechaHoraProgramada()));
        } else {
            st.setNull(4, java.sql.Types.DATE);
        }

        if (orden.getVoluntario() != null) {
            st.setString(5, orden.getVoluntario().getCodigo());
        } else {
            st.setNull(5, java.sql.Types.VARCHAR);
        }

        st.setString(6, orden.getBeneficiario().getCodigo());
        st.setString(7, orden.getSolicitud().getCodigo());
        if (st.executeUpdate() <= 0) {
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

        if (orden.getFechaHoraProgramada() != null) {
            st.setDate(2, java.sql.Date.valueOf(orden.getFechaHoraProgramada()));
        } else {
            st.setNull(2, java.sql.Types.DATE);
        }

        if (orden.getVoluntario() != null) {
            st.setString(3, orden.getVoluntario().getCodigo());
        } else {
            st.setNull(3, java.sql.Types.VARCHAR);
        }

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
	            "SELECT codigo, estado, FechaCreacion, FechaProgramada, codVoluntario, codBeneficiario,codSolicitud " +
	            "FROM ordenEntrega WHERE codigo = ?"
	        );

	        st.setString(1, codigo);
	        ResultSet rs = st.executeQuery();

	        if (rs.next()) {

	            String estado = rs.getString("estado");
	            LocalDate fechaCreacion = rs.getDate("FechaCreacion").toLocalDate();
	           
	            LocalDate fechaProg = null;
	            if (rs.getDate("FechaProgramada") != null) {
	                fechaProg = rs.getDate("FechaProgramada").toLocalDate();
	            }

	            BeneficiarioDAO beneficiarioDAO = new BeneficiarioDAOJDBC();
	            Beneficiario beneficiario =
	                beneficiarioDAO.find(rs.getString("codBeneficiario"));
	            Voluntario voluntario = null;
	            if (rs.getString("codVoluntario") != null) {
	                voluntario = this.voluntario.find(rs.getString("codVoluntario"));
	            }
	            String codSolicitud = rs.getString("codSolicitud");

	            SolicitudBien solicitud = this.solicitud.find(codSolicitud);
	            if (solicitud == null) {
	                throw new DAOException(
	                    "OrdenEntrega " + codigo + " tiene solicitud inexistente: " + codSolicitud
	                );
	            }
	            ArrayList<Visita> visitas =
	                this.visita.findAllOrdenEntrega(codigo);

	            orden = new OrdenEntrega(
	                fechaCreacion,
	                estado,
	                codigo,
	                fechaProg,
	                visitas,solicitud,
	                beneficiario,
	                voluntario
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
	    final String SQL = "SELECT codigo FROM ordenEntrega";
	    List<String> codigos = new ArrayList<>();

	    // 1) leer códigos (sin llamar a find acá adentro)
	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement st = conn.prepareStatement(SQL);
	         ResultSet rs = st.executeQuery()) {

	        while (rs.next()) {
	            codigos.add(rs.getString("codigo"));
	        }

	    } catch (SQLException e) {
	        throw new DAOException("Error FIND ALL OrdenEntrega: " + e.getMessage() + ".OE600");
	    } finally {
	        ConnectionManager.disconnect();
	    }

	    // 2) ahora sí, llamar find por cada código
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
	            while (rs.next()) {
	                codigos.add(rs.getString("codigo"));
	            }
	        }

	    } catch (SQLException e) {
	        throw new DAOException("Error FIND ALL OE por beneficiario: " + e.getMessage() + ".OE610");
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