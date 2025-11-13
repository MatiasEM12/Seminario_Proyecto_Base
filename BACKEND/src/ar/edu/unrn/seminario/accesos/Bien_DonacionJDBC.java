package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.Coordenada;

public class Bien_DonacionJDBC implements Bien_DonacionDAO {
BienDAO bien;
	@Override
	public void create(String codBien, String codDonacion) {
		try {
			
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO Bien_Donacion(codBien,codDonacion)"
							+ " VALUES (?, ?)");
			
			statement.setString(1, codBien);
			statement.setString(2, codDonacion);
	
	
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
	public void update(String codBienNuevo,String codBienViejo, String codDonacion) {
try {
			
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE SET codBien=?, codDonacion=? FROM Bien_Donacion WHERE codBien=? AND codDonacion=?)"
							+ " VALUES (?, ?)");
			
			statement.setString(1, codBienNuevo);
			statement.setString(2, codDonacion);
			statement.setString(3, codBienViejo);
			statement.setString(4, codDonacion);
	
	
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				 System.out.println("El Bien_ Donacion se ha actualizado correctamente");
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
	public void remove(String codigoDonacion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Bien> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Bien> findDonacion(String codDonacion) {
		List<Bien> bienes = new ArrayList<>();
		
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT bd.codBien  "
					+ "FROM Bien_Donacion bd, donacion d WHERE bd.codDonacion=? AND d.codigo=? AND  bd.codDonacion=d.codigo");
			
			sent.setString(1, codDonacion);
			sent.setString(2, codDonacion);
			ResultSet rs = sent.executeQuery();
			while (rs.next()) {
				
				bienes.add(bien.find(rs.getString("bd.codBien")));
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
		return bienes;
	
	}
}
