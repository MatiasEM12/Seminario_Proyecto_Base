package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;
import ar.edu.unrn.seminario.modelo.Coordenada;
import ar.edu.unrn.seminario.modelo.Donante;
import ar.edu.unrn.seminario.modelo.Ubicacion;

public class DonanteDAOJDBC implements DonanteDao {

    UbicacionDAO u = new UbicacionDAOJDBC();

    @Override
    public void create(Donante donante) {
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO donante(codigo, nombre, apellido, dni, contacto, Fecha_Nacimiento, username, codUbicacion, activo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );

            java.sql.Date fechaSQL = java.sql.Date.valueOf(donante.getFecha_nac());

            // username y ubicacion por defecto si vienen null
            String username = donante.getUsername();
            if (username == null || username.trim().isEmpty()) {
                // por ejemplo: usar el mismo código o el dni
                username = "user_" + donante.getCodigo();
            }

            String codUbicacion = (donante.getUbicacion() != null)
                    ? donante.getUbicacion().getCodigo()
                    : "UB01"; // algún código válido que tengas en la tabla ubicacion

            statement.setString(1, donante.getCodigo());
            statement.setString(2, donante.getNombre());
            statement.setString(3, donante.getApellido());
            statement.setString(4, donante.getDni());
            statement.setString(5, donante.getContacto());
            statement.setDate(6, fechaSQL);
            statement.setString(7, username);
            statement.setString(8, codUbicacion);
            statement.setInt(9, 1); // activo = true

            int cantidad = statement.executeUpdate();
            if (cantidad <= 0) {
                System.out.println("Error al insertar donante");
            }

        } catch (SQLException e) {
            System.out.println("Error al procesar consulta (INSERT donante): " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionManager.disconnect();
        }
    }


    @Override
    public void update(Donante donante) {
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                "UPDATE donante SET nombre = ?, apellido = ?, dni = ?, contacto = ?, " +
                "Fecha_Nacimiento = ?, username = ?, codUbicacion = ?, activo = ? " +
                "WHERE codigo = ?"
            );

            java.sql.Date fechaSQL = java.sql.Date.valueOf(donante.getFecha_nac());

            statement.setString(1, donante.getNombre());
            statement.setString(2, donante.getApellido());
            statement.setString(3, donante.getDni());
            statement.setString(4, donante.getContacto());
            statement.setDate(5, fechaSQL);
            statement.setString(6, donante.getUsername());
            statement.setString(7, donante.getUbicacion().getCodigo());
            statement.setBoolean(8, true); // o donante.isActivo()
            statement.setString(9, donante.getCodigo());

            int cantidad = statement.executeUpdate();
            if (cantidad > 0) {
                System.out.println("El Donante se ha actualizado correctamente");
            } else {
                System.out.println("No se encontró el donante a actualizar");
            }

        } catch (SQLException e) {
            System.out.println("Error al procesar consulta (UPDATE donante): " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }
    }


	public void remove(String codigo) {
		try {
			 Connection conn = ConnectionManager.getConnection();
			  PreparedStatement statement1 = conn.prepareStatement(
			            "SELECT codUbicacion FROM donante WHERE codigo = ?"
			        );
			 
			  statement1.setString(1, codigo);
			  ResultSet rs = statement1.executeQuery();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM donante WHERE codigo = ?"
		        );

		        statement.setString(1, codigo);
		        
		        u.remove(rs.getString("codUbicacion"));
		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("Donante eliminado correctamente.");
		        } else {
		            System.out.println("No se encontró al donante.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar donanre");
		}
		
	}

	public void remove(Donante donante) {
		try {
			
			 Connection conn = ConnectionManager.getConnection();
			  PreparedStatement statement1 = conn.prepareStatement(
			            "SELECT codUbicacion FROM donante WHERE username = ?"
			        );
			  statement1.setString(1, donante.getUsername());
			  ResultSet rs = statement1.executeQuery();
			  
			 Connection conn2 = ConnectionManager.getConnection();
		        PreparedStatement statement = conn2.prepareStatement(
		            "DELETE FROM donante WHERE codigo = ? "
		        );

		        ;
		        statement.setString( 1,donante.getCodigo());
		        u.remove(rs.getString("codUbicacion"));
		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("donante eliminado correctamente.");
		        } else {
		            System.out.println("No se encontró el donante.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar donante");
		}
	}

   
    public Donante find(String codigo) throws DataEmptyException, DataObjectException, DataNullException, DataDateException {
        Donante donante = null;
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement sent = conn.prepareStatement(
                "SELECT codigo, nombre, apellido, dni, contacto, Fecha_Nacimiento, username, codUbicacion, activo " +
                "FROM donante WHERE codigo = ?"
            );
            sent.setString(1, codigo);
            ResultSet rs = sent.executeQuery();

            if (rs.next()) {
                java.sql.Date sqlDate = rs.getDate("Fecha_Nacimiento");
                LocalDate fechaNac = sqlDate.toLocalDate();

                String codUbicacion = rs.getString("codUbicacion");
                Ubicacion ubic = u.find(codUbicacion); // si tu UbicacionDAO tiene find

                donante = new Donante(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        fechaNac,
                        rs.getString("dni"),
                        rs.getString("contacto"),
                        ubic
                );
                // si tu modelo tiene setCodigo / setUsername / setActivo los seteás acá
            }

        } catch (SQLException e) {
            System.out.println("Error al procesar consulta (SELECT donante): " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }
        return donante;
    }

    @Override
    public List<Donante> findAll() throws DataEmptyException, DataObjectException, DataNullException, DataDateException {
        List<Donante> donantes = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement sent = conn.prepareStatement(
                "SELECT codigo FROM donante"
            );
            ResultSet rs = sent.executeQuery();
            while (rs.next()) {
                donantes.add(this.find(rs.getString("codigo")));
            }
        } catch (SQLException e) {
            System.out.println("Error al procesar consulta (SELECT ALL donante): " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }
        return donantes;
    }


	
	@Override
	public void remove(Long id) {
		// TODO Auto-generated method stub
		
	}

	
}