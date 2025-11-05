package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;

public class UsuarioDAOJDBC implements UsuarioDao {

	@Override
	public void create(Usuario usuario) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO usuarios(usuario, contrasena, nombre, email, activo,rol) "
							+ "VALUES (?, ?, ?, ?, ?, ?)");
	
			statement.setString(1, usuario.getUsuario());
			statement.setString(2, usuario.getContrasena());
			statement.setString(3, usuario.getNombre());
			statement.setString(4, usuario.getEmail());
			statement.setBoolean(5, usuario.isActivo());
			statement.setInt(6, usuario.getRol().getCodigo());
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				System.out.println("Modificando " + cantidad + " registros");
			} else {
				System.out.println("Error al actualizar");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
			System.out.println("Error al procesar consulta");
			// TODO: disparar Exception propia
		} catch (Exception e) {
			System.out.println("Error al insertar un usuario");
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}

	}

	@Override
	public void update(Usuario usuario) {
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM usuario WHERE username = ? "
		        );

		        statement.setString(1, usuario.getNombre());
		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("Usuario eliminado correctamente.");
		        } else {
		            System.out.println("No se encontró el Usuario.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar Usuario");
		}
	}
	

	@Override
	public void remove(Long id) {//   (String codigo)
		/*try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM donante WHERE codigo=?"
		        );

	
		        statement.setString(1, id);

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("Usuario eliminado correctamente.");
		        } else {
		            System.out.println("No se encontró el Usuario.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar usuario");
		}
*/
	}

	@Override
	public void remove(Usuario usuario) {
		
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement("DELETE FROM usuarios WHERE usuario = ?");
			        
		        //conseguimos el codigo del usuario
		        statement.setString(1,usuario.getUsuario());
			    int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("se eliminaron correctamente. un total de "+cantidad+ " usuarios");
		        } else {
		            System.out.println("No se encontró usuarios con ese tipo de rol.");
		        }
	
		}catch(SQLException e) {
			System.out.println("Error al Eliminar usuario");
		}
	}

	@Override
	public Usuario find(String username) {
		Usuario usuario = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(
					"SELECT u.usuario,  u.contrasena, u.nombre, u.email,u.activo, r.codigo as codigo_rol, r.nombre as nombre_rol "
							+ " FROM usuarios u JOIN roles r ON (u.rol = r.codigo) " + " WHERE u.usuario = ?");

			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				Rol rol = new Rol(rs.getInt("codigo_rol"), rs.getString("nombre_rol"));
				boolean activo;
				if(rs.getInt("activo")==1){
					activo=true;
				}else {
					activo=false;
				}
				
				usuario = new Usuario(rs.getString("usuario"), rs.getString("contrasena"), rs.getString("nombre"),
						rs.getString("email"), rol,activo);
			}

		} catch (SQLException e) {
			System.out.println("Error al procesar consulta");
			// TODO: disparar Exception propia
			// throw new AppException(e, e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			// TODO: disparar Exception propia
			// throw new AppException(e, e.getCause().getMessage(), e.getMessage());
		} finally {
			ConnectionManager.disconnect();
		}

		return usuario;
	}

	@Override
	public List<Usuario> findAll() {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			Connection conn = ConnectionManager.getConnection();
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT u.usuario,  u.contrasena, u.nombre, u.email,u.activo, r.codigo as codigo_rol, r.nombre as nombre_rol  "
							+ "FROM usuarios u JOIN roles r ON (u.rol = r.codigo) ");

			while (rs.next()) {

				Rol rol = new Rol(rs.getInt("codigo_rol"), rs.getString("nombre_rol"));
				Usuario usuario = null;
				boolean activo;
				if(rs.getInt("activo")==1){
					activo=true;
				}else {
					activo=false;
				}
				
			
				try {
					usuario = new Usuario(rs.getString("usuario"), rs.getString("contrasena"),
							rs.getString("nombre"), rs.getString("email"), rol,activo);
				} catch (DataEmptyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			System.out.println("Error de mySql\n" + e.toString());
			// TODO: disparar Exception propia
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}

		return usuarios;
	}

}
