package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.modelo.Bien;

public class InventarioDAOJDBC implements InventarioDAO{

	private BienDAOJDBC bienDao= new BienDAOJDBC();
	
public void create(String codBien,String tipoBien, boolean disponible) throws DAOException {
		
		try {
			
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
				
					.prepareStatement("INSERT INTO inventario(codBien,tipoBien,disponible)"
							+ " VALUES (?, ?, ?)");
			

			statement.setString(1, codBien);
			statement.setString(2, tipoBien);
			statement.setBoolean(3, disponible);
	
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				// System.out.println("Modificando " + cantidad + " registros");
			} else {
				throw new DAOException("Error al actualizar. codigo error I100");
				
			}

		} catch (SQLException e) {
			throw new DAOException("Error al procesar consulta (INSERT INVENTARIO): " + e.getMessage() + ". codigo error I101");
	    } finally {
	        ConnectionManager.disconnect();
	    }
		
	}

	@Override
	public void update(String codBien,String tipoBien, boolean disponible) throws DAOException {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE inventario SET codBien=? , tipoBien= ?, disponible = ?WHERE codBien = ?");
	

			statement.setString(1, codBien);
			statement.setString(2, tipoBien);
			statement.setBoolean(3, disponible);
			statement.setString(4, codBien);
			
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				 System.out.println("El inventario se ha actualizado correctamente");
			} else {
				throw new DAOException("Error al actualizar. codigo error I200");
				
			}

		} catch (SQLException e) {
			throw new DAOException("Error al procesar consulta."+ e.getMessage() +" codigo error I201");
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}
		
	}
	
	

	

	@Override
	public void remove(String codBien) throws DAOException {
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM inventario WHERE codBien= ? "
		        );

		        statement.setString(1, codBien);

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("inventario eliminado correctamente el bien.");
		        } else {
		        	throw new DAOException("No se encontró el bien en el inventario. codigo error I300");
		        }
			
		}catch(SQLException e) {
			throw new DAOException("Error al Eliminar bien del inventario."+ e.getMessage() +" codigo error I301");
		}finally {
			ConnectionManager.disconnect();
		}
		
	}



	@Override
	public Bien findBien(String codBien) throws DAOException {
		Bien bien= null;
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT codBien "
			+ "FROM inventario "+ "WHERE codBien = ?");
			sent.setString(1, codBien);
			ResultSet rs = sent.executeQuery();
			if (rs.next()) {
				
				
					
					bien= this.bienDao.find(codBien);
				
			}
		}
		catch(SQLException e){
			throw new DAOException("Error al procesar consulta"+ e.getMessage()+". codigo error I500");
		}
		catch (Exception e) {
			throw new DAOException("Error inesperado: " + e.getMessage()+". codigo error I501");
		} 
		finally {
			ConnectionManager.disconnect();
		}	 
		return bien;
	}

	@Override
	public List<Bien> findAll() throws DAOException {
		List<Bien> bienes = new ArrayList<>();
		
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT codBien  "
					+ "FROM inventario ");
			ResultSet rs = sent.executeQuery();
			while (rs.next()) {
				
				bienes.add(this.findBien(rs.getString("codBien")));
			}
		}
		catch(SQLException e){
			throw new DAOException("Error al procesar consulta"+ e.getMessage()+". codigo error I600");
		}
		catch (Exception e) {
			throw new DAOException("Error inesperado: " + e.getMessage()+". codigo error I601");
		} 
		finally {
			ConnectionManager.disconnect();
		}	 
		return bienes;
	}
	@Override
	public List<Bien> findBienesDisponibles() throws DAOException {
	List<Bien> bienes = new ArrayList<>();
		
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement(  "SELECT codigoBien FROM inventario WHERE disponible = 1");
			ResultSet rs = sent.executeQuery();
			while (rs.next()) {
				
				bienes.add(this.findBien(rs.getString("codigo")));
			}
		}
		catch(SQLException e){
			throw new DAOException("Error al procesar consulta"+ e.getMessage()+". codigo error I600");
		}
		catch (Exception e) {
			throw new DAOException("Error inesperado: " + e.getMessage()+". codigo error I601");
		} 
		finally {
			ConnectionManager.disconnect();
		}	 
		return bienes;
	}
	@Override
	public List<Bien> findBienesNoDisponibles() throws DAOException {
	List<Bien> bienes = new ArrayList<>();
		
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement(  "SELECT codigoBien FROM inventario WHERE disponible = 0");
			ResultSet rs = sent.executeQuery();
			while (rs.next()) {
				
				bienes.add(this.findBien(rs.getString("codigo")));
			}
		}
		catch(SQLException e){
			throw new DAOException("Error al procesar consulta"+ e.getMessage()+". codigo error I600");
		}
		catch (Exception e) {
			throw new DAOException("Error inesperado: " + e.getMessage()+". codigo error I601");
		} 
		finally {
			ConnectionManager.disconnect();
		}	 
		return bienes;
	}
	
	
}
