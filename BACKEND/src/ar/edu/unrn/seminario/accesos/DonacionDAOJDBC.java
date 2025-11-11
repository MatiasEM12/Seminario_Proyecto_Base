package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.sql.Date;
import java.time.LocalDate;

import ar.edu.unrn.seminario.modelo.Donacion;

public class DonacionDAOJDBC implements DonacionDAO{


	
	@Override
	public void create(Donacion donacion) {
	try {
			
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO donacion(codigo,observacion,Fecha_Donacion,codigoDonante,codigoOrdenPedido)"
							+ " VALUES (?, ?, ?,?,?)");
			
		
			

			java.sql.Date fechaSQL = java.sql.Date.valueOf(donacion.getFechaDonacion());
			
			statement.setString(1, donacion.getCodigo());
			statement.setString(2, donacion.getObservacion());
			statement.setDate(3, fechaSQL);
			statement.setString(4, donacion.getCod_Donante());
			statement.setString(5, donacion.getCod_Pedido());
			
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
	public void update(Donacion donacion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String codigo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Donacion donacion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Donacion find(String codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Donacion> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
