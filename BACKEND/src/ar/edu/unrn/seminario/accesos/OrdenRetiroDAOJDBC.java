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

import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.StateChangeException;
import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.Coordenada;
import ar.edu.unrn.seminario.modelo.Orden;
import ar.edu.unrn.seminario.modelo.OrdenPedido;
import ar.edu.unrn.seminario.modelo.OrdenRetiro;
import ar.edu.unrn.seminario.modelo.Visita;
import ar.edu.unrn.seminario.modelo.Voluntario;
import ar.edu.unrn.seminario.modelo.Orden.EstadoOrden;

public class OrdenRetiroDAOJDBC implements OrdenRetiroDao {

    VisitaDao visita;
    VoluntarioDAO voluntario;
    OrdenPedidoDao op;

    @Override
    public void create(OrdenRetiro orden) {
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO OrdenRetiro (codigo, tipo, estado, FechaCreacion, codVoluntario, codOrdenPedido) " +
                "VALUES (?, ?, ?, ?, ?, ?)"
            );

            java.sql.Date fechaSQL = java.sql.Date.valueOf(orden.getFechaEmision());

            statement.setString(1, orden.getCodigo());
            statement.setString(2, OrdenRetiro.getTipo()); // o "OrdenRetiro"
            statement.setString(3, orden.getEstadoString());
            statement.setDate(4, fechaSQL);
            statement.setString(5, orden.getVoluntario().getCodigo());
            statement.setString(6, orden.getPedido().getCodigo());

            int cantidad = statement.executeUpdate();
            if (cantidad <= 0) {
                System.out.println("Error al insertar OrdenRetiro");
            }

        } catch (SQLException e) {
            System.out.println("Error al procesar consulta (INSERT OrdenRetiro): " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }
    }

	@Override
	public void update(OrdenRetiro orden) {
		try {
	        Connection conn = ConnectionManager.getConnection();
	        PreparedStatement statement = conn.prepareStatement(
	            "UPDATE OrdenRetiro SET estado = ?, Fecha_Emision = ?, codVoluntario = ?, codOrdenPedido = ? WHERE codigo = ?"
	        );

	        // Fecha
	        java.sql.Date fechaSQL = java.sql.Date.valueOf(orden.getFechaEmision());
	        statement.setString(1, orden.getEstadoRetiro() != null ? orden.getEstadoRetiro() : orden.getEstadoString());
	        statement.setDate(2, fechaSQL);

	        // voluntario puede ser null
	        if (orden.getVoluntario() != null) {
	            statement.setString(3, orden.getVoluntario().getCodigo());
	        } else {
	            statement.setNull(3, java.sql.Types.VARCHAR);
	        }

	        // pedido (puede no cambiar)
	        if (orden.getPedido() != null) {
	            statement.setString(4, orden.getPedido().getCodigo());
	        } else {
	            statement.setNull(4, java.sql.Types.VARCHAR);
	        }

	        statement.setString(5, orden.getCodigo());

	        int cantidad = statement.executeUpdate();
	        if (cantidad <= 0) {
	            System.out.println("OrdenRetiro.update: no se actualizó ningún registro para codigo=" + orden.getCodigo());
	        }
	    } catch (SQLException e) {
	        System.out.println("Error al actualizar OrdenRetiro: " + e.getMessage());
	    } finally {
	        ConnectionManager.disconnect();
	    }
		
	}

	@Override
	public void remove(OrdenRetiro orden) {
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM OrdenRetiro WHERE codigo = ?"
		        );

		        statement.setString(1, orden.getCodigo());

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("Orden Retiro eliminado correctamente.");
		        } else {
		            System.out.println("No se encontró la Orden Retiro con ese código.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar Orden");
		}
		
	}

	@Override
	public void remove(String codigo) {
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM OrdenRetiro WHERE codigo = ?"
		        );

		        statement.setString(1,codigo);

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("Orden Retiro eliminado correctamente.");
		        } else {
		            System.out.println("No se encontró la Orden Retiro con ese código.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar Orden");
		}
		
	}

	public OrdenRetiro find(String codigo) throws DataNullException, DataLengthException, StateChangeException {
        OrdenRetiro orden = null;
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement sent = conn.prepareStatement(
                "SELECT codigo, tipo, estado, FechaCreacion, codVoluntario, codOrdenPedido " +
                "FROM OrdenRetiro WHERE codigo = ?"
            );
            sent.setString(1, codigo);
            ResultSet rs = sent.executeQuery();

            if (rs.next()) {
                LocalDate fecha = rs.getDate("FechaCreacion").toLocalDate();

                Voluntario vol = voluntario.find(rs.getString("codVoluntario"));
                OrdenPedido pedido = op.find(rs.getString("codOrdenPedido"));
                ArrayList<Visita> visitas = visita.findAll(rs.getString("codigo"));

                orden = new OrdenRetiro(
                        rs.getString("codigo"),
                        rs.getString("estado"),
                        fecha,
                        vol,
                        pedido,
                        visitas
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al procesar consulta (SELECT OrdenRetiro): " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }
        return orden;
    }

    @Override
    public List<OrdenRetiro> findAll() throws DataNullException, DataLengthException, StateChangeException {
        ArrayList<OrdenRetiro> ordenes = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement sent = conn.prepareStatement(
                "SELECT codigo FROM OrdenRetiro"
            );
            ResultSet rs = sent.executeQuery();
            while (rs.next()) {
                ordenes.add(this.find(rs.getString("codigo")));
            }
        } catch (SQLException e) {
            System.out.println("Error al procesar consulta (SELECT ALL OrdenRetiro): " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }
        return ordenes;
    }

}
