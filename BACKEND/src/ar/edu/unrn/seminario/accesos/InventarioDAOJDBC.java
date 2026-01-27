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

	
public void create(Bien bien) throws DAOException {
		
		try {
			
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					// tienen otro atributo llamado entregado para saber si el bien fue entregado. 0 = no entregao, 1 = entregado, esta en default como 0.
					.prepareStatement("INSERT INTO inventario(codigo,tipo,nombre,peso,descripcion,nivelNecesidad,fechaVencimiento,talle,material)"
							+ " VALUES (?, ?, ?,?,?,?,?,?,?,?)");
			
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
				throw new DAOException("Error al actualizar. codigo error I100");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
			throw new DAOException("Error al procesar consulta (INSERT Bien): " + e.getMessage() + ". codigo error I101");
	    } finally {
	        ConnectionManager.disconnect();
	    }
		
	}

	@Override
	public void update(Bien bien) throws DAOException {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE inventario SET tipo=?,nombre=?,peso=?,descripcion=?,nivelNecesidad=?,fechaVencimiento=?,talle=?,material=? WHERE codigo = ?");
			java.sql.Date fechaSQL = java.sql.Date.valueOf(bien.getFechaVencimiento());
			statement.setString(1, bien.getTipo());
			statement.setString(2, bien.getNombre());
			statement.setDouble(3, bien.getPeso());
			statement.setString(4, bien.getDescripcion());
			statement.setInt(5, bien.getNivelNecesidad());
			statement.setDate(6, fechaSQL);
			statement.setDouble(7, bien.getTalle());
			statement.setString(8, bien.getMaterial());
			statement.setString(9, bien.getCodigo()); 
			
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				 System.out.println("El inventario se ha actualizado correctamente");
			} else {
				throw new DAOException("Error al actualizar. codigo error I200");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
			throw new DAOException("Error al procesar consulta."+ e.getMessage() +" codigo error I201");
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}
		
	}
	public void cambiarEntrega(String codigo) throws DAOException {
		try {

			Connection conn = ConnectionManager.getConnection();
			//el NOT estregado ase que de 0->1,1->0 del bien que coincida el codigo.
			PreparedStatement statement = conn
					.prepareStatement("UPDATE inventario SET entregado=NOT entregado WHERE codigo = ?");
			
			statement.setString(1, codigo);
			
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				 System.out.println("El estado se ha actualizado correctamente");
			} else {
				throw new DAOException("Error no se encontro el bien. codigo error i250");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
			throw new DAOException("Error al procesar consulta."+ e.getMessage() +" codigo error I251");
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
	public void remove(String codigo) throws DAOException {
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM inventario WHERE codigo = ? "
		        );

		        statement.setString(1, codigo);

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
	public void remove(Bien bien) throws DAOException {
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM inventario WHERE codigo = ? "
		        );

		        statement.setString(1, bien.getCodigo());

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("bien eliminado correctamente.");
		        } else {
		        	throw new DAOException("No se encontró el bien en el inventario. codigo error I400");
		        }
			
		}catch(SQLException e) {
			throw new DAOException("Error al Eliminar bien del inventario."+ e.getMessage() +" codigo error I401");
		}finally {
			ConnectionManager.disconnect();
		}
		
		
	}

	@Override
	public Bien find(String codigo) throws DAOException {
		Bien bien= null;
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT codigo,tipo,nombre,peso,descripcion,nivelNecesidad,fechaVencimiento,talle,material "
			+ "FROM inventario "+ "WHERE codigo = ?");
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
			PreparedStatement sent = conn.prepareStatement("SELECT codigo  "
					+ "FROM inventario ");
			ResultSet rs = sent.executeQuery();
			while (rs.next()) {
				
				bienes.add(this.find(rs.getString("codigo")));
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
	public ArrayList<Bien> findBienVisita(String codVisita) throws DAOException {
ArrayList<Bien> bienes = new ArrayList<>();
		
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT b.codigo  "
					+ "FROM inventario b , bien_visita bv WHERE b.codigo = bv.codBien AND bv.codVisita = ?");
		
			sent.setString(1, codVisita);
			ResultSet rs = sent.executeQuery();
			
			while (rs.next()) {
				
				bienes.add(this.find(rs.getString("codigo")));
			}
		}
		catch(SQLException e){
			throw new DAOException("Error al procesar consulta"+ e.getMessage()+". codigo error I700");
		}
		catch (Exception e) {
			throw new DAOException("Error inesperado: " + e.getMessage()+". codigo error I701");
		} 
		finally {
			ConnectionManager.disconnect();
		}	 
		return bienes;
	}
	
	@Override
	public ArrayList<Bien> findBienDonacion(String codDonacion) throws DAOException {
		ArrayList<Bien> bienes = new ArrayList<>();
		
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT b.codigo  "
					+ "FROM inventario b , bien_donacion bd WHERE b.codigo = bd.codBien AND bd.codDonacion = ?");
		
			sent.setString(1, codDonacion);
			ResultSet rs = sent.executeQuery();
			
			while (rs.next()) {
				
				bienes.add(this.find(rs.getString("codigo")));
			}
		}
		catch(SQLException e){
			throw new DAOException("Error al procesar consulta"+ e.getMessage()+". codigo error I801");
		}
		catch (Exception e) {
			throw new DAOException("Error inesperado: " + e.getMessage()+". codigo error I801");
		} 
		finally {
			ConnectionManager.disconnect();
		}	 
		return bienes;

	}
	
	
	
	// para iventario
	
	
	public List<Bien> findALLTipo(String tipo) throws DAOException {
	    List<Bien> bienes = new ArrayList<>();
	    try {
	        //rebisa si la busqueda no fue por bienes vencidos
	    	//se realisa el filtro de busqueda del bien
	        //rebisa si la busqueda fue por bienes vencidos
	    	PreparedStatement sent;
	    	Connection conn = ConnectionManager.getConnection();
	        if (tipo.equals("Bienes vencidos")) {
	            sent = conn.prepareStatement(
	                "SELECT codigo, tipo, nombre, peso, descripcion, nivelNecesidad, fechaVencimiento, talle, material " +
	                "FROM inventario WHERE fechaVencimiento < ?");  //comparara la fecha con la actual
	            sent.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
	            
	        }else{ 
	        	// recupera todos los bienes entregados
	        	if (tipo.equals("Entregados")) {
	        		sent = conn.prepareStatement(
	                    "SELECT codigo, tipo, nombre, peso, descripcion, nivelNecesidad, fechaVencimiento, talle, material " +
	                    "FROM inventario WHERE entregado = 1");
	        	}
	        	
	        	else {
	        		// recupera los bienes por tipo.
		            sent = conn.prepareStatement(
		                "SELECT codigo, tipo, nombre, peso, descripcion, nivelNecesidad, fechaVencimiento, talle, material " +
		                "FROM inventario WHERE tipo = ?");
		            sent.setString(1, tipo);
	        	}
	        }
	        // recore el archivo y almacena los bienes en la lista. 
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
	    } catch (SQLException e) {
	    	throw new DAOException("Error al procesar consulta: " + e.getMessage()+". codigo error I900");
	    } catch (Exception e) {
	    	throw new DAOException("Error inesperado: " + e.getMessage()+". codigo error I901");
	    } finally {
	        ConnectionManager.disconnect();
	    }

	    return bienes;
	}
}
