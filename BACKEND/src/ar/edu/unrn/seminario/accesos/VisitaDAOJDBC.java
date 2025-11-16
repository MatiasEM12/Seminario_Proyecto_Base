package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.Coordenada;
import ar.edu.unrn.seminario.modelo.Visita;

public class VisitaDAOJDBC implements VisitaDao{
BienDAO biendao;
	@Override
	public void create(Visita visita) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO Visitas (fechaVisita,observaciones, tipo, codOrdenRetiro,codigo)"
							+ " VALUES (?, ?, ?, ?, ?, ?)");
			java.sql.Date fechaSQL = java.sql.Date.valueOf(visita.getFechaVisita());
			
			statement.setDate(1, fechaSQL);
			statement.setString(2, visita.getObservaciones());
			statement.setString(3, visita.getTipo());
			statement.setObject(4, visita.getRetiro());
			statement.setObject(5, visita.getCodigo());
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				// System.out.println("Modificando " + cantidad + " registros");
			} else {
				System.out.println("Error al actualizar");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
	        System.out.println("Error al procesar consulta (INSERT Visita): " + e.getMessage());
	    } finally {
	        ConnectionManager.disconnect();
	    }
		
	}

	@Override
	public void update(Visita visita) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE visita SET fechaVisita=?, observaciones=?, "
							+ "tipo=?, ordenRetiro=? WHERE codigo=?)");
			java.sql.Date fechaSQL = java.sql.Date.valueOf(visita.getFechaVisita());
			
			statement.setDate(1, fechaSQL);
			statement.setString(2, visita.getObservaciones());
			statement.setString(3, visita.getTipo());
			statement.setObject(4, visita.getRetiro());
			statement.setObject(5, visita.getCodigo());
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				// System.out.println("Modificando " + cantidad + " registros");
			} else {
				System.out.println("Error al actualizar");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
	        System.out.println("Error al procesar consulta (INSERT Visita): " + e.getMessage());
	    } finally {
	        ConnectionManager.disconnect();
	    }
		
	}

	@Override
	public void remove(String codigo) {
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM visita WHERE codigo = ?"
		        );

		        statement.setString(1, codigo);

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("Visita eliminada correctamente.");
		        } else {
		            System.out.println("No se encontró la visita con ese código.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar Visita");
		}
	}

	@Override
	public void remove(Visita visita) {
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM visita WHERE codigo = ?"
		        );

		        statement.setString(1, visita.getCodigo());

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("Visita eliminada correctamente.");
		        } else {
		            System.out.println("No se encontró la visitacon ese código.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar visita");
		}
	}

	@Override
	public Visita find(String codigo) throws DataNullException, DataLengthException {
	   
		Visita visita=null;
		
		
		try {
			Connection conn= ConnectionManager.getConnection();
			PreparedStatement sent = conn.prepareStatement("SELECT v.codigo, v.fechaVisita, v.observaciones, v.tipo, "
					+ "v.ordenRetiro " +
			        "FROM Visita v WHERE v.codigo = ?");
			sent.setString(1, codigo);
			ResultSet rs = sent.executeQuery();
			if (rs.next()) {
				java.sql.Date sqlDate = rs.getDate("v.fechaVisita");
				   java.time.LocalDate localDate = sqlDate.toLocalDate();
				
				visita=new Visita(localDate,rs.getString("v.observaciones"),rs.getString("v.tipo"),rs.getString("v.ordenRetiro")
						,biendao.findBienVisita("v.codigo"),rs.getString("v.codigo"));
				
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
		return visita;

	}



	@Override
	public List<Visita> findAll() throws DataNullException, DataLengthException {
	    List<Visita> visitas = new ArrayList<>();

	    String sqlVisitas =
	        "SELECT v.codigo, v.fechaVisita, v.observaciones, v.tipo, v.ordenRetiro " +
	        "FROM Visita v";


	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement stVisitas = conn.prepareStatement(sqlVisitas);
	         ResultSet rs = stVisitas.executeQuery()) {
	        while (rs.next()) {
	
	            Visita v = this.find("v.codigo"); 
	            visitas.add(v);
	           
	        }

	    } catch (SQLException e) {
	        System.out.println("Error al procesar consulta: " + e.getMessage());
	        // TODO: lanzar tu excepción propia (DataAccessException, etc.)
	    } finally {
	        ConnectionManager.disconnect();
	    }

	    return visitas;
	}

	@Override
	public ArrayList<Visita> findAll(String codOrdenRetiro) throws DataNullException, DataLengthException {
	    ArrayList<Visita> visitas = new ArrayList<>() ;


	    try {
	    	Connection conn = ConnectionManager.getConnection();
	         PreparedStatement stVisitas = conn.prepareStatement(  "SELECT v.codigo, v.fechaVisita, v.observaciones, v.tipo, v.ordenRetiro " +
	     	        "FROM Visita v OrdneRetiro or WHERE or.codigo =  ? AND	  or.codigo=v.codOrdenPedido" );
	    		stVisitas.setString(1,codOrdenRetiro);
	    		
	         ResultSet rs = stVisitas.executeQuery();
	        while (rs.next()) {
	
	            Visita v = this.find("v.codigo"); 
	            visitas.add(v);
	           
	        }

	    } catch (SQLException e) {
	        System.out.println("Error al procesar consulta: " + e.getMessage());
	        // TODO: lanzar tu excepción propia (DataAccessException, etc.)
	    } finally {
	        ConnectionManager.disconnect();
	    }

	    return visitas;
	}
}
