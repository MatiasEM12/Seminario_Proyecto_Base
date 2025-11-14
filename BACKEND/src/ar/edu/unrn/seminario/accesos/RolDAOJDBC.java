package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.StateChangeException;
import ar.edu.unrn.seminario.modelo.Rol;

public class RolDAOJDBC implements RolDao {

	@Override
	public void create(Rol rol) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO roles(codigo, nombre,activo) "  //elimine descripcion para probar tabla base
							+ "VALUES (?, ?, ?)");

			statement.setInt(1, rol.getCodigo());
			statement.setString(2, rol.getNombre());
			//statement.setString(3, rol.getDescripcion());
			statement.setBoolean(3, rol.isActivo());
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
	//funciona
	public void update(Rol rol) {
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE roles SET nombre = ?, descripcion = ?, activo = ? WHERE codigo = ?");
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
		            "DELETE FROM roles WHERE codigo = ?"
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
		            "DELETE FROM roles WHERE codigo = ?"
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

	public Rol find(Integer codigo) {
	    Rol rol = null;

	    String sql = "SELECT r.codigo, r.nombre, r.activo FROM roles r WHERE r.codigo = ?";

	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement statement = conn.prepareStatement(sql)) {

	        statement.setInt(1, codigo);

	        try (ResultSet rs = statement.executeQuery()) {
	            if (rs.next()) {
	                boolean activo = rs.getInt("activo") == 1;
	                rol = new Rol(
	                    rs.getInt("codigo"),
	                    rs.getString("nombre"),
	                    activo
	                );
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error al procesar consulta: " + e.getMessage());
	    } catch (ar.edu.unrn.seminario.exception.DataNullException e) {
	        System.out.println("Error de datos al crear Rol: " + e.getMessage());
	    }

	    return rol;
	}

	@Override
	public List<Rol> findAll() throws StateChangeException {
	    List<Rol> listado = new ArrayList<>();

	    String sql = "SELECT r.codigo, r.nombre, r.activo FROM roles r";

	    try (Connection conn = ConnectionManager.getConnection();
	         Statement sentencia = conn.createStatement();
	         ResultSet resultado = sentencia.executeQuery(sql)) {

	        while (resultado.next()) {
	            int codigo = resultado.getInt("codigo");
	            String nombre = resultado.getString("nombre");
	            int activoBD = resultado.getInt("activo"); // 0 o 1
	            boolean activo = (activoBD == 1);

	            // Usamos el constructor que ya tenés:
	            Rol rol = new Rol(codigo, nombre, activo);

	            listado.add(rol);
	        }
	    } catch (SQLException e) {
	        System.out.println("Error de MySQL\n" + e);
	    } catch (ar.edu.unrn.seminario.exception.DataNullException e) {
	        // si el constructor de Rol tira DataNullException
	        System.out.println("Error de datos al crear Rol: " + e.getMessage());
	    }

	    return listado;
	}


}
