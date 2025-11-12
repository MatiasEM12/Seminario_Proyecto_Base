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
import ar.edu.unrn.seminario.modelo.Ubicacion;

public class DonanteDAOJDBC implements DonanteDao{
	UbicacionDAO u;
	public void create(Donante donante) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO donante(codigo, nombre,apellido, dni,contacto,Fecha_Nacimiento, username, codUbicacion)"
							+ " VALUES (?, ?, ?, ?, ?, ?,?,?)");
			
			java.sql.Date fechaSQL = java.sql.Date.valueOf(donante.getFecha_nac());
			
			
			
			statement.setString(1, donante.getCodigo());
			statement.setString(2, donante.getNombre());
			statement.setString(3, donante.getApellido());
			statement.setString(4, donante.getDni());
			statement.setString(5, donante.getContacto());
			statement.setDate(6,fechaSQL);
			statement.setString(7, donante.getUsername());
			statement.setObject(8, donante.getUbicacion().getCodigo());
		
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
					.prepareStatement("UPDATE donante SET codigo ?, nombre = ?,apellido =?,dni= ?, contacto= ?,Fecha_Nacimiento=? , ,username=? codUbicacion = ? "
							+ "WHERE codigo = ?");
			java.sql.Date fechaSQL = java.sql.Date.valueOf(donante.getFecha_nac());
			
			statement.setDate(1, fechaSQL);
			
			statement.setString(1, donante.getCodigo());
			statement.setString(2, donante.getNombre());
			statement.setString(3, donante.getApellido());
			statement.setString(4, donante.getDni());
			statement.setString(5, donante.getContacto());
			statement.setDate(6,fechaSQL);
			statement.setString(7, donante.getUsername());
			statement.setObject(8, donante.getUbicacion().getCodigo());
			statement.setString(9, donante.getCodigo());
			
			
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

	public void remove(String codigo) {
		try {
			 Connection conn = ConnectionManager.getConnection();
			  PreparedStatement statement1 = conn.prepareStatement(
			            "SELECT codUbicacion FROM donante WHERE codigo = ?"
			        );
			 
			  statement1.setString(1, codigo);
			  ResultSet rs = statement1.executeQuery();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM donante WHERE codigo = ?"
		        );

		        statement.setString(1, codigo);
		        
		        u.remove(rs.getString("codUbicacion"));
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
			  PreparedStatement statement1 = conn.prepareStatement(
			            "SELECT codUbicacion FROM donante WHERE username = ?"
			        );
			  statement1.setString(1, donante.getUsername());
			  ResultSet rs = statement1.executeQuery();
			  
			 Connection conn2 = ConnectionManager.getConnection();
		        PreparedStatement statement = conn2.prepareStatement(
		            "DELETE FROM donante WHERE codigo = ? "
		        );

		        ;
		        statement.setString( 1,donante.getCodigo());
		        u.remove(rs.getString("codUbicacion"));
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
			PreparedStatement sent = conn.prepareStatement("SELECT D.codigo, D.nombre, D.apellido,D.dni,D.Fecha_Nacimiento,D.username D.contacto, D.ubicacion,"+
			"U.codigo, U.zona, U.barrio, U.direccion, C.codigo,C.Latitud,C.Longitud"
			+ "FROM Donante D "+"JOIN Ubicacion U ON D.ubicacion = U.codigo"+ "WHERE D.codigo = ? AND U.codCoordenada=C.codigo");
			sent.setString(1, codigo);
			ResultSet rs = sent.executeQuery();
		
			if (rs.next()) {
				Coordenada coordenada= new Coordenada(rs.getDouble("Latitud"),rs.getDouble("Longitud"),rs.getString("codigo"));
				Ubicacion ubicacion = new Ubicacion(rs.getString("U.codigo"),rs.getString("U.zona"),rs.getString("U.barrio"),rs.getString("U.direccion"),coordenada);
				
			Date sqlDate = rs.getDate("D.Fecha_Nacimiento");
			LocalDate fecha = sqlDate.toLocalDate();
				donante=new Donante(rs.getString("nombre"),rs.getString("apellido"),  fecha  , rs.getString("D.dni"),rs.getString("D.contacto"),ubicacion,rs.getString("D.username"),rs.getString("D.codigo"));
				donante.setUbicacion(ubicacion);
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
			PreparedStatement sent = conn.prepareStatement("SELECT codigo"+ "FROM donante ");
			ResultSet rs = sent.executeQuery();
			while (rs.next()) {
				
				
				donantes.add(this.find(rs.getString("codigo")));
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