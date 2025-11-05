package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.modelo.Donante;
import ar.edu.unrn.seminario.modelo.Ubicacion;

public class DonanteDAOJDBC implements DonanteDao{

	public void create(Donante donante) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO donante(codigo, nombre, email, username, codUbicacion, activo)"
							+ " VALUES (?, ?, ?, ?, ?, ?)");
			
			statement.setString(1, donante.getCodigo());
			statement.setString(2, donante.getNombre());
			//statement.setString(3, donante.getApellido());
			statement.setString(3, donante.getPreferenciaContacto());
			statement.setString(4, donante.getUsername());
			statement.setObject(5, donante.getUbicacion() != null ? donante.getUbicacion().getCodigo() : null);
			statement.setBoolean(6, false);
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

	public void update(Donante donante) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE donante SET codigo ?, nombre = ?, email = ?, codUbicacion = ? WHERE username = ?");
			statement.setString(1, donante.getCodigo());
			statement.setString(2, donante.getNombre());
			//statement.setString(2, donante.getApellido());
			statement.setString(3, donante.getPreferenciaContacto());
			statement.setObject(4, donante.getUbicacion().getCodigo());
			statement.setString(5, donante.getUsername());
			
			
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				 System.out.println("El Donante se ha actualizado correctamente");
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

	public void remove(String username) {
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM rol WHERE username = ?"
		        );

		        statement.setString(1, username);

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("Donante eliminado correctamente.");
		        } else {
		            System.out.println("No se encontró al donante.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar donanre");
		}
		
	}

	public void remove(Donante donante) {
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM donante WHERE username = ? AND codigo=?"
		        );

		        statement.setString(1, donante.getUsername());
		        statement.setString(2, donante.getCodigo());

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("donante eliminado correctamente.");
		        } else {
		            System.out.println("No se encontró el donante.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar donante");
		}
	}

	public Donante find(String codigo) {
		Donante donante= null;
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT D.codigo, D.nombre, D.email, D.codUbicacion,D.username "+ "FROM donante D "+ "WHERE D.codigo = ?");
			sent.setString(1, codigo);
			ResultSet rs = sent.executeQuery();
			if (rs.next()) {
				Ubicacion ubicacion = new Ubicacion(rs.getString("codUbicacion"),null,null,null);	 //Null ya que no tenemos tabla Ubicacion 
				donante=new Donante(rs.getString("nombre"),null,rs.getString("email"), ubicacion,rs.getString("username"));
				
				
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
		return donante;
	}

	public List<Donante> findAll() {
		List<Donante> donantes = new ArrayList<>();
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT D.codigo, D.nombre, D.email, D.codUbicacion,D.username "+ "FROM donante D ");
			ResultSet rs = sent.executeQuery();
			while (rs.next()) {
				Ubicacion ubicacion = new Ubicacion(rs.getString("codUbicacion"),null,null,null);	 //Null ya que no tenemos tabla Ubicacion 
				Donante donante=new Donante(rs.getString("nombre"),null,rs.getString("email"), ubicacion,rs.getString("username"));
				
				donantes.add(donante);
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
		return donantes;
	}

	@Override
	public void remove(Long id) {
		// TODO Auto-generated method stub
		
	}

	
}