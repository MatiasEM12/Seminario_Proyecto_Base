package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.modelo.OrdenPedido;


public class OrdenPedidoDAOJDBC implements OrdenPedidoDao{

	@Override
	public void create(OrdenPedido orden) throws DAOException{
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO ordenPedido (fechaCreacion, cargaPesada, observaciones, codDonante, codDonacion,codigo)"
							+ " VALUES (?, ?, ?, ?, ?,?)");
			java.sql.Date fechaSQL = java.sql.Date.valueOf(orden.getFechaEmision());
			
			statement.setDate(1, fechaSQL);
			statement.setBoolean(2, orden.isCargaPesada());
			statement.setString(3, orden.getObservaciones());
			statement.setString(4, orden.getCodDonante());
			statement.setString(5, orden.getCodDonacion());
			statement.setString(6, orden.getCodigo());
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				// System.out.println("Modificando " + cantidad + " registros");
			} else {
				throw new DAOException("Error al actualizar. codigo error OP100");
				// TODO: disparar Exception propia
			}
			
		} catch (SQLException e) {
			throw new DAOException("Error al procesar consulta (INSERT OrdenPedido): " + e.getMessage()+". codigo error OP101");
	    } finally {
	        ConnectionManager.disconnect();
	    }
		
	}

	@Override
	public void update(OrdenPedido orden) throws DAOException{
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE orden SET fechaEmision = ?, cargaPesada = ?, observaciones = ? WHERE codigo = ?"); //elimine descripcion para probar tabla base
			 // Conversión de LocalDate a java.sql.Date
	        java.sql.Date fechaSQL = java.sql.Date.valueOf(orden.getFechaEmision());

	        // Asignación de parámetros
	        statement.setDate(1, fechaSQL);
			statement.setBoolean(2, orden.isCargaPesada());
			statement.setString(3, orden.getObservaciones());
			statement.setString(4, orden.getCodigo());
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				 System.out.println("La orden se ha actualizado correctamente");
			} else {
				throw new DAOException("Error al actualizar. codigo error OP200");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
			throw new DAOException("Error al procesar consulta. codigo error OP201");
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}
	}

	@Override
	public void remove(String id) throws DAOException{
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM OrdenPedido WHERE codigo = ?"
		        );

		        statement.setString(1, id);

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("Rol eliminado correctamente.");
		        } else {
		        	throw new DAOException("No se encontró el rol con ese código. codigo error OP300");
		        }
			
		}catch(SQLException e) {
			throw new DAOException("Error al Eliminar Orden. codigo error OP301");
		}finally {
			ConnectionManager.disconnect();
		}
	}

	@Override
	public void remove(OrdenPedido orden) throws DAOException{
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM OrdenPedido WHERE codigo = ?"
		        );

		        statement.setString(1, orden.getCodigo());

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("Rol eliminado correctamente.");
		        } else {
		        	throw new DAOException("No se encontró el rol con ese código. codigo error OP400");
		        }
			
		}catch(SQLException e) {
			throw new DAOException("Error al Eliminar Orden. codigo error OP401");
		}finally {
			ConnectionManager.disconnect();
		}
	}

	@Override
	public OrdenPedido find(String codigo) throws DAOException{
		OrdenPedido orden = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("SELECT o.codigo, o.fechaEmision, o.cargaPesada, o.observaciones, o.codDonante, o.codDonacion " +
			                 "FROM OrdenPedido o WHERE o.codigo = ?");

			statement.setString(1, codigo);
			 try (ResultSet rs = statement.executeQuery()) {
		            if (rs.next()) {
		                // Si la columna es DATE:
		                LocalDate fecha = rs.getDate("fechaEmision").toLocalDate();

		                orden = new OrdenPedido(
		                    fecha,
		                    rs.getBoolean("cargaPesada"),
		                    rs.getString("observaciones"),
		                    rs.getString("codDonante"),
		                    rs.getString("codDonacion")
		                );
		                // Seteamos el código que viene de BD (puede coincidir con el parámetro, pero es lo correcto).
		                orden.setCodigo(rs.getString("codigo"));
		            }
		        }

		} catch (SQLException e) {
			throw new DAOException("Error al procesar consulta. codigo error OP500");
			// TODO: disparar Exception propia
			// throw new AppException(e, e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			throw new DAOException("Error al procesar consulta. codigo error OP501"+e);
			// TODO: disparar Exception propia
			// throw new AppException(e, e.getCause().getMessage(), e.getMessage());
		} finally {
			ConnectionManager.disconnect();
		}

		return orden;
	}

	@Override
	public List<OrdenPedido> findAll() throws DAOException{
		List<OrdenPedido> ordenes = new ArrayList<>();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("SELECT o.codigo, o.fechaEmision, o.cargaPesada ,o.observaciones, o.codDonante, o.codDonacion" + " FROM OrdenPedido o " + " WHERE o.codigo = ?");

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				LocalDate fecha = rs.getDate("fechaEmision").toLocalDate();

                OrdenPedido orden = new OrdenPedido(
                    fecha,
                    rs.getBoolean("cargaPesada"),
                    rs.getString("observaciones"),
                    rs.getString("codDonante"),
                    rs.getString("codDonacion")
                );
				ordenes.add(orden);
			}
			

		} catch (SQLException e) {
			throw new DAOException("Error al procesar consulta. codigo error OP600");
			// TODO: disparar Exception propia
			// throw new AppException(e, e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			// TODO: disparar Exception propia
			// throw new AppException(e, e.getCause().getMessage(), e.getMessage());
			throw new DAOException("Error al procesar consulta. codigo error OP601"+e);
		} finally {
			ConnectionManager.disconnect();
		}

		return ordenes;
		
	}
	
}
