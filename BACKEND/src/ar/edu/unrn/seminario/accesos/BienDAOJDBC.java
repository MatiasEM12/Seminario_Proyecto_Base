package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DataDoubleException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.Coordenada;

public class BienDAOJDBC  implements BienDAO{

	@Override
	public void create(Bien bien) {
		
		try {
			
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO bien(codigo,tipo,nombre,peso,descripcion,nivelNecesidad,fechaVencimiento,talle,material)"
							+ " VALUES (?, ?, ?,?,?,?,?,?,?)");
			
			java.sql.Date fechaSQL = java.sql.Date.valueOf(bien.getFechaVencimiento());
			statement.setString(1, bien.getCodigo());
			statement.setString(2, bien.getTipo());
			statement.setDouble(3, bien.getPeso());
			statement.setString(4, bien.getDescripcion());
			statement.setInt(5, bien.getNivelNecesidad());
			statement.setDate(6,fechaSQL);
			statement.setDouble(7,bien.getTalle());
			statement.setString(8,bien.getMaterial());
		
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
	public void update(Bien bien) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE bien SET codigo=?,tipo=?,nombre=?,peso=?,descripcion=?,nivelNecesidad=?,fechaVencimiento=?,talle=?,material=? WHERE codigo = ?");
			java.sql.Date fechaSQL = java.sql.Date.valueOf(bien.getFechaVencimiento());
			statement.setString(1, bien.getCodigo());
			statement.setString(2, bien.getTipo());
			statement.setDouble(3, bien.getPeso());
			statement.setString(4, bien.getDescripcion());
			statement.setInt(5, bien.getNivelNecesidad());
			statement.setDate(6,fechaSQL);
			statement.setDouble(7,bien.getTalle());
			statement.setString(8,bien.getMaterial());
			statement.setString(9, bien.getCodigo());
			
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				 System.out.println("El bien se ha actualizado correctamente");
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
		            "DELETE FROM bien WHERE codigo = ? "
		        );

		        statement.setString(1, codigo);

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("bien correctamente.");
		        } else {
		            System.out.println("No se encontró el bien.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar bien");
		}finally {
			ConnectionManager.disconnect();
		}
		
	}

	@Override
	public void remove(Bien bien) {
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
		            System.out.println("No se encontró el bien.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar bien");
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
			+ "FROM bien "+ "WHERE codigo = ?");
			sent.setString(1, codigo);
			ResultSet rs = sent.executeQuery();
			if (rs.next()) {
				
				if() {//String nombre, String descripcion, LocalDate fechaVencimiento,String tipo) throws DataNullException{ //alimento,medicamento 
					
					
					
				}else if() {//double peso, String nombre, String descripcion, String material,String tipo)throws DataNullException,DataDoubleException{ //mueble, electrodomestico 
					
				}else if() {//Bien(String nombre, String descripcion, double talle, String material,String tipo)throws DataNullException,DataDoubleException { //ropa
					
				}else { //Bien(double peso, String nombre, String descripcion, LocalDate fechaVencimiento, double talle,String material, String tipo) throws DataNullException { //otro
					
				}
				
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
		return coordenada;
	}

	@Override
	public List<Bien> findAll() {
List<Coordenada> coordenadas = new ArrayList<>();
		
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT codigo  "
					+ "FROM coordenada ");
			ResultSet rs = sent.executeQuery();
			while (rs.next()) {
				
				coordenadas.add(this.find(rs.getString("codigo")));
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
		return coordenadas;
	}

	}

}
