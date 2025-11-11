package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
	public void remove(Ubicacion ubicacion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Ubicacion find(String codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ubicacion> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
