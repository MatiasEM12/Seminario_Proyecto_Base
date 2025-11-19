package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;
import ar.edu.unrn.seminario.modelo.Coordenada;
import ar.edu.unrn.seminario.modelo.Donante;
import ar.edu.unrn.seminario.modelo.Ubicacion;

public class DonanteDAOJDBC implements DonanteDao{
	UbicacionDAO u;
	public void create(Donante donante) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO donante(codigo, nombre,apellido, dni,contacto,Fecha_Nacimiento, username, codUbicacion, activo)"
							+ " VALUES (?, ?, ?, ?, ?, ?,?,?,?)");
			
			java.sql.Date fechaSQL = java.sql.Date.valueOf(donante.getFecha_nac());
			
			
			
			statement.setString(1, donante.getCodigo());
			statement.setString(2, donante.getNombre());
			statement.setString(3, donante.getApellido());
			statement.setString(4, donante.getDni());
			statement.setString(5, donante.getContacto());
			statement.setDate(6,fechaSQL);
			statement.setString(7, donante.getUsername());
			statement.setString(8, donante.getUbicacion().getCodigo());
			statement.setObject(9, true);
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				// System.out.println("Modificando " + cantidad + " registros");
			} else {
				System.out.println("Error al actualizar");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
	        System.out.println("Error al procesar consulta (INSERT Donante): " + e.getMessage());
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
			    System.out.println("INSERT Donante OK - codigo=" + donante.getCodigo() + ", username=" + donante.getUsername());
			} else {
			    throw new SQLException("No se insertó el donante (executeUpdate devolvió 0)");
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

	public Donante find(String codigo)
	        throws DataNullException, DataEmptyException, DataObjectException, DataDateException {

	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    Donante donante = null;

	    try {
	        conn = ConnectionManager.getConnection();
	        String sql =
	                "SELECT D.codigo           AS d_codigo, " +
	                "       D.nombre           AS d_nombre, " +
	                "       D.apellido         AS d_apellido, " +
	                "       D.dni              AS d_dni, " +
	                "       D.Fecha_Nacimiento AS d_fecha, " +
	                "       D.username         AS d_username, " +
	                "       D.contacto         AS d_contacto, " +
	                "       U.codigo           AS u_codigo, " +
	                "       U.zona             AS u_zona, " +
	                "       U.barrio           AS u_barrio, " +
	                "       U.direccion        AS u_direccion, " +
	                "       U.codCoordenada    AS u_codCoordenada, " +
	                "       C.codigo           AS c_codigo, " +
	                "       C.Latitud          AS c_latitud, " +
	                "       C.Longitud         AS c_longitud " +
	                "FROM donante D " +
	                "LEFT JOIN ubicacion U ON D.codUbicacion = U.codigo " +
	                "LEFT JOIN coordenada C ON U.codCoordenada = C.codigo " +
	                "WHERE D.codigo = ?";

	        ps = conn.prepareStatement(sql);
	        ps.setString(1, codigo);
	        rs = ps.executeQuery();

	        if (rs.next()) {
	            // Ubicacion + Coordenada
	            Ubicacion u = null;

	            String codUbicacion = rs.getString("u_codigo");
	            if (codUbicacion != null) {
	                String codCoordenada = rs.getString("c_codigo");
	                String codCoordenadaRef = rs.getString("u_codCoordenada");

	                if (codCoordenada == null && codCoordenadaRef != null) {
	                    // Hay referencia a coordenada pero no fila en tabla coordenada
	                    throw new DataObjectException(
	                        "La ubicacion " + codUbicacion +
	                        " tiene codCoordenada=" + codCoordenadaRef +
	                        " pero no existe fila en la tabla coordenada"
	                    );
	                }

	                Coordenada c = null;
	                if (codCoordenada != null) {
	                    c = new Coordenada(
	                            rs.getDouble("c_latitud"),
	                            rs.getDouble("c_longitud"),
	                            codCoordenada
	                    );
	                }

	                u = new Ubicacion(
	                        rs.getString("u_zona"),
	                        rs.getString("u_barrio"),
	                        rs.getString("u_direccion"),
	                        c
	                );
	            }

	            java.sql.Date sqlDate = rs.getDate("d_fecha");
	            LocalDate fecha = (sqlDate != null) ? sqlDate.toLocalDate() : null;

	            donante = new Donante(
	                    rs.getString("d_nombre"),
	                    rs.getString("d_apellido"),
	                    fecha,
	                    rs.getString("d_dni"),
	                    rs.getString("d_contacto"),
	                    u,
	                    rs.getString("d_username"),
	                    rs.getString("d_codigo")
	            );
	        }

	    } catch (SQLException e) {
	        System.out.println("Error al procesar consulta (find Donante): " + e.getMessage());
	        throw new RuntimeException(e);
	    } finally {
	        try { if (rs != null) rs.close(); } catch (SQLException ex) {}
	        try { if (ps != null) ps.close(); } catch (SQLException ex) {}
	        ConnectionManager.disconnect();
	    }
	    return donante;
	}



	public List<Donante> findAll() {
        List<Donante> donantes = new ArrayList<>();
        Connection conn = null;
        PreparedStatement sent = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();
            sent = conn.prepareStatement("SELECT codigo FROM donante");
            rs = sent.executeQuery();

            while (rs.next()) {
                String codigo = rs.getString("codigo");
                try {
                    Donante d = this.find(codigo);
                    if (d != null) {
                        donantes.add(d);
                    } else {
                        System.out.println(
                                "Advertencia: find(codigo=" + codigo + ") devolvió null - no se añadirá a la lista."
                        );
                    }
                } catch (Exception ex) {
                    System.out.println(
                            "Advertencia: error al cargar donante codigo=" + codigo + " - " + ex.getMessage()
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al procesar consulta " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ex) {}
            try { if (sent != null) sent.close(); } catch (SQLException ex) {}
            ConnectionManager.disconnect();
        }
        return donantes;
    }



	@Override
	public void remove(Long id) {
		// TODO Auto-generated method stub
		
	}
	
	public int obtenerCantidadUsuarios() throws SQLException {
	    String sql = "SELECT COUNT(*) FROM donante";

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