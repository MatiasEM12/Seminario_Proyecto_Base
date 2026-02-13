package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.modelo.Coordenada;
import ar.edu.unrn.seminario.modelo.Donante;
import ar.edu.unrn.seminario.modelo.Voluntario;

public class VoluntarioDAOJDBC implements VoluntarioDAO{

    
	@Override
	public void create(Voluntario voluntario) throws DAOException{
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO voluntario(codigo, nombre,apellido, dni,contacto,Fecha_Nacimiento, username,activo)"
							+ " VALUES (?, ?, ?, ?, ?, ?,?,?)");
			
		
			java.sql.Date fechaSQL = java.sql.Date.valueOf(voluntario.getFecha_nac());

			statement.setString(1, voluntario.getCodigo());
			statement.setString(2, voluntario.getNombre());
			statement.setString(3, voluntario.getApellido());
			statement.setString(4, voluntario.getDni());
			statement.setString(5, voluntario.getContacto());
			statement.setDate(6, fechaSQL);
			statement.setString(7, voluntario.getUsername());
			statement.setBoolean(8, true); // activo
		
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				// System.out.println("Modificando " + cantidad + " registros");
			} else {
				throw new DAOException("Error al actualizar.codigo V100");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
			throw new DAOException("Error al procesar consulta.codigo V101" + e.getMessage());
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}
		
	}

	@Override
	public void update(Voluntario voluntario) throws DAOException{
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE voluntario SET codigo ?, nombre = ?,apellido =?,dni= ?, contacto= ?,Fecha_Nacimiento=? , ,username=? "
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
				throw new DAOException("Error al actualizarcodigo V200");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
			throw new DAOException("Error al procesar consulta.codigo V2001");
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
		            "DELETE FROM voluntario WHERE codigo = ?"
		        );

		        statement.setString(1, codigo);
		        
		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("Voluntario eliminado correctamente.");
		        } else {
		        	throw new DAOException("No se encontró al voluntario.");
		        }
			
		}catch(SQLException e) {
			throw new DAOException("Error al Eliminar Voluntario.codigo V300");
		}finally {
	        ConnectionManager.disconnect();
	    }

		
		
	}

	@Override
	public void remove(Voluntario voluntario) throws DAOException{
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
	        	throw new DAOException("No se encontró al voluntario.");
	        }
		
	}catch(SQLException e) {
		throw new DAOException("Error al Eliminar Voluntario.codigo V400");
	}finally {
        ConnectionManager.disconnect();
    }

	
		
	}

	@Override
	public Voluntario find(String codigo) throws DAOException{
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
			throw new DAOException("Error al procesar consulta"+ e.getMessage()+".codigo V500");
		}
		catch (Exception e) {
			throw new DAOException("Error inesperado: " + e.getMessage()+".codigo V501");
		} 
		finally {
			ConnectionManager.disconnect();
		}	 
		return voluntario;
	}

	@Override
	public List<Voluntario> findAll() throws DAOException{
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
			throw new DAOException("Error al procesar consulta"+ e.getMessage()+".codigo V600");
		}
		catch (Exception e) {
			throw new DAOException("Error inesperado: " + e.getMessage()+".codigo V601");
		} 
		finally {
			ConnectionManager.disconnect();
		}	 
		return voluntarios;
	
	}
	
	public int obtenerCantidadVoluntarios() throws SQLException {
	    String sql = "SELECT COUNT(*) FROM voluntario";

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
	
	public int obtenerMaximoVoluntarios() throws SQLException {
	    String sql = "SELECT MAX(codigo) FROM voluntario";

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
