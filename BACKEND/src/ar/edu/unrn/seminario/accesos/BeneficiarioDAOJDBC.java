package ar.edu.unrn.seminario.accesos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Beneficiario;

public class BeneficiarioDAOJDBC implements BeneficiarioDAO{
	@Override
	public void create(Beneficiario beneficiario) throws DAOException{
		
		try {
			
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO coordenada(codigo,Latitud,Longitud)"
							+ " VALUES (?, ?, ?)");
			
			statement.setString(1, beneficiario.getCodigo());
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
	public void update(Beneficiario beneficiario) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String codigo) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Beneficiario beneficiario) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Beneficiario find(String codigo) throws DataNullException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Beneficiario> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Beneficiario> findBienDonacion(String codDonacion) throws DataNullException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Beneficiario> findBienVisita(String codVisita) throws DataNullException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Beneficiario> findALLTipo(String tipo) throws DataNullException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}
}
