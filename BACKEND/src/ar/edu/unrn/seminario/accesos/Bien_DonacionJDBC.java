package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DataDoubleException;
import ar.edu.unrn.seminario.exception.DataNullException;
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
	public List<Bien> findDonacion(String codDonacion) throws DataNullException, DataDoubleException {
		 ArrayList<Bien> resultado = new ArrayList<>();
		    if (codDonacion == null) return resultado;

		    Connection conn = null;
		    PreparedStatement ps = null;
		    ResultSet rs = null;
		    try {
		        conn = ConnectionManager.getConnection();
		        String sql = "SELECT b.codigo, b.tipo, b.nombre, b.peso, b.descripcion, b.nivelNecesidad, b.fechaVencimiento, b.talle, b.material " +
		                     "FROM bien b " +
		                     "JOIN Bien_Donacion bd ON b.codigo = bd.codBien " +
		                     "WHERE bd.codDonacion = ?";
		        ps = conn.prepareStatement(sql);
		        ps.setString(1, codDonacion);
		        rs = ps.executeQuery();
		        while (rs.next()) {
		            Bien bien = new Bien(
		                rs.getString("codigo"),
		                rs.getString("tipo"),
		                rs.getDouble("peso"),
		                rs.getString("nombre"),
		                rs.getString("descripcion"),
		                rs.getInt("nivelNecesidad"),
		                rs.getDate("fechaVencimiento") != null ? rs.getDate("fechaVencimiento").toLocalDate() : null,
		                rs.getObject("talle") != null ? rs.getDouble("talle") : null,
		                rs.getString("material")
		            );
		            resultado.add(bien);
		        }
		    } catch (SQLException e) {
		        System.out.println("Error en findBienDonacion: " + e.getMessage());
		    } finally {
		        try { if (rs != null) rs.close(); } catch (SQLException ex) {}
		        try { if (ps != null) ps.close(); } catch (SQLException ex) {}
		        ConnectionManager.disconnect();
		    }
		    return resultado;
	}
}
