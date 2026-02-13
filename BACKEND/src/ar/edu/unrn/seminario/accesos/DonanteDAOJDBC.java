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
import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;
import ar.edu.unrn.seminario.modelo.Coordenada;
import ar.edu.unrn.seminario.modelo.Donante;
import ar.edu.unrn.seminario.modelo.Ubicacion;

public class DonanteDAOJDBC implements DonanteDao{
	private UbicacionDAO u = new UbicacionDAOJDBC();
	public void create(Donante donante) throws DAOException{
		try {

			Connection conn = ConnectionManager.getConnection();
			u.create(donante.getUbicacion());
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
				throw new DAOException("Error al actualizar. codigo error UD100");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
			throw new DAOException("Error al procesar consulta (INSERT Donante): " + e.getMessage()+". codigo error UD101");
	    } finally {
	        ConnectionManager.disconnect();
	    }
		
	}

	public void update(Donante donante) throws DAOException{
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE donante \r\n"
							+ "SET nombre = ?, \r\n"
							+ "    apellido = ?, \r\n"
							+ "    dni = ?, \r\n"
							+ "    contacto = ?, \r\n"
							+ "    Fecha_Nacimiento = ?, \r\n"
							+ "    username = ?, \r\n"
							+ "    codUbicacion = ?\r\n"
							+ "WHERE codigo = ?\r\n"
							+ "");
			java.sql.Date fechaSQL = java.sql.Date.valueOf(donante.getFecha_nac());
			
			
			statement.setString(1, donante.getNombre());
			statement.setString(2, donante.getApellido());
			statement.setString(3, donante.getDni());
			statement.setString(4, donante.getContacto());
			statement.setDate(5, fechaSQL);
			statement.setString(6, donante.getUsername());
			statement.setString(7, donante.getUbicacion().getCodigo());
			statement.setString(8, donante.getCodigo());
			

			
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
			    System.out.println("INSERT Donante OK - codigo=" + donante.getCodigo() + ", username=" + donante.getUsername());
			} else {
			    throw new SQLException("No se insertó el donante (executeUpdate devolvió 0). codigo error UD200");
			}

		} catch (SQLException e) {
			throw new DAOException("Error al procesar consulta. codigo error UD201");
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}
		
	}

	public void remove(String codigo) throws DAOException, DataNullException{
		try {
			 Connection conn = ConnectionManager.getConnection();
			  PreparedStatement statement1 = conn.prepareStatement(
			            "SELECT codUbicacion FROM donante WHERE codigo = ?"
			        );
			 
			  statement1.setString(1, codigo);
			  String codUbicacion = null;
			  ResultSet rs = statement1.executeQuery();
			  if (rs.next()) {
				  codUbicacion = rs.getString("codUbicacion");
			  }
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM donante WHERE codigo = ?"
		        );

		        statement.setString(1, codigo);
		        
				
				if (codUbicacion != null) {
				    u.remove(codUbicacion);
				}
	        u.remove(rs.getString("codUbicacion"));
		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("Donante eliminado correctamente.");
		        } else {
		        	throw new DAOException("No se encontró al donante. codigo error UD300");
		        }
			
		}catch(SQLException e) {
			throw new DAOException("Error al Eliminar donanre. codigo error UD301");
		}
		
	}

	public void remove(Donante donante) throws DAOException, DataNullException{
		try {
			
			 Connection conn = ConnectionManager.getConnection();
			  PreparedStatement statement1 = conn.prepareStatement(
			            "SELECT codUbicacion FROM donante WHERE username = ?"
			        );
			  statement1.setString(1, donante.getUsername());
			  ResultSet rs = statement1.executeQuery();
			  
			 
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM donante WHERE codigo = ? "
		        );

		        ;
		        statement.setString( 1,donante.getCodigo());
		        String codUbicacion = null;
		        if (rs.next()) {
		            codUbicacion = rs.getString("codUbicacion");
		        }

		        if (codUbicacion != null) {
		            u.remove(codUbicacion);
		        }
		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("donante eliminado correctamente.");
		        } else {
		        	throw new DAOException("No se encontró el donante. codigo error UD400");
		        }
			
		}catch(SQLException e) {
			throw new DAOException("Error al Eliminar donante. codigo error UD401");
		}
	}
	public Donante find(String codigo)
	        throws DataNullException, DataEmptyException, DataObjectException, DataDateException, DAOException, DataLengthException {

	    Donante donante = null;

	    try {
	        Connection conn = ConnectionManager.getConnection();
	        PreparedStatement ps = conn.prepareStatement(
	            "SELECT codigo, nombre, apellido, dni, contacto, Fecha_Nacimiento, username, codUbicacion " +
	            "FROM donante WHERE codigo = ?"
	        );

	        ps.setString(1, codigo);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {

	            UbicacionDAO ubicacionDAO = new UbicacionDAOJDBC();
	            String codUbicacion = rs.getString("codUbicacion");

	            Ubicacion u = ubicacionDAO.find(codUbicacion);
	            if (u == null) {
	                throw new DataObjectException( //200
	                    "Ubicacion inexistente para donante " + codigo
	                );
	            }

	            LocalDate fecha = rs.getDate("Fecha_Nacimiento").toLocalDate();

	            donante = new Donante(
	                rs.getString("nombre"),
	                rs.getString("apellido"),
	                fecha,
	                rs.getString("dni"),
	                rs.getString("contacto"),
	                u,
	                rs.getString("username"),
	                rs.getString("codigo")
	            );
	        }

	    } catch (SQLException e) {
	    	throw new DAOException("Error SQL find Donante: " + e.getMessage());
	    } finally {
	        ConnectionManager.disconnect();
	    }

	    return donante;
	}




	public List<Donante> findAll() throws DAOException{
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
                                "Advertencia: find(codigo=" + codigo + ") devolvió null - no se añadirá a la lista. codigo error UD600"
                        );
                    }
                } catch (Exception ex) {
                	throw new DAOException(
                            "Advertencia: error al cargar donante codigo=" + codigo + " - " + ex.getMessage()+". codigo error UD601"
                    );
                }
            }
        } catch (SQLException e) {
        	throw new DAOException("Error al procesar consulta " + e.getMessage()+". codigo error UD602");
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
	
	public int obtenerCantidadDonantes() throws SQLException {
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
	
	
	@Override
	public int obtenerMaximoDonantes() throws SQLException {
		  String sql = "SELECT MAX(codigo) FROM donante";

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