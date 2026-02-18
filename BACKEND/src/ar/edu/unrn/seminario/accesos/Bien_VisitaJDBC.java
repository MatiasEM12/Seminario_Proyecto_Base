package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.modelo.Bien;

public class Bien_VisitaJDBC implements Bien_VisitaDAO{
	BienDAO bien= new BienDAOJDBC();

	 @Override
	    public void create(String codBien, String codVisita) throws DAOException {

	        try {
	            Connection conn = ConnectionManager.getConnection();
	            PreparedStatement statement = conn.prepareStatement(
	                    "INSERT INTO Bien_Visita (codBien, codVisita) VALUES (?, ?)");

	            statement.setString(1, codBien);
	            statement.setString(2, codVisita);

	            int cantidad = statement.executeUpdate();
	            if (cantidad <= 0) {
	                throw new DAOException("Error al actualizar. codigo error BV100");
	            }

	        } catch (SQLException e) {
	            throw new DAOException("Error al procesar consulta. codigo error BV101"+e);
	        } finally {
	            ConnectionManager.disconnect();
	        }
	    }

	    @Override
	    public void update(String codBienNuevo, String codBienViejo, String codVisita) throws DAOException {

	        try {
	            Connection conn = ConnectionManager.getConnection();
	            PreparedStatement statement = conn.prepareStatement(
	                    "UPDATE Bien_Visita SET codBien=? WHERE codVisita=? AND codBien=?");

	            statement.setString(1, codBienNuevo);
	            statement.setString(2, codVisita);
	            statement.setString(3, codBienViejo);

	            int cantidad = statement.executeUpdate();
	            if (cantidad <= 0) {
	                throw new DAOException("Error al actualizar. codigo error BV200");
	            }

	        } catch (SQLException e) {
	            throw new DAOException("Error al procesar consulta. codigo error BV201"+ e);
	        } finally {
	            ConnectionManager.disconnect();
	        }
	    }

	    @Override
	    public List<Bien> findVisita(String codVisita) throws DAOException {

	        List<Bien> bienes = new ArrayList<>();

	        try {
	            Connection conn = ConnectionManager.getConnection();
	            PreparedStatement sent = conn.prepareStatement(
	                    "SELECT codBien FROM Bien_Visita WHERE codVisita = ?");

	            sent.setString(1, codVisita); // ✅ solo uno

	            ResultSet rs = sent.executeQuery();
	            while (rs.next()) {
	                bienes.add(bien.find(rs.getString("codBien"))); // ✅ nombre correcto
	            }

	        } catch (SQLException e) {
	            throw new DAOException("Error al procesar consulta. codigo error BV300"+ e);
	        } catch (Exception e) {
	            throw new DAOException("Error inesperado. codigo error BV301"+e);
	        } finally {
	            ConnectionManager.disconnect();
	        }

	        return bienes;
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



}
