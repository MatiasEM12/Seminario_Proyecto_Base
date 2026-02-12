package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataDoubleException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.Coordenada;

public class BienDAOJDBC  implements BienDAO{

	@Override
	public void create(Bien bien) throws DAOException{
		
		try {
			
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO bien(codigo,tipo,nombre,peso,descripcion,nivelNecesidad,fechaVencimiento,talle,material)"
							+ " VALUES (?, ?, ?,?,?,?,?,?,?)");
			
			java.sql.Date fechaSQL = java.sql.Date.valueOf(bien.getFechaVencimiento());
			statement.setString(1, bien.getCodigo());
			statement.setString(2, bien.getTipo());
			statement.setString(3, bien.getNombre());
			statement.setDouble(4, bien.getPeso());
			statement.setString(5, bien.getDescripcion());
			statement.setInt(6, bien.getNivelNecesidad());
			statement.setDate(7, fechaSQL);
			statement.setDouble(8, bien.getTalle());
			statement.setString(9, bien.getMaterial());
		
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				// System.out.println("Modificando " + cantidad + " registros");
			} else {
				throw new DAOException("Error al actualizar. codigo error B100");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
			throw new DAOException("Error al procesar consulta (INSERT Bien): " + e.getMessage() + ". codigo error B101");
	    } finally {
	        ConnectionManager.disconnect();
	    }
		
	}

	@Override
	public void update(Bien bien) throws DAOException{
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE bien SET codigo=?,tipo=?,nombre=?,peso=?,descripcion=?,nivelNecesidad=?,fechaVencimiento=?,talle=?,material=? WHERE codigo = ?");
			java.sql.Date fechaSQL = java.sql.Date.valueOf(bien.getFechaVencimiento());
			statement.setString(1, bien.getCodigo());
			statement.setString(2, bien.getTipo());
			statement.setString(3, bien.getNombre());
			statement.setDouble(4, bien.getPeso());
			statement.setString(5, bien.getDescripcion());
			statement.setInt(6, bien.getNivelNecesidad());
			statement.setDate(7, fechaSQL);
			statement.setDouble(8, bien.getTalle());
			statement.setString(9, bien.getMaterial());
			statement.setString(10, bien.getCodigo());

			
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				 System.out.println("El bien se ha actualizado correctamente");
			} else {
				throw new DAOException("Error al actualizar. codigo error B200");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
			throw new DAOException("Error al procesar consulta. codigo error B201");
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
	public void remove(String codigo) throws DAOException{
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM bien WHERE codigo = ? "
		        );

		        statement.setString(1, codigo);

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("bien eliminado correctamente.");
		        } else {
		            System.out.println("No se encontró el bien. codigo error B300");
		        }
			
		}catch(SQLException e) {
			throw new DAOException("Error al Eliminar bien. codigo error B301");
		}finally {
			ConnectionManager.disconnect();
		}
		
	}

	@Override
	public void remove(Bien bien) throws DAOException{

		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM bien WHERE codigo = ? "
		        );

		        statement.setString(1, bien.getCodigo());

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("bien eliminado correctamente.");
		        } else {
		            System.out.println("No se encontró el bien. codigo error B400");
		        }
			
		}catch(SQLException e) {
			throw new DAOException("Error al Eliminar bien. codigo error B401");
		}finally {
			ConnectionManager.disconnect();
		}
		
		
	}

	@Override
	public Bien find(String codigo) throws DataNullException, DAOException{
		Bien bien= null;
		if (codigo == null || codigo.isBlank()) {
		    throw new DataNullException("Matrícula inválida. B502");
		}
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT codigo,tipo,nombre,peso,descripcion,nivelNecesidad,fechaVencimiento,talle,material "
			+ "FROM bien "+ "WHERE codigo = ?");
			sent.setString(1, codigo);
			ResultSet rs = sent.executeQuery();
			if (rs.next()) {
				
				
					
					 java.sql.Date sqlDate = rs.getDate("fechaVencimiento");
					 java.time.LocalDate fecha = sqlDate.toLocalDate();
					bien=new Bien(rs.getString("codigo"),rs.getString("tipo"),rs.getDouble("peso"),rs.getString("nombre"),
							rs.getString("descripcion"),rs.getInt("nivelNecesidad"),fecha,rs.getDouble("talle"),rs.getString("material"));
			
				
			}
		}
		catch(SQLException e){
			throw new DAOException("Error al procesar consulta"+ e.getMessage()+". codigo error B500");
		}
		catch (Exception e) {
			throw new DAOException("Error inesperado: " + e.getMessage()+". codigo error B501");
		} 
		finally {
			ConnectionManager.disconnect();
		}	 
		return bien;
	}

	@Override
	public List<Bien> findAll() throws DAOException{
List<Bien> bienes = new ArrayList<>();
		
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT codigo  "
					+ "FROM bien ");
			ResultSet rs = sent.executeQuery();
			while (rs.next()) {
				
				bienes.add(this.find(rs.getString("codigo")));
			}
		}
		catch(SQLException e){
			throw new DAOException("Error al procesar consulta"+ e.getMessage()+". codigo error B600");
		}
		catch (Exception e) {
			throw new DAOException("Error inesperado: " + e.getMessage()+". codigo error B601");
		} 
		finally {
			ConnectionManager.disconnect();
		}	 
		return bienes;
	}
	
	@Override
	public ArrayList<Bien> findBienVisita(String codVisita) throws DataNullException, DAOException{
		ArrayList<Bien> bienes = new ArrayList<>();
		if (codVisita == null || codVisita.isBlank()) {
			throw new DataNullException("Matrícula inválida. B702");
		}
		
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT b.codigo\r\n"
					+ "FROM bien b\r\n"
					+ "JOIN Bien_Visita bv ON b.codigo = bv.codBien\r\n"
					+ "WHERE bv.codVisita = ?\r\n"
					+ ""
					+ "");
		
			sent.setString(1, codVisita);
			ResultSet rs = sent.executeQuery();
			
			while (rs.next()) {
				
				bienes.add(this.find(rs.getString("b.codigo")));
			}
		}
		catch(SQLException e){
			throw new DAOException("Error al procesar consulta"+ e.getMessage()+". codigo error B700");
		}
		catch (Exception e) {
			throw new DAOException("Error inesperado: " + e.getMessage()+". codigo error B701");
		} 
		finally {
			ConnectionManager.disconnect();
		}	 
		return bienes;
	}
	
	@Override
	public ArrayList<Bien> findBienDonacion(String codDonacion) throws DataNullException,DAOException{
		ArrayList<Bien> bienes = new ArrayList<>();
		if (codDonacion == null || codDonacion.isBlank()) {
			throw new DataNullException("Matrícula inválida. B802");
		}
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("FROM bien b\r\n"
					+ "JOIN Bien_Donacion bd ON b.codigo = bd.codBien\r\n"
					+ "WHERE bd.codDonacion = ?\r\n"
					+ "");
		
			sent.setString(1, codDonacion);
			ResultSet rs = sent.executeQuery();
			
			while (rs.next()) {
				
				bienes.add(this.find(rs.getString("b.codigo")));
			}
		}
		catch(SQLException e){
			throw new DAOException("Error al procesar consulta"+ e.getMessage()+". codigo error B801");
		}
		catch (Exception e) {
			throw new DAOException("Error inesperado: " + e.getMessage()+". codigo error B801");
		} 
		finally {
			ConnectionManager.disconnect();
		}	 
		return bienes;

	}
	
	
	
	// para iventario
	
	
	public List<Bien> findALLTipo(String tipo) throws DataNullException,DAOException{
	    List<Bien> bienes = new ArrayList<>();
	    if (tipo == null || tipo.isBlank()) {
			throw new DataNullException("Matrícula inválida. B902");
		}
	    try {
	        //rebisa si la busqueda no fue por bienes vencidos
	        if (tipo.equals("Bienes vencidos")) {
	            Connection conn = ConnectionManager.getConnection();
	            PreparedStatement sent = conn.prepareStatement(
	                "SELECT codigo, tipo, nombre, peso, descripcion, nivelNecesidad, fechaVencimiento, talle, material " +
	                "FROM bien WHERE fechaVencimiento < ?");  //comparara la fecha con la actual
	            sent.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
	            ResultSet rs = sent.executeQuery();
	            while (rs.next()) {
	                java.sql.Date sqlDate = rs.getDate("fechaVencimiento");
	                java.time.LocalDate fecha = sqlDate.toLocalDate();
	                Bien bien = new Bien(
	                    rs.getString("codigo"),
	                    rs.getString("tipo"),
	                    rs.getDouble("peso"),
	                    rs.getString("nombre"),
	                    rs.getString("descripcion"),
	                    rs.getInt("nivelNecesidad"),
	                    fecha,
	                    rs.getDouble("talle"),
	                    rs.getString("material")
	                );
	                bienes.add(bien);
	            }
	        }else{ // recupera todos los que sean iguales al tipo.
	            Connection conn = ConnectionManager.getConnection();
	            PreparedStatement sent = conn.prepareStatement(
	                "SELECT codigo, tipo, nombre, peso, descripcion, nivelNecesidad, fechaVencimiento, talle, material " +
	                "FROM bien WHERE tipo = ?");
	            sent.setString(1, tipo);
	            ResultSet rs = sent.executeQuery();
	            
	            while (rs.next()) {
	                java.sql.Date sqlDate = rs.getDate("fechaVencimiento");
	                java.time.LocalDate fecha = sqlDate.toLocalDate();
	                Bien bien = new Bien(
	                    rs.getString("codigo"),
	                    rs.getString("tipo"),
	                    rs.getDouble("peso"),
	                    rs.getString("nombre"),
	                    rs.getString("descripcion"),
	                    rs.getInt("nivelNecesidad"),
	                    fecha,
	                    rs.getDouble("talle"),
	                    rs.getString("material")
	                );
	                bienes.add(bien);
	            }
	        }
	    } catch (SQLException e) {
	    	throw new DAOException ("Error al procesar consulta: " + e.getMessage()+". codigo error B900");
	    } catch (Exception e) {
	    	throw new DAOException("Error inesperado: " + e.getMessage()+". codigo error B901");
	    } finally {
	        ConnectionManager.disconnect();
	    }

	    return bienes;
	}
	
	
	public int obtenerCantidadBienes() throws SQLException {
	    String sql = "SELECT COUNT(*) FROM bien";

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
	
	public int obtenerMaximoBienes() throws SQLException {
	    String sql = "SELECT MAX(codigo) FROM bien";

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
