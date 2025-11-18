package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Usuario;

public class UsuarioDAOJDBC implements UsuarioDao {

	@Override
	public void create(Usuario usuario) {

		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("INSERT INTO usuarios(codigo,usuario, contrasena, nombre, contacto, activo,codigoRol) "+ "VALUES (?, ?, ?, ?, ?, ?,?)");
			statement.setString(1, usuario.getCodigo());
			statement.setString(2, usuario.getUsuario());
			statement.setString(3, usuario.getContrasena());
			statement.setString(4, usuario.getNombre());
			statement.setString(5, usuario.getContacto());
			statement.setBoolean(6, usuario.isActivo());
			statement.setInt(7, usuario.getRol().getCodigo());
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				System.out.println("Modificando " + cantidad + " registros");
			} else {
				System.out.println("Error al actualizar");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
			System.out.println("Error al procesar consulta");
			e.printStackTrace();
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
			System.out.print(usuario.getUsuario());
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "UPDATE usuarios SET contrasena = ?, nombre = ?, contacto = ?, activo = ?, codigoRol = ? ,codigo=? WHERE usuario = ?"
		        );
		        statement.setString(1, usuario.getContrasena());
		        statement.setString(2, usuario.getNombre());
				statement.setString(3, usuario.getContacto());
				statement.setBoolean(4, usuario.isActivo());
				statement.setInt(5, usuario.getRol().getCodigo());
		        statement.setString(6, usuario.getCodigo());
		        statement.setString(7, usuario.getUsuario());
		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("Usuario actualizado correctamente.");
		        } else {
		            System.out.println("No se encontró el Usuario.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al actualisar Usuario");
		}finally {
		ConnectionManager.disconnect();
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
					"SELECT u.codigo, u.usuario,  u.contrasena, u.nombre, u.contacto,u.activo, r.codigo as codigo_rol, r.nombre as nombre_rol "
							+ " FROM usuarios u JOIN roles r ON (u.codigoRol = r.codigo) " + " WHERE u.usuario = ?");

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
						rs.getString("contacto"), rol,activo,rs.getString("codigo"));
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
	public List<Usuario> findAll() throws DataNullException {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			Connection conn = ConnectionManager.getConnection();
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT u.codigo, u.usuario,  u.contrasena, u.nombre, u.contacto,u.activo, r.codigo as codigo_rol, r.nombre as nombre_rol  "
							+ "FROM usuarios u JOIN roles r ON (u.codigoRol = r.codigo) ");

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
							rs.getString("nombre"), rs.getString("contacto"), rol,activo,rs.getString("codigo"));
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
//Metodo Helper, al cargar usuaios desde la base de datos, cuando se crean Usuarion desde el programa puede generar claves repetidas
	//esta cantdad se utiliza para que sea la base del contador de la clase Usuario. 
	public int obtenerCantidadUsuarios() throws SQLException {
	    String sql = "SELECT COUNT(*) FROM usuarios";

	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        if (rs.next()) {
	            return rs.getInt(1);  // devuelve el COUNT(*)
	        }
	    }
	    return 0;
	}
}
