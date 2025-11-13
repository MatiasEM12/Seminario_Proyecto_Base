     package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.Coordenada;
import ar.edu.unrn.seminario.modelo.Donacion;
import ar.edu.unrn.seminario.modelo.Donante;
import ar.edu.unrn.seminario.modelo.OrdenPedido;

public class DonacionDAOJDBC implements DonacionDAO{

DonanteDao d;
OrdenPedidoDao op;
BienDAO  b;
	
	@Override
	public void create(Donacion donacion) {
	try {
			
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO donacion(codigo,observacion,Fecha_Donacion,codigoDonante,codigoOrdenPedido)"
							+ " VALUES (?, ?, ?,?,?)");
			
		
			LocalDate fecha = donacion.getFechaDonacion();
			java.sql.Date fechaSQL = java.sql.Date.valueOf(fecha);
			
			statement.setString(1, donacion.getCodigo());
			statement.setString(2, donacion.getObservacion());
			statement.setDate(3, fechaSQL);
			statement.setString(4, donacion.getDonante().getCodigo());
			statement.setString(5, donacion.getPedido().getCodigo());
			
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
	public void update(Donacion donacion) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE coordenada SET codigo=?,observacion=?,Fecha_Donacion=?,codigoDonante=?,codigoOrdenPedido=? "
							+ "WHERE codigo = ?");

			LocalDate fecha = donacion.getFechaDonacion().toLocalDate();
			java.sql.Date fechaSQL = java.sql.Date.valueOf(fecha);
			
			statement.setString(1, donacion.getCodigo());
			statement.setString(2, donacion.getObservacion());
			statement.setDate(3, fechaSQL);
			statement.setString(4, donacion.getDonante().getCodigo());
			statement.setString(5, donacion.getPedido().getCodigo());
			statement.setString(6, donacion.getCodigo());
			
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				 System.out.println("La coordenada se ha actualizado correctamente");
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
	public void remove(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String codigo) {
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM donacion WHERE codigo = ? "
		        );

		        statement.setString(1, codigo);

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("Donacion eliminada correctamente.");
		        } else {
		            System.out.println("No se encontró la donacion.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar donacion");
		}finally {
			ConnectionManager.disconnect();
		}
		
	}

	@Override
	public void remove(Donacion donacion) {
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM donacion WHERE codigo = ? "
		        );

		        statement.setString(1, donacion.getCodigo());

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("Donacion eliminada correctamente.");
		        } else {
		            System.out.println("No se encontró la donacion.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar donacion");
		}finally {
			ConnectionManager.disconnect();
		}
		
		
	}

	@Override
	public Donacion find(String codigo) {
		Donacion donacion= null;
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT codigo,observacion,Fecha_Donacion,codigoDonante,codigoOrdenPedido "
			+ "FROM donacion "+ "WHERE codigo = ?");
			sent.setString(1, codigo);
			ResultSet rs = sent.executeQuery();
			if (rs.next()) {
				   java.sql.Date sqlDate = rs.getDate("Fecha_DOnacion");
				   java.time.LocalDate localDate = sqlDate.toLocalDate();
				   java.time.LocalDateTime localDateTime = localDate.atStartOfDay();
				   
				donacion =new Donacion (localDateTime,rs.getString("observacion"),b.findBienDonacion(rs.getString("codigo")),d.find(rs.getString("codigoDonante")) , 
						op.find(rs.getString("codigoPedido")),rs.getString("codigo") );
				
			}
		}
		catch(SQLException e){
			System.out.println("Error al procesar consulta"+ e.getMessage());
		}
		catch (Exception e) {
			System.out.println("Error inesperado: " + e.getMessage());
		} 
		finally {
			ConnectionManager.disconnect();
		}	 
		return donacion;
	}

	@Override
	public List<Donacion> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
