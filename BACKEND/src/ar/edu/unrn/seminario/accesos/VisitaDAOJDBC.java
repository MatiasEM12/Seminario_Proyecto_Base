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
import ar.edu.unrn.seminario.modelo.Visita;

public class VisitaDAOJDBC implements VisitaDao{

	@Override
	public void create(Visita visita) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO visita (fechaVisita, observaciones, tipo, ordenRetiro, bienesRecolectados)"
							+ " VALUES (?, ?, ?, ?, ?)");
			java.sql.Date fechaSQL = java.sql.Date.valueOf(visita.getFechaVisita());
			
			statement.setDate(1, fechaSQL);
			statement.setString(2, visita.getObservaciones());
			statement.setString(3, visita.getTipo());
			statement.setObject(4, visita.getRetiro());
			statement.setObject(5, visita.getBienesRecolectados());
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
	public void update(Visita visita) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE visita SET fechaVisita=?, observaciones=?, "
							+ "tipo=?, ordenRetiro=?, bienesRecolectados=? WHERE codigo=?)");
			java.sql.Date fechaSQL = java.sql.Date.valueOf(visita.getFechaVisita());
			
			statement.setDate(1, fechaSQL);
			statement.setString(2, visita.getObservaciones());
			statement.setString(3, visita.getTipo());
			statement.setObject(4, visita.getRetiro());
			statement.setObject(5, visita.getBienesRecolectados());
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
		            System.out.println("No se encontró el rol con ese código.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar Orden");
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
		            System.out.println("No se encontró el rol con ese código.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar Orden");
		}
	}

	@Override
	public Visita find(String codigo) throws DataNullException, DataLengthException {
	    Visita visita = null;

	    String sqlVisita =
	        "SELECT v.codigo, v.fechaVisita, v.observaciones, v.tipo, v.ordenRetiro " +
	        "FROM Visita v WHERE v.codigo = ?";

	    String sqlBienes =
	        "SELECT b.codigo, b.nombre, b.descripcion, b.fechaVencimiento, b.tipo " +
	        "FROM VisitaBien vb " +
	        "JOIN Bien b ON b.codigo = vb.codBien " +
	        "WHERE vb.codVisita = ?";

	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement stVisita = conn.prepareStatement(sqlVisita)) {

	        stVisita.setString(1, codigo);

	        try (ResultSet rs = stVisita.executeQuery()) {
	            if (rs.next()) {
	                // fechaVisita: DATE -> LocalDate
	                java.sql.Date sqlDate = rs.getDate("fechaVisita");
	                LocalDate fechaVisita = (sqlDate != null) ? sqlDate.toLocalDate() : null;

	                String observaciones = rs.getString("observaciones");
	                String tipo = rs.getString("tipo"); // si en BD es BOOLEAN, usa getBoolean(...)
	                String codOrdenRetiro = rs.getString("ordenRetiro");

	                // 2) Traer bienes asociados (objetos Bien)
	                ArrayList<Bien> bienesRecolectados = new ArrayList<>();
	                try (PreparedStatement stBienes = conn.prepareStatement(sqlBienes)) {
	                    stBienes.setString(1, codigo);
	                    try (ResultSet rs2 = stBienes.executeQuery()) {
	                        while (rs2.next()) {
	                            //String codBien = rs2.getString("codigo");
	                            String nombre = rs2.getString("nombre");
	                            String descripcion = rs2.getString("descripcion");
	                            java.sql.Date sqlDateVenc = rs.getDate("fechaVisita");
	        	                LocalDate fechaVenc = (sqlDateVenc != null) ? sqlDateVenc.toLocalDate() : null;
	                            String tipoBien = rs2.getString("tipo");

	                            // ✅ Usar un constructor para carga desde BD que NO genere código nuevo
	                            Bien bien = new Bien(nombre, descripcion, fechaVenc, tipoBien);
	                            bienesRecolectados.add(bien);

	                            // Alternativa si no querés nuevo constructor:
	                            // Bien bien = new Bien(nombre, descripcion, fechaVenc, tipoBien);
	                            // bien.setCodigo(codBien); // menos prolijo
	                        }
	                    }
	                }

	                visita = new Visita(
	                        fechaVisita,
	                        observaciones,
	                        tipo,
	                        codOrdenRetiro,          // mejor pasar FK en vez de objeto entero
	                        bienesRecolectados
	                );
	                visita.setCodigo(rs.getString("codigo"));
	            }
	        }

	    } catch (SQLException e) {
	        System.out.println("Error al procesar consulta: " + e.getMessage());
	        // TODO: lanzar tu excepción propia
	    } finally {
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

	    String sqlBienes =
	        "SELECT vb.codVisita, b.codigo, b.nombre, b.descripcion, b.fechaVencimiento, b.tipo " +
	        "FROM VisitaBien vb " +
	        "JOIN Bien b ON b.codigo = vb.codBien";

	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement stVisitas = conn.prepareStatement(sqlVisitas);
	         ResultSet rs = stVisitas.executeQuery()) {

	        // Índice por código para asociar bienes después y mantener el orden de inserción
	        Map<String, Visita> index = new LinkedHashMap<>();

	        // 1) Cargar todas las visitas (con lista vacía)
	        while (rs.next()) {
	            java.sql.Date sqlDate = rs.getDate("fechaVisita");
	            LocalDate fechaVisita = (sqlDate != null) ? sqlDate.toLocalDate() : null;

	            String observaciones = rs.getString("observaciones");
	            // Si 'tipo' fuera BOOLEAN en BD: boolean tipo = rs.getBoolean("tipo");
	            String tipo = rs.getString("tipo");
	            String codOrdenRetiro = rs.getString("ordenRetiro");

	            Visita v = new Visita(
	                fechaVisita,
	                observaciones,
	                tipo,
	                codOrdenRetiro,
	                new ArrayList<>()
	            );
	            v.setCodigo(rs.getString("codigo"));

	            visitas.add(v);
	            index.put(v.getCodigo(), v);
	        }

	        // 2) Si no hay visitas, listo
	        if (index.isEmpty()) {
	            return visitas;
	        }

	        // 3) Traer todos los bienes y agruparlos por visita
	        try (PreparedStatement stBienes = conn.prepareStatement(sqlBienes);
	             ResultSet rs2 = stBienes.executeQuery()) {

	            while (rs2.next()) {
	                String codVisita = rs2.getString("codVisita");
	                Visita v = index.get(codVisita);
	                if (v == null) continue; // por si hay basura referencial

	               // String codBien = rs2.getString("codigo");
	                String nombre = rs2.getString("nombre");
	                String descripcion = rs2.getString("descripcion");
	                java.sql.Date sqlDate = rs.getDate("fechaVisita");
	                LocalDate fechaVenc = (sqlDate != null) ? sqlDate.toLocalDate() : null;
	                String tipoBien = rs2.getString("tipo");

	                // ✔️ Constructor recomendado para carga desde BD (NO genera nuevo código)
	                Bien bien = new Bien(nombre, descripcion, fechaVenc, tipoBien);

	                // Si todavía no tenés ese constructor, alternativa (menos prolija):
	                // Bien bien = new Bien(nombre, descripcion, fechaVenc, tipoBien);
	                // bien.setCodigo(codBien);

	                v.getBienesRecolectados().add(bien);
	            }
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
