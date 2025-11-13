package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.modelo.Bien;

public class Bien_VisitaJDBC implements Bien_VisitaDAO{
	BienDAO bien;
	@Override
	public void create(String codBien, String codVisita) {

		try {
			
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO Bien_Visita(codBien,codVisita)"
							+ " VALUES (?, ?)");
			
			statement.setString(1, codBien);
			statement.setString(2, codVisita);
	
	
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
	public void update(String codBienNuevo,String codBienViejo, String codVisita) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE Bien_Visita SET codBien=?,codVisita=?  WHERE codVisita = ? AND codBien=?");
			
			statement.setString(1, codBienNuevo);
			statement.setString(2, codVisita);
			statement.setString(3, codVisita);
			statement.setString(4, codBienViejo);
			
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				 System.out.println("El Bien_ Visita se ha actualizado correctamente");
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
	public void remove(String codigoVisita) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Bien> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Bien> findVisita(String codVisita) {
		List<Bien> bienes = new ArrayList<>();
		
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT bv.codBien  "
					+ "FROM Bien_Visita bv, donacion d WHERE bv.codDonacion=? AND d.codigo=? AND  bv.codDonacion=d.codigo");
			
			sent.setString(1, codVisita);
			sent.setString(2, codVisita);
			ResultSet rs = sent.executeQuery();
			while (rs.next()) {
				
				bienes.add(bien.find(rs.getString("bv.codBien")));
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
