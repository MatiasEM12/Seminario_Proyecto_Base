package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import ar.edu.unrn.seminario.exception.*;


import java.util.ArrayList;

import ar.edu.unrn.seminario.modelo.Vehiculo;

public class VehiculoDAOJDBC implements VehiculoDAO{
	
	public void create(Vehiculo vehiculo) throws DAOException{
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement("INSERT INTO Vehiculo(matricula, tipo, modelo, capacidadMaxCarga, disponibilidad,cronogramaMantenimiento) "+ 
			"VALUES (?, ?, ?, ?, ?, ?)");
			statement.setString(1, vehiculo.getMatricula());
			statement.setString(2, vehiculo.getTipo());
			statement.setString(3, vehiculo.getModelo());
			statement.setDouble(4, vehiculo.getCapacidadMaxCarga());
			statement.setBoolean(5, vehiculo.isDisponibilidad());
			statement.setString(6, vehiculo.getCronogramaMantenimiento());
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				System.out.println("se creo " + cantidad + " registro");
			} else {
				throw new DAOException("No se pudo insertar el vehiculo V100");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
			throw new DAOException("Error al procesar consulta. codigo V100" + e);
		} catch (Exception e) {
			throw new DAOException("Error al crear un vehiculo"+".codigo V101" + e);
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}
	}

	public void update(Vehiculo vehiculo) throws DAOException{
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE Vehiculo SET matricula = ?, tipo = ?, modelo = ?, capacidadMaxCarga = ?, disponibilidad = ?, cronogramaMantenimiento = ? WHERE matricula = ?");
			statement.setString(1, vehiculo.getMatricula());
			statement.setString(2, vehiculo.getTipo());
			statement.setString(3, vehiculo.getModelo());
			statement.setDouble(4, vehiculo.getCapacidadMaxCarga());
			statement.setBoolean(5, vehiculo.isDisponibilidad());
			statement.setString(6, vehiculo.getCronogramaMantenimiento());
			//si queremos no actualisar la matricula que que es pk hai que eliminar en el set matricula y eliminar el primer get, la rason por la que quisieramos
			//sacarlo es que como es pk si se cambia por una que existe saltaria error.
			statement.setString(7, vehiculo.getMatricula());
			
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				 System.out.println("El vehiculo se ha actualizado correctamente");
			} else {
				throw new DAOException("Error al actualizar. codigo error V200");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
			throw new DAOException("Error al procesar consulta. codigo error V201"+e);
			// TODO: disparar Exception propia
		} finally {
			ConnectionManager.disconnect();
		}
	}
	
	public void remove(String matricula) throws DAOException{
		if (matricula == null || matricula.isBlank()) {
		    throw new DAOException("Matrícula inválida. V302");
		}
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM Vehiculo WHERE matricula = ? "
		        );

		        statement.setString(1, matricula);

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("vehiculo eliminado correctamente.");
		        } else {
		        	throw new DAOException("No se encontró el vehiculo. codigo error V300");
		        }
			
		}catch(SQLException e) {
			throw new DAOException("Error al Eliminar el vehiculo. codigo error V301"+e);
		}finally {
			ConnectionManager.disconnect();
		}
	}

	public Vehiculo find(String matricula) throws DAOException{
		Vehiculo vehiculo= null;
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT matricula, tipo, modelo, capacidadMaxCarga, disponibilidad, cronogramaMantenimiento "
			+ "FROM Vehiculo "+ "WHERE matricula = ?");
			sent.setString(1, matricula);
			ResultSet rs = sent.executeQuery();
			if (rs.next()) {
				
				vehiculo=new Vehiculo(rs.getString("matricula"),rs.getString("tipo"),rs.getString("modelo"),rs.getDouble("capacidadMaxCarga"),rs.getBoolean("disponibilidad"),rs.getString("cronogramaMantenimiento"));
				
			}
		}
		catch(SQLException e){
			throw new DAOException("Error al procesar consulta"+ e.getMessage()+". codigo error V500");
		}
		catch (Exception e) {
			throw new DAOException("Error inesperado: " + e.getMessage()+". codigo error V501");
		} 
		finally {
			ConnectionManager.disconnect();
		}	 
		return vehiculo;
	}

	public List<Vehiculo> findAll() throws DAOException{
		List<Vehiculo> vehiculo = new ArrayList<>();
		
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT matricula, tipo, modelo, capacidadMaxCarga, disponibilidad, cronogramaMantenimiento "
					+ "FROM Vehiculo ");
			ResultSet rs = sent.executeQuery();
			while (rs.next()) {
				vehiculo.add(new Vehiculo(
				        rs.getString("matricula"),
				        rs.getString("tipo"),
				        rs.getString("modelo"),
				        rs.getDouble("capacidadMaxCarga"),
				        rs.getBoolean("disponibilidad"),
				        rs.getString("cronogramaMantenimiento")
				    ));
			}
		}
		catch(SQLException e){
			throw new DAOException("Error al procesar consulta"+ e.getMessage()+". codigo error V600");
		}
		catch (Exception e) {
			throw new DAOException("Error inesperado: " + e.getMessage()+". codigo error V601");
		} 
		finally {
			ConnectionManager.disconnect();
		}	 
		return vehiculo;
	}
	
	public int obtenerCantidadVehiculos() throws SQLException {
	    String sql = "SELECT COUNT(*) FROM Vehiculo";

	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        if (rs.next()) {
	            return rs.getInt(1);  // devuelve el COUNT(*)
	        }
	    }finally {
			ConnectionManager.disconnect();
		}
	    return 0;
	}
	public int obtenerMaximoVehiculos() throws SQLException {
	    String sql = "SELECT MAX(codigo) FROM vehiculo";

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
