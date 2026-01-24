package ar.edu.unrn.seminario.accesos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Coordenada;

public class CoordenadaDAOJDBC implements CoordenadaDAO{

	@Override
	public void create(Coordenada coordenada) throws DAOException{
		
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
				throw new DAOException("Error al actualizar. codigo error C100");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
			throw new DAOException("Error al procesar consulta. codigo error C101");
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}
	
	}

	@Override
	public void update(Coordenada coordenada) throws DAOException{
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE coordenada SET Latitud = ?, Longitud = ? WHERE codigo = ?");
			statement.setDouble(1, coordenada.getLatitud());
			statement.setDouble(2, coordenada.getLongitud());
			statement.setString(3, coordenada.getCodigo());
			
			
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				 System.out.println("La coordenada se ha actualizado correctamente");
			} else {
				throw new DAOException("Error al actualizar. codigo error C200");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
			throw new DAOException("Error al procesar consulta. codigo error C201");
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
	public void remove(String codigo) throws DataNullException,DAOException{
		if (codigo == null || codigo.isBlank()) {
		    throw new DAOException("Matrícula inválida. V302");
		}
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
		        	throw new DAOException("No se encontró la coordenada. codigo error C300");
		        }
			
		}catch(SQLException e) {
			throw new DAOException("Error al Eliminar coordenada. codigo error C301");
		}finally {
			ConnectionManager.disconnect();
		}
		
	}

	@Override
	public void remove(Coordenada coordenada) throws DAOException{
		
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM coordenada WHERE codigo = ? "
		        );

		        statement.setString(1,coordenada.getCodigo());

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		        	throw new DAOException("coordenada eliminada correctamente.");
		        } else {
		        	throw new DAOException("No se encontró la coordenada. codigo error C400");
		        }
			
		}catch(SQLException e) {
			throw new DAOException("Error al Eliminar coordenada. codigo error C401");
		}finally {
			ConnectionManager.disconnect();
		}
	}
		
	

	@Override
	public Coordenada find(String codigo) throws DataNullException,DAOException{
		Coordenada coordenada= null;
		if (codigo == null || codigo.isBlank()) {
		    throw new DAOException("Matrícula inválida. C502");
		}
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT codigo,Latitud,Longitud "
			+ "FROM coordenada "+ "WHERE codigo = ?");
			sent.setString(1, codigo);
			ResultSet rs = sent.executeQuery();
			if (rs.next()) {
				
				coordenada=new Coordenada(rs.getDouble("Latitud"),rs.getDouble("Longitud"),rs.getString("codigo"));
				
			}
		}
		catch(SQLException e){
			throw new DAOException("Error al procesar consulta"+ e.getMessage()+". codigo error C500");
		}
		catch (Exception e) {
			throw new DAOException("Error inesperado: " + e.getMessage()+". codigo error C501");
		} 
		finally {
			ConnectionManager.disconnect();
		}	 
		return coordenada;
	}

	@Override
	public List<Coordenada> findAll() throws DAOException{
		List<Coordenada> coordenadas = new ArrayList<>();
		
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT codigo  "
					+ "FROM coordenada ");
			ResultSet rs = sent.executeQuery();
			while (rs.next()) {
				
				coordenadas.add(this.find(rs.getString("codigo")));
			}
		}
		catch(SQLException e){
			throw new DAOException("Error al procesar consulta"+ e.getMessage()+". codigo error C600");
		}
		catch (Exception e) {
			throw new DAOException("Error inesperado: " + e.getMessage()+". codigo error C601");
		} 
		finally {
			ConnectionManager.disconnect();
		}	 
		return coordenadas;
	}

}