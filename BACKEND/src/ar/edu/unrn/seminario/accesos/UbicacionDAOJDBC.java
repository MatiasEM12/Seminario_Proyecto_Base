package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.modelo.Coordenada;
import ar.edu.unrn.seminario.modelo.Ubicacion;

public class UbicacionDAOJDBC  implements UbicacionDAO{
	CoordenadaDAO coordenada = null;
	
	@Override
public void create(Ubicacion ubicacion) {
		try {
			

			Connection conn = ConnectionManager.getConnection();
			
			// Se verifica si la coordenada existe en la Base de Datos
			PreparedStatement checkCoord= conn
					.prepareStatement("SELECT codigo FROM coordenada WHERE codigo = ?");
			checkCoord.setString(1, ubicacion.getCoordenada().getCodigo());
	        ResultSet rs = checkCoord.executeQuery();
			
	        if(!rs.next()) { // Si no existe se crea
	        	
	        	coordenada.create( ubicacion.getCoordenada());
	                 System.out.println("Coordenada creada: " + ubicacion.getCoordenada().getCodigo());
	        } 
	             //  Si la coordenada SÍ existe , solo se informa
	             else {
	                 System.out.println("Coordenada ya existente: " + ubicacion.getCoordenada().getCodigo());
	             }
	        // Se carga la Ubicacion en la Base de Datos	
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO Ubicacion(codigo,zona,barrio,direccion,codCoordenada)"
							+ " VALUES (?, ?, ?,?,?)");
			
			statement.setString(1, ubicacion.getCodigo());
			statement.setString(2, ubicacion.getZona());
			statement.setString(3,ubicacion.getBarrio() );
			statement.setString(4,ubicacion.getDireccion());
			statement.setString(5,ubicacion.getCoordenada().getCodigo());
			
			
		
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
	public void update(Ubicacion ubicacion) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE ubicacion SET codigo ?, zona= ?, barrio = ? , direccion= ?, codCoordenada= ? "
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
	public void remove(Long id) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	@Override
	public void remove(String codigo) {
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
		            System.out.println("No se encontró la ubicacion.");
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
			System.out.println("Error al Eliminar ubicacion");
		}finally {
			ConnectionManager.disconnect();
		}
		
		
	}

	@Override
	public void remove(Ubicacion ubicacion) {
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
		            System.out.println("No se encontró la ubicacion.");
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
			System.out.println("Error al Eliminar ubicacion");
		}finally {
			ConnectionManager.disconnect();
		}
		
		
	}

	
	
	@Override
	public Ubicacion find(String codigo) {
		
		Ubicacion ubicacion= null;
		Coordenada coordenada = null;
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT U.codigo,U.zona,U.barrio,U.descripcion, C.codigo,C.Latitud,C.Longitud "
			+ "FROM coordenada C, ubicacion U "+ "WHERE U.codigo = ? AND U.codCoordenada==C.codigo");
			sent.setString(1, codigo);
			ResultSet rs = sent.executeQuery();
			if (rs.next()) {
				
				coordenada=new Coordenada(rs.getDouble("C.Latitud"),rs.getDouble("C.Longitud"),rs.getString("C.codigo"));
				ubicacion= new Ubicacion(rs.getString("U.codigo"),rs.getString("U.zona"),rs.getString("U.barrio"),rs.getString("U.descripcion")
						,coordenada);
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
		return ubicacion;
	}

	@Override
	public List<Ubicacion> findAll() {
		List<Ubicacion> ubicaciones = new ArrayList<>();
		
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT codigo"
					+ "FROM ubicacion ");
			ResultSet rs = sent.executeQuery();
			while (rs.next()) {
				
				ubicaciones.add(this.find(rs.getString("codigo")));
			
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
		return ubicaciones;
	}
	}

}
