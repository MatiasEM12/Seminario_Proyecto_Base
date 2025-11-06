package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.modelo.Donante;
import ar.edu.unrn.seminario.modelo.Orden;
import ar.edu.unrn.seminario.modelo.OrdenPedido;
import ar.edu.unrn.seminario.modelo.Rol;

public class OrdenPedidoDAOJDBC implements OrdenPedidoDao{

	@Override
	public void create(OrdenPedido orden) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO ordenPedido (fechaemision, cargaPesada, observaciones, codDonante, codDonacion)"
							+ " VALUES (?, ?, ?, ?)");
			
			statement.setObject(1, orden.getFechaEmision());
			statement.setBoolean(2, orden.isCargaPesada());
			statement.setString(3, orden.getObservaciones());
			statement.setString(4, orden.getCodDonante());
			statement.setString(4, orden.getCodDonacion());
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				// System.out.println("Modificando " + cantidad + " registros");
			} else {
				System.out.println("Error al actualizar");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
			System.out.println("Error al procesar consulta");
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}
		
	}

	@Override
	public void update(OrdenPedido orden) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE roles SET fechaEmision = ?, cargaPesada = ?, observaciones = ? WHERE codigo = ?"); //elimine descripcion para probar tabla base

			statement.setString(1, orden.getFechaEmision());
			statement.setBoolean(2, orden.isCargaPesada());
			statement.setString(3, orden.getObservaciones());
			statement.setString(4, orden.getCodigo());
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				 System.out.println("El Rol se ha actualizado correctamente");
			} else {
				System.out.println("Error al actualizar");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
			System.out.println("Error al procesar consulta");
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}
	}

	@Override
	public void remove(String id) {
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
		            System.out.println("No se encontró el rol con ese código.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar Orden");
		}
	}

	@Override
	public void remove(OrdenPedido orden) {
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
		            System.out.println("No se encontró el rol con ese código.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar Orden");
		}		
	}

	@Override
	public OrdenPedido find(String codigo) {
		Orden orden = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("SELECT o.codigo, o.fechaEmision, o.cargaPesada ,o.observaciones, o.codDonante, o.codDonacion" + " FROM OrdenPedido o " + " WHERE o.codigo = ?");

			statement.setString(1, codigo);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				orden = new OrdenPedido(rs.getObject("fechaEmision"),rs.getBoolean("cargaPesada"),
						rs.getString("observaciones"), rs.getString("codDonante"),rs.getString("codDonacion"));
			}
			

		} catch (SQLException e) {
			System.out.println("Error al procesar consulta");
			// TODO: disparar Exception propia
			// throw new AppException(e, e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			// TODO: disparar Exception propia
			// throw new AppException(e, e.getCause().getMessage(), e.getMessage());
		} finally {
			ConnectionManager.disconnect();
		}

		return orden;
	}

	@Override
	public List<OrdenPedido> findAll() {
		List<OrdenPedido> ordenes = new ArrayList<>();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("SELECT o.codigo, o.fechaEmision, o.cargaPesada ,o.observaciones, o.codDonante, o.codDonacion" + " FROM OrdenPedido o " + " WHERE o.codigo = ?");

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				OrdenPedido orden = new OrdenPedido(rs.getTimestamp("fechaEmision"),rs.getBoolean("cargaPesada"),
						rs.getString("observaciones"), rs.getString("codDonante"),rs.getString("codDonacion"));
				ordenes.add(orden);
			}
			

		} catch (SQLException e) {
			System.out.println("Error al procesar consulta");
			// TODO: disparar Exception propia
			// throw new AppException(e, e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			// TODO: disparar Exception propia
			// throw new AppException(e, e.getCause().getMessage(), e.getMessage());
		} finally {
			ConnectionManager.disconnect();
		}

		return ordenes;
		
	}
	
}
