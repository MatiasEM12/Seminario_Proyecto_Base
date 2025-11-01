package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.modelo.Rol;

public class RolDAOJDBC implements RolDao {

	@Override
	public void create(Rol rol) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO rol(codigo, nombre, descripcion,estado) "
							+ "VALUES (?, ?, ?, ?)");

			statement.setInt(1, rol.getCodigo());
			statement.setString(2, rol.getNombre());
			statement.setString(3, rol.getDescripcion());
			statement.setBoolean(4, rol.isActivo());
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
	public void update(Rol rol) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE rol SET nombre = ?, descripcion = ?, estado = ? WHERE codigo = ?");

			statement.setString(1, rol.getNombre());
			statement.setString(2, rol.getDescripcion());
			statement.setBoolean(3, rol.isActivo());
			statement.setInt(4, rol.getCodigo());
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				 System.out.println("El Rol se ha actualizado correctamente");
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

	public void remove(Integer codigo) {
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM rol WHERE codigo = ?"
		        );

		        statement.setInt(1, codigo);

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

	@Override
	public void remove(Rol rol) {
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM rol WHERE codigo = ?"
		        );

		        statement.setInt(1, rol.getCodigo());

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

	@Override
	public Rol find(Integer codigo) {
		Rol rol = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("SELECT r.codigo, r.nombre " + " FROM roles r " + " WHERE r.codigo = ?");

			statement.setInt(1, codigo);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				rol = new Rol(rs.getInt("codigo"), rs.getString("nombre"));
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

		return rol;
	}

	@Override
	public List<Rol> findAll() {
		List<Rol> listado = new ArrayList<Rol>();
		Statement sentencia = null;
		ResultSet resultado = null;
		try {
			sentencia = ConnectionManager.getConnection().createStatement();
			resultado = sentencia.executeQuery("select r.nombre, r.codigo, r.activo from roles r ");

			while (resultado.next()) {
				Rol rol = new Rol();
				rol.setNombre(resultado.getString(1));
				rol.setCodigo(resultado.getInt(2));
				rol.setActivo(resultado.getBoolean(3));

				listado.add(rol);
			}
		} catch (SQLException e) {
			System.out.println("Error de mySql\n" + e.toString());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.disconnect();
		}

		return listado;
	}

}
