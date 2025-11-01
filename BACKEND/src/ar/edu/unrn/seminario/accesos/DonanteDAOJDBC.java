package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import ar.edu.unrn.seminario.modelo.Donante;

public class DonanteDAOJDBC implements DonanteDao{

	public void create(Donante donante) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO Donante(nombre, apellido, preferenciaContacto, ubicacion, username) "
							+ "VALUES (?, ?, ?, ?,?)");

			statement.setString(1, donante.getNombre());
			statement.setString(2, donante.getApellido());
			statement.setString(3, donante.getPreferenciaContacto());
			statement.setObject(4, donante.getUbicacion());
			statement.setString(5, donante.getUsername());
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
					.prepareStatement("UPDATE rol SET nombre = ?, apellido = ?, preferenciaContacto = ?, ubicacion = ? WHERE username = ?");

			statement.setString(1, donante.getNombre());
			statement.setString(2, donante.getApellido());
			statement.setString(3, donante.getPreferenciaContacto());
			statement.setObject(4, donante.getUbicacion());
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
		            System.out.println("Rol eliminado correctamente.");
		        } else {
		            System.out.println("No se encontró el rol con ese código.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar rol");
		}
		
	}

	public void remove(Donante donante) {
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM rol WHERE username = ?"
		        );

		        statement.setString(1, donante.getUsername());

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("Rol eliminado correctamente.");
		        } else {
		            System.out.println("No se encontró el rol con ese código.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar rol");
		}
	}

	public Donante find(Integer codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Donante> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
