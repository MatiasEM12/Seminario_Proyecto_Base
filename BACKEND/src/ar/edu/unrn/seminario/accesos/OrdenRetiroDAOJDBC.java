package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.Orden;
import ar.edu.unrn.seminario.modelo.OrdenPedido;
import ar.edu.unrn.seminario.modelo.OrdenRetiro;
import ar.edu.unrn.seminario.modelo.Visita;

public class OrdenRetiroDAOJDBC implements OrdenRetiroDao{

	@Override
	public void create(OrdenRetiro orden) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO ordenRetiro (fechaemision, ordenPedido, visitas)"
							+ " VALUES (?, ?, ?)");
			
			statement.setObject(1, orden.getFechaEmision());
			statement.setObject(2, orden.getPedido());
			statement.setObject(3, orden.getVisitas());
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
	public void update(OrdenRetiro orden) {
		// TODO Auto-generated method stub
		
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
		            System.out.println("Rol eliminado correctamente.");
		        } else {
		            System.out.println("No se encontró el rol con ese código.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar Orden");
		}
		
	}

	@Override
	public void remove(String id) {
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM OrdenRetiro WHERE codigo = ?"
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
	public OrdenRetiro find(String codigo) {
		OrdenRetiro orden = null;
		ArrayList<Visita> visitas=null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("SELECT o.codigo, o.fechaEmision, o.ordenPedido" + " FROM OrdenRetiro o " + " WHERE o.codigo = ?");

			statement.setString(1, codigo);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				OrdenPedido ordenPedido=new OrdenPedido(rs.getTimestamp("fechaEmision"),rs.getBoolean("cargaPesada"),
						rs.getString("observaciones"), rs.getString("codDonante"),rs.getString("codDonacion"));
				visitas= new ArrayList<Visitas>(rs.getObject("fechaVisita", rs.getString("observaciones"), rs.getString("tipo"),this,
			null));
				
				orden = new OrdenRetiro(rs.getObject("fechaEmision"),ordenPedido,visitas);
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
	public List<OrdenRetiro> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
