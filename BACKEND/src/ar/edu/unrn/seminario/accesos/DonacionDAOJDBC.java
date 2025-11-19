     package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;
import ar.edu.unrn.seminario.exception.StateChangeException;
import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.Coordenada;
import ar.edu.unrn.seminario.modelo.Donacion;
import ar.edu.unrn.seminario.modelo.Donante;
import ar.edu.unrn.seminario.modelo.OrdenPedido;
import ar.edu.unrn.seminario.modelo.Rol;

public class DonacionDAOJDBC implements DonacionDAO{

DonanteDao d;
OrdenPedidoDao op;
BienDAO  b;
	
	@Override
	public void create(Donacion donacion) throws DataNullException {
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
			
			Donante donante = donacion.getDonante();
			if (donante == null) {
			    throw new DataNullException("La donación no tiene donante asociado");
			}
			statement.setString(4, donante.getCodigo());
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
	        System.out.println("Error al procesar consulta (INSERT Donacion): " + e.getMessage());
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

			LocalDate fecha = donacion.getFechaDonacion();
			java.sql.Date fechaSQL = java.sql.Date.valueOf(fecha);
			
			statement.setString(1, donacion.getCodigo());
			statement.setString(2, donacion.getObservacion());
			statement.setDate(3, fechaSQL);
			statement.setString(4, donacion.getDonante() != null ? donacion.getDonante().getCodigo() : null);
			statement.setString(5,  donacion.getPedido() != null ? donacion.getPedido().getCodigo() : null);
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
				   
				   
				donacion =new Donacion (localDate,rs.getString("observacion"),b.findBienDonacion(rs.getString("codigo")),d.find(rs.getString("codigoDonante")) , 
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
	public List<Donacion> findAll() throws DataNullException, DataEmptyException, DataObjectException, DataDateException{
		List<Donacion> listado = new ArrayList<>();
	    String sql = "SELECT d.codigo, d.observacion, d.Fecha_Donacion, d.codigoDonante, d.codigoOrdenPedido FROM donacion d ";

	    try (Connection conn = ConnectionManager.getConnection();
	         Statement sentencia = conn.createStatement();
	         ResultSet resultado = sentencia.executeQuery(sql)) {

	        while (resultado.next()) {
	            String codigo = resultado.getString("codigo");
	            String observacion = resultado.getString("observacion");
	            Date Fecha_Donacion = resultado.getDate("Fecha_Donacion");
	            LocalDate fecha = Fecha_Donacion.toLocalDate();
	            String codigoDonante = resultado.getString("codigoDonante");
	            String codigoOrdenPedido = resultado.getString("codigoOrdenPedido");
	            Donante donante = d.find(codigoDonante);
	            OrdenPedido pedido = op.find(codigoOrdenPedido);
	            ArrayList<Bien> bienes = b.findBienDonacion(codigo);
	            // Usamos el constructor que ya tenés:
	            Donacion donacion = new Donacion(fecha,observacion,bienes,donante,pedido,codigo);
	            listado.add(donacion);
	        }
	    } catch (SQLException e) {
			System.out.println("Error de mySql\n" + e.toString());
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}

	    return listado;
	}
	
	public List<Donacion> findAllPendiente() throws DataNullException, DataEmptyException, DataObjectException, DataDateException {
		List<Donacion> listado = new ArrayList<>();
	    String sql = "SELECT d.codigo, d.observacion, d.Fecha_Donacion, d.codigoDonante, d.codigoOrdenPedido "
	    		+ "FROM donacion d "
	    		+"JOIN ordenpedido o ON d.codigoOrdenPedido = o.codigo JOIN donante don ON d.codigoDonante = don.codigo "
	    		+ "WHERE o.estado = 'PENDIENTE'";

	    try (Connection conn = ConnectionManager.getConnection();
	         Statement sentencia = conn.createStatement();
	         ResultSet resultado = sentencia.executeQuery(sql)) {

	        while (resultado.next()) {
	            String codigo = resultado.getString("codigo");
	            String observacion = resultado.getString("observacion");
	            Date Fecha_Donacion = resultado.getDate("Fecha_Donacion");
	            LocalDate fecha = Fecha_Donacion.toLocalDate();
	            String codigoDonante = resultado.getString("codigoDonante");
	            String codigoOrdenPedido = resultado.getString("codigoOrdenPedido");
	            Donante donante = d.find(codigoDonante);
	            OrdenPedido pedido = op.find(codigoOrdenPedido);
	            ArrayList<Bien> bienes = b.findBienDonacion(codigo);
	            // Usamos el constructor que ya tenés:
	            Donacion donacion = new Donacion(fecha,observacion,bienes,donante,pedido,codigo);
	            listado.add(donacion);
	        }
	    } catch (SQLException e) {
			System.out.println("Error de mySql\n" + e.toString());
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}
	    return listado;
	}

	@Override
	public Donacion findPorOrdenPedido(String codigoOrdenPedido) throws DataNullException {

		
		Donacion donacion= null;
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT codigo,observacion,Fecha_Donacion,codigoDonante,codigoOrdenPedido "
			+ "FROM donacion "+ "WHERE codigoOrdenPedido codigo = ?");
			sent.setString(1, codigoOrdenPedido);
			ResultSet rs = sent.executeQuery();
			if (rs.next()) {
				   java.sql.Date sqlDate = rs.getDate("Fecha_DOnacion");
				   java.time.LocalDate localDate = sqlDate.toLocalDate();
				   
				   
				donacion =new Donacion (localDate,rs.getString("observacion"),b.findBienDonacion(rs.getString("codigo")),d.find(rs.getString("codigoDonante")) , 
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

}
