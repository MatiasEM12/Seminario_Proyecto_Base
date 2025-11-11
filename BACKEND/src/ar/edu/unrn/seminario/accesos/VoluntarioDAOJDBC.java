package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.modelo.Coordenada;
import ar.edu.unrn.seminario.modelo.Donante;
import ar.edu.unrn.seminario.modelo.Voluntario;

public class VoluntarioDAOJDBC implements VoluntarioDAO{

    //codigo,nombre,apellido,dni,contacto,Fecha_Nacimiento,username,

	@Override
	public void create(Voluntario voluntario) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO donante(codigo, nombre,apellido, dni,contacto,Fecha_Nacimiento, username)"
							+ " VALUES (?, ?, ?, ?, ?, ?,?)");
			
			java.sql.Date fechaSQL = java.sql.Date.valueOf(voluntario.getFecha_nac());
			
			statement.setDate(1, fechaSQL);
			
			statement.setString(1, voluntario.getCodigo());
			statement.setString(2, voluntario.getNombre());
			statement.setString(3, voluntario.getApellido());
			statement.setString(4, voluntario.getDni());
			statement.setString(5, voluntario.getContacto());
			statement.setDate(6,fechaSQL);
			statement.setString(7, voluntario.getUsername());

		
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
	public void update(Voluntario voluntario) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE donante SET codigo ?, nombre = ?,apellido =?,dni= ?, contacto= ?,Fecha_Nacimiento=? , ,username=? "
							+ "WHERE codigo = ?");
			java.sql.Date fechaSQL = java.sql.Date.valueOf(voluntario.getFecha_nac());
			
			statement.setDate(1, fechaSQL);
			
			statement.setString(1, voluntario.getCodigo());
			statement.setString(2, voluntario.getNombre());
			statement.setString(3, voluntario.getApellido());
			statement.setString(4, voluntario.getDni());
			statement.setString(5, voluntario.getContacto());
			statement.setDate(6,fechaSQL);
			statement.setString(7, voluntario.getUsername());
			statement.setString(8, voluntario.getCodigo());
			
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

	@Override
	public void remove(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String codigo) {
		try {
			 
			Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM voluntario WHERE codigo = ?"
		        );

		        statement.setString(1, codigo);
		        
		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("Voluntario eliminado correctamente.");
		        } else {
		            System.out.println("No se encontró al voluntario.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar Voluntario");
		}
		
		
	}

	@Override
	public void remove(Voluntario voluntario) {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(
	            "DELETE FROM voluntario WHERE codigo = ?"
	        );

	        statement.setString(1, voluntario.getCodigo());
	        
	        int cantidad = statement.executeUpdate();
	        if (cantidad > 0) {
	            System.out.println("Voluntario eliminado correctamente.");
	        } else {
	            System.out.println("No se encontró al voluntario.");
	        }
		
	}catch(SQLException e) {
		System.out.println("Error al Eliminar Voluntario");
	}
	
		
	}

	@Override
	public Voluntario find(String codigo) {
		Voluntario voluntario= null;
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT codigo, nombre,apellido, dni,contacto,Fecha_Nacimiento, username "
			+ "FROM voluntario"+ "WHERE codigo = ?");
			sent.setString(1, codigo);
			ResultSet rs = sent.executeQuery();
			if (rs.next()) {
				Date sqlDate = rs.getDate("D.Fecha_Nacimiento");
				LocalDate fecha = sqlDate.toLocalDate(); 
				voluntario=new Voluntario(rs.getString("nombre"),rs.getString("apellido"),  fecha  ,rs.getString("contacto"), rs.getString("dni"),rs.getString("username"),rs.getString("codigo"));
				
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
		return voluntario;
	}

	@Override
	public List<Voluntario> findAll() {
		List<Voluntario> voluntarios = new ArrayList<>();
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT codigo"+ "FROM voluntario ");
			ResultSet rs = sent.executeQuery();
			while (rs.next()) {
				
				
				voluntarios.add(this.find(rs.getString("codigo")));
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
		return voluntarios;
	
	}

}
