package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;
import ar.edu.unrn.seminario.modelo.Coordenada;
import ar.edu.unrn.seminario.modelo.Ubicacion;

public class UbicacionDAOJDBC  implements UbicacionDAO{
	CoordenadaDAO coordenada = new CoordenadaDAOJDBC();

	
	@Override
	public void create(Ubicacion ubicacion) throws DAOException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();
            coordenada.create(ubicacion.getCoordenada());
            // 1) Verificar si ya existe la ubicación con ese código
            String sqlExiste = "SELECT codigo FROM ubicacion WHERE codigo = ?";
            ps = conn.prepareStatement(sqlExiste);
            ps.setString(1, ubicacion.getCodigo());
            rs = ps.executeQuery();

            if (rs.next()) {
                
                System.out.println("Ubicacion ya existente: " + ubicacion.getCodigo()+".codigo UB100");
                return;
            }

            rs.close();
            ps.close();

            // 2) Insertar la ubicación
            String sqlInsert = "INSERT INTO ubicacion (codigo, zona, barrio, direccion, codCoordenada, activo) "
                             + "VALUES (?, ?, ?, ?, ?, ?)";

            ps = conn.prepareStatement(sqlInsert);
            ps.setString(1, ubicacion.getCodigo());
            ps.setString(2, ubicacion.getZona());
            ps.setString(3, ubicacion.getBarrio());
            ps.setString(4, ubicacion.getDireccion());
            ps.setString(5, ubicacion.getCoordenada().getCodigo());
            ps.setBoolean(6, true); // activo = true

            int cantidad = ps.executeUpdate();
            if (cantidad > 0) {
                System.out.println("Ubicacion insertada: " + ubicacion.getCodigo());
            } else {
            	throw new DAOException("No se insertó la ubicacion (executeUpdate devolvió 0)");
            }

        } catch (SQLException e) {
            
        	throw new DAOException("Error al procesar consulta (create Ubicacion): " + e.getMessage()+".codigo UB200");
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ex) {}
            try { if (ps != null) ps.close(); } catch (SQLException ex) {}
            ConnectionManager.disconnect();
        }
    }

	

	@Override
	public void update(Ubicacion ubicacion) throws DAOException{
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE ubicacion SET codigo=?, zona= ?, barrio = ? , direccion= ?, codCoordenada= ? "
							+ "WHERE codigo = ?");
			statement.setString(1, ubicacion.getCodigo());
			statement.setString(2, ubicacion.getZona());
			statement.setString(3,ubicacion.getBarrio() );
			statement.setString(4,ubicacion.getDireccion());
			statement.setString(5,ubicacion.getCoordenada().getCodigo());
			statement.setString(6, ubicacion.getCodigo());
			
			
			
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				 System.out.println("La ubucacion se ha actualizado correctamente");
			} else {
				throw new DAOException("Error al actualizar");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
			throw new DAOException("Error al procesar consulta"+".codigo UB300");
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
	public void remove(String codigo) throws DataNullException, DAOException {
		try {
			 Connection conn = ConnectionManager.getConnection();
			 String sqlSelect = "SELECT codCoordenada FROM ubicacion WHERE codigo = ?";
		        String codCoordenada = null;

		        try (PreparedStatement psSelect = conn.prepareStatement(sqlSelect)) {
		            psSelect.setString(1, codigo);
		            try (ResultSet rs = psSelect.executeQuery()) {
		                if (rs.next()) {
		                    codCoordenada = rs.getString("codCoordenada");
		                } else {
		                    System.out.println("No se encontró la ubicación con código: " + codigo);
		                    return;
		                }
		            }
		        }
			
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM ubicacion WHERE codigo = ? "
		        );

		        statement.setString(1, codigo);

		        int cantidad = statement.executeUpdate();
		        
		     
		        if (cantidad > 0) {
		            System.out.println("ubicacion eliminada correctamente.");
		        } else {
		        	throw new DAOException("No se encontró la ubicacion.");
		        }
			
		     // Verificar si hay otras ubicaciones que usan la misma coordenada
		        String sqlCount = "SELECT COUNT(*) AS total FROM ubicacion WHERE codCoordenada = ?";
		        int count = 0;
		        try (PreparedStatement psCount = conn.prepareStatement(sqlCount)) {
		            psCount.setString(1, codCoordenada);
		            try (ResultSet rsCount = psCount.executeQuery()) {
		                if (rsCount.next()) {
		                    count = rsCount.getInt("total");
		                }
		            }
		        }
		        
		       // Si no hay más referencias, eliminar la coordenada
		       if (count == 0) {
		 
		             coordenada.remove(codCoordenada);
		    	   }

		}catch(SQLException e) {
			throw new DAOException("Error al Eliminar ubicacion"+".codigo UB400");
		}finally {
			ConnectionManager.disconnect();
		}
		
		
	}

	@Override
	public void remove(Ubicacion ubicacion) throws DataNullException, DAOException {
		try {
			 Connection conn = ConnectionManager.getConnection();
			 String sqlSelect = "SELECT codCoordenada FROM ubicacion WHERE codigo = ?";
		        String codCoordenada = null;

		        try (PreparedStatement psSelect = conn.prepareStatement(sqlSelect)) {
		            psSelect.setString(1, ubicacion.getCodigo());
		            try (ResultSet rs = psSelect.executeQuery()) {
		                if (rs.next()) {
		                    codCoordenada = rs.getString("codCoordenada");
		                } else {
		                    System.out.println("No se encontró la ubicación con código: " + ubicacion.getCodigo());
		                    return;
		                }
		            }
		        }
			
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM ubicacion WHERE codigo = ? "
		        );

		        statement.setString(1,  ubicacion.getCodigo());

		        int cantidad = statement.executeUpdate();
		        
		     
		        if (cantidad > 0) {
		            System.out.println("ubicacion eliminada correctamente.");
		        } else {
		        	throw new DAOException("No se encontró la ubicacion.");
		        }
			
		     // Verificar si hay otras ubicaciones que usan la misma coordenada
		        String sqlCount = "SELECT COUNT(*) AS total FROM ubicacion WHERE codCoordenada = ?";
		        int count = 0;
		        try (PreparedStatement psCount = conn.prepareStatement(sqlCount)) {
		            psCount.setString(1, codCoordenada);
		            try (ResultSet rsCount = psCount.executeQuery()) {
		                if (rsCount.next()) {
		                    count = rsCount.getInt("total");
		                }
		            }
		        }
		        
		       // Si no hay más referencias, eliminar la coordenada
		       if (count == 0) {
		 
		             coordenada.remove(codCoordenada);
		    	   }

		}catch(SQLException e) {
			throw new DAOException("Error al Eliminar ubicacion"+".codigo UB500");
		}finally {
			ConnectionManager.disconnect();
		}
		
		
	}

	
	@Override
	public Ubicacion find(String codigo) throws DAOException{

	    Ubicacion ubicacion = null;

	    try {
	        Connection conn = ConnectionManager.getConnection();
	        PreparedStatement sent = conn.prepareStatement(
	            "SELECT " +
	            "U.codigo AS u_codigo, U.zona, U.barrio, U.direccion, " +
	            "U.codCoordenada AS u_codCoordenada, " +
	            "C.codigo AS c_codigo, C.Latitud, C.Longitud " +
	            "FROM ubicacion U " +
	            "LEFT JOIN coordenada C ON U.codCoordenada = C.codigo " +
	            "WHERE U.codigo = ?"
	        );

	        sent.setString(1, codigo);
	        ResultSet rs = sent.executeQuery();

	        if (rs.next()) {

	            Coordenada coordenada = null;

	            String codCoordenada = rs.getString("c_codigo");
	            String codCoordenadaRef = rs.getString("u_codCoordenada");

	            if (codCoordenada == null && codCoordenadaRef != null) {
	                throw new DataObjectException(
	                    "La ubicación " + codigo +
	                    " referencia codCoordenada=" + codCoordenadaRef +
	                    " inexistente"
	                );
	            }

	            if (codCoordenada != null) {
	                coordenada = new Coordenada(
	                    rs.getDouble("Latitud"),
	                    rs.getDouble("Longitud"),
	                    codCoordenada
	                );
	            }

	            ubicacion = new Ubicacion(
	                rs.getString("u_codigo"),
	                rs.getString("zona"),
	                rs.getString("barrio"),
	                rs.getString("direccion"),
	                coordenada
	            );
	        }

	    } catch (SQLException e) {
	    	throw new DAOException("Error SQL Ubicacion.find: " + e.getMessage() + ". codigo UB600");
	    } catch (Exception e) {
	    	throw new DAOException("Error inesperado Ubicacion.find: " + e.getMessage() + ". codigo UB601");
	    } finally {
	        ConnectionManager.disconnect();
	    }

	    return ubicacion;
	}


	@Override
	public List<Ubicacion> findAll() throws DAOException{
	    List<Ubicacion> ubicaciones = new ArrayList<>();

	    try {
	        Connection conn = ConnectionManager.getConnection();
	        PreparedStatement sent = conn.prepareStatement(
	            "SELECT codigo FROM ubicacion"
	        );
	        ResultSet rs = sent.executeQuery();

	        while (rs.next()) {
	            ubicaciones.add(this.find(rs.getString("codigo")));
	        }

	    } catch (SQLException e) {
	    	throw new DAOException("Error al procesar consulta " + e.getMessage() + ".codigo UB700");
	    } catch (Exception e) {
	    	throw new DAOException("Error inesperado: " + e.getMessage() + ".codigo UB701");
	    } finally {
	        ConnectionManager.disconnect();
	    }

	    return ubicaciones;
	}

}

