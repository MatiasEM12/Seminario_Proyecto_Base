package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.modelo.Bien;

public class InventarioDAOJDBC implements InventarioDAO{

	
public void create(Bien bien) {
		
		try {
			
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO inventario(codigo,tipo,nombre,peso,descripcion,nivelNecesidad,fechaVencimiento,talle,material)"
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
				System.out.println("Error al actualizar. codigo error I100");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
	        System.out.println("Error al procesar consulta (INSERT Bien): " + e.getMessage() + ". codigo error I101");
	    } finally {
	        ConnectionManager.disconnect();
	    }
		
	}

	@Override
	public void update(Bien bien) {
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
				System.out.println("Error al actualizar. codigo error I200");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
			System.out.println("Error al procesar consulta. codigo error I201");
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
		            "DELETE FROM inventario WHERE codigo = ? "
		        );

		        statement.setString(1, codigo);

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("inventario eliminado correctamente el bien.");
		        } else {
		            System.out.println("No se encontró el bien en el inventario. codigo error I300");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar bien del inventario. codigo error I301");
		}finally {
			ConnectionManager.disconnect();
		}
		
	}

	@Override
	public void remove(Bien bien) {
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
		            System.out.println("No se encontró el bien en el inventario. codigo error I400");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar bien del inventario. codigo error I401");
		}finally {
			ConnectionManager.disconnect();
		}
		
		
	}

	@Override
	public Bien find(String codigo) {
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
			System.out.println("Error al procesar consulta"+ e.getMessage()+". codigo error I500");
		}
		catch (Exception e) {
			System.out.println("Error inesperado: " + e.getMessage()+". codigo error I501");
		} 
		finally {
			ConnectionManager.disconnect();
		}	 
		return bien;
	}

	@Override
	public List<Bien> findAll() {
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
			System.out.println("Error al procesar consulta"+ e.getMessage()+". codigo error I600");
		}
		catch (Exception e) {
			System.out.println("Error inesperado: " + e.getMessage()+". codigo error I601");
		} 
		finally {
			ConnectionManager.disconnect();
		}	 
		return bienes;
	}
	@Override
	public ArrayList<Bien> findBienVisita(String codVisita) {
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
			System.out.println("Error al procesar consulta"+ e.getMessage()+". codigo error I700");
		}
		catch (Exception e) {
			System.out.println("Error inesperado: " + e.getMessage()+". codigo error I701");
		} 
		finally {
			ConnectionManager.disconnect();
		}	 
		return bienes;
	}
	
	@Override
	public ArrayList<Bien> findBienDonacion(String codDonacion) {
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
			System.out.println("Error al procesar consulta"+ e.getMessage()+". codigo error I801");
		}
		catch (Exception e) {
			System.out.println("Error inesperado: " + e.getMessage()+". codigo error I801");
		} 
		finally {
			ConnectionManager.disconnect();
		}	 
		return bienes;

	}
	
	
	
	// para iventario
	
	
	public List<Bien> findALLTipo(String tipo) {
	    List<Bien> bienes = new ArrayList<>();
	    try {
	        //rebisa si la busqueda no fue por bienes vencidos
	        if (tipo.equals("Bienes vencidos")) {
	            Connection conn = ConnectionManager.getConnection();
	            PreparedStatement sent = conn.prepareStatement(
	                "SELECT codigo, tipo, nombre, peso, descripcion, nivelNecesidad, fechaVencimiento, talle, material " +
	                "FROM inventario WHERE fechaVencimiento < ?");  //comparara la fecha con la actual
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
	                "FROM inventario WHERE tipo = ?");
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
	        System.out.println("Error al procesar consulta: " + e.getMessage()+". codigo error I900");
	    } catch (Exception e) {
	        System.out.println("Error inesperado: " + e.getMessage()+". codigo error I901");
	    } finally {
	        ConnectionManager.disconnect();
	    }

	    return bienes;
	}
}
