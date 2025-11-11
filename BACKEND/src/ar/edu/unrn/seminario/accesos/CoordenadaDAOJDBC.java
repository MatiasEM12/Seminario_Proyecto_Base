package ar.edu.unrn.seminario.accesos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.modelo.Coordenada;

public class CoordenadaDAOJDBC implements CoordenadaDAO{

	@Override
	public void create(Coordenada coordenada) {
		
		try {
			
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO coordenada(codigo,Latitud,Longitud)"
							+ " VALUES (?, ?, ?)");
			
			statement.setString(1, coordenada.getCodigo());
			statement.setDouble(2, coordenada.getLatitud());
			statement.setDouble(3, coordenada.getLongitud());
		
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
	public void update(Coordenada coordenada) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE coordenada SET codigo ?, Latitud = ?, Longitud = ? WHERE codigo = ?");
			statement.setString(1, coordenada.getCodigo());
			statement.setDouble(2, coordenada.getLatitud());
			statement.setDouble(3, coordenada.getLongitud());
			statement.setString(4, coordenada.getCodigo());
			
			
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				 System.out.println("La coordenada se ha actualizado correctamente");
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
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM coordenada WHERE codigo = ? "
		        );

		        statement.setString(1, codigo);

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("coordenada eliminada correctamente.");
		        } else {
		            System.out.println("No se encontró la coordenada.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar coordenada");
		}finally {
			ConnectionManager.disconnect();
		}
		
	}

	@Override
	public void remove(Coordenada coordenada) {
		
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM coordenada WHERE codigo = ? "
		        );

		        statement.setString(1,coordenada.getCodigo());

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("coordenada eliminada correctamente.");
		        } else {
		            System.out.println("No se encontró la coordenada.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar coordenada");
		}finally {
			ConnectionManager.disconnect();
		}
	}
		
	

	@Override
	public Coordenada find(String codigo) {
		Coordenada coordenada= null;
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT codigo,Latitud,Longitud "
			+ "FROM coordenada "+ "WHERE D.codigo = ?");
			sent.setString(1, codigo);
			ResultSet rs = sent.executeQuery();
			if (rs.next()) {
				
				coordenada=new Coordenada(rs.getDouble("Latitud"),rs.getDouble("Longitud"),rs.getString("codigo"));
				
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
		return coordenada;
	}

	@Override
	public List<Coordenada> findAll() {
		List<Coordenada> coordenadas = new ArrayList<>();
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT codigo,Latitud,Longitud "
					+ "FROM coordenada "+ "WHERE D.codigo = ?");
			ResultSet rs = sent.executeQuery();
			while (rs.next()) {
				
				Coordenada 	coordenada=new Coordenada(rs.getDouble("Latitud"),rs.getDouble("Longitud"),rs.getString("codigo"));
				
				coordenadas.add(coordenada);
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
		return coordenadas;
	}

}
