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

import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.Orden;
import ar.edu.unrn.seminario.modelo.OrdenPedido;
import ar.edu.unrn.seminario.modelo.OrdenRetiro;
import ar.edu.unrn.seminario.modelo.Visita;

public class OrdenRetiroDAOJDBC implements OrdenRetiroDao{

	@Override
	public void create(OrdenRetiro orden) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO ordenRetiro (codigo, Fecha_Emision, codVoluntario,codOrdenPedido)"
							+ " VALUES (?, ?, ?,?)");
			
			java.sql.Date fechaSQL = java.sql.Date.valueOf(orden.getFechaEmision());
			
			statement.setDate(1, fechaSQL);
			
			
			statement.setObject(1,orden.getCodigo());
			statement.setDate(2, fechaSQL);
			statement.setString(3, orden.getVoluntario().getCodigo());
			statement.setString(4, orden.getPedido().getCodigo());
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
	public void update(OrdenRetiro orden) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(OrdenRetiro orden) {
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM OrdenRetiro WHERE codigo = ?"
		        );

		        statement.setString(1, orden.getCodigo());

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("Orden Retiro eliminado correctamente.");
		        } else {
		            System.out.println("No se encontró la Orden Retiro con ese código.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar Orden");
		}
		
	}

	@Override
	public void remove(String codigo) {
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM OrdenRetiro WHERE codigo = ?"
		        );

		        statement.setString(1,codigo);

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("Orden Retiro eliminado correctamente.");
		        } else {
		            System.out.println("No se encontró la Orden Retiro con ese código.");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar Orden");
		}
		
	}

	public OrdenRetiro find(String codigo) {
	    if (codigo == null || codigo.isBlank()) return null;

	    OrdenRetiro ordenRetiro = null;

	    final String SQL_ORDEN_RETIRO =
	        "SELECT o.codigo                       AS codRetiro, " +
	        "       o.fechaEmision                 AS fechaEmisionRetiro, " +
	        "       o.ordenPedido                  AS codOrdenPedido, " +
	        "       op.fechaEmision                AS fechaEmisionPedido, " +
	        "       op.cargaPesada, " +
	        "       op.observaciones, " +
	        "       op.codDonante, " +
	        "       op.codDonacion " +
	        "FROM   OrdenRetiro o " +
	        "JOIN   OrdenPedido op ON op.codigo = o.ordenPedido " +
	        "WHERE  o.codigo = ?";

	    final String SQL_VISITAS =
	        "SELECT v.codigo, v.fechaVisita, v.observaciones, v.tipo " +
	        "FROM   Visita v " +
	        "WHERE  v.ordenRetiro = ?";

	    final String SQL_BIENES_POR_VISITA =
	        "SELECT b.codigo, b.nombre, b.descripcion, b.fechaVencimiento, b.tipo " +
	        "FROM   VisitaBien vb " +
	        "JOIN   Bien b ON b.codigo = vb.codBien " +
	        "WHERE  vb.codVisita = ?";

	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement stOrden = conn.prepareStatement(SQL_ORDEN_RETIRO)) {

	        stOrden.setString(1, codigo);

	        try (ResultSet rs = stOrden.executeQuery()) {
	            if (!rs.next()) {
	                // No existe la OrdenRetiro con ese código
	                return null;
	            }

	            // --- Cabecera: OrdenPedido + OrdenRetiro ---
	            String codRetiro         = rs.getString("codRetiro");
	            String codOrdenPedido    = rs.getString("codOrdenPedido");

	            // fechaEmisionRetiro podría ser DATE o TIMESTAMP en tu BD; contemplamos ambas
	            LocalDate fechaEmisionRetiro;
	            java.sql.Date dRetiro = rs.getDate("fechaEmisionRetiro");
	            if (dRetiro != null) {
	                fechaEmisionRetiro = dRetiro.toLocalDate();
	            } else {
	                java.sql.Timestamp tsRetiro = rs.getTimestamp("fechaEmisionRetiro");
	                fechaEmisionRetiro = (tsRetiro != null) ? tsRetiro.toLocalDateTime().toLocalDate() : null;
	            }

	            // OrdenPedido requiere LocalDateTime según tu firma anterior
	            java.sql.Date sqlDate = rs.getDate("fechaEmisionPedido");
                LocalDate fechaEmisionPedido = (sqlDate != null) ? sqlDate.toLocalDate() : null;
	            

	            boolean cargaPesada      = rs.getBoolean("cargaPesada");
	            String observacionesOP   = rs.getString("observaciones");
	            String codDonante        = rs.getString("codDonante");
	            String codDonacion       = rs.getString("codDonacion");

	            // Construimos OrdenPedido (y le seteamos el código leído de BD para NO generar uno nuevo)
	            OrdenPedido ordenPedido = new OrdenPedido(
	                    fechaEmisionPedido,
	                    cargaPesada,
	                    observacionesOP,
	                    codDonante,
	                    codDonacion
	            );
	            
	            ordenPedido.setCodigo(codOrdenPedido); // <-- IMPORTANTE: evitar crear un código nuevo
	            
	            // Construimos OrdenRetiro base
	            ordenRetiro = new OrdenRetiro(fechaEmisionRetiro, ordenPedido, /*visitas*/ null);
	           // ordenRetiro.setCodigo(codRetiro); // si tu clase tiene el setter

	            // --- Detalle: Visitas de la OrdenRetiro ---
	            ArrayList<Visita> visitas = new ArrayList<>();
	            try (PreparedStatement stVisitas = conn.prepareStatement(SQL_VISITAS)) {
	                stVisitas.setString(1, codRetiro);
	                try (ResultSet rsVis = stVisitas.executeQuery()) {
	                    while (rsVis.next()) {
	                        String codVisita = rsVis.getString("codigo");

	                        java.sql.Date dVis = rsVis.getDate("fechaVisita");
	                        LocalDate fechaVisita = (dVis != null) ? dVis.toLocalDate() : null;

	                        String observacionesV = rsVis.getString("observaciones");
	                        String tipoV          = rsVis.getString("tipo");

	                        // --- Bienes de la visita ---
	                        ArrayList<Bien> bienesRecolectados = new ArrayList<>();
	                        try (PreparedStatement stBienes = conn.prepareStatement(SQL_BIENES_POR_VISITA)) {
	                            stBienes.setString(1, codVisita);
	                            try (ResultSet rsB = stBienes.executeQuery()) {
	                                while (rsB.next()) {
	                                    String codBien      = rsB.getString("codigo");
	                                    String nombre       = rsB.getString("nombre");
	                                    String descripcion  = rsB.getString("descripcion");
	                                    java.sql.Date sqlDateVenc = rs.getDate("fechaVisita");
	                	                LocalDate fechaVenc = (sqlDate != null) ? sqlDate.toLocalDate() : null;
	                                    String tipoBien     = rsB.getString("tipo");

	                                    Bien bien = new Bien(nombre, descripcion, fechaVenc, tipoBien);
	                                    // Si tu clase Bien necesita conservar el código de BD:
	                                    // bien.setCodigo(codBien);

	                                    bienesRecolectados.add(bien);
	                                }
	                            }
	                        }

	                        // Según tu firma previa:
	                        // Visita(LocalDate fechaVisita, String observaciones, String tipo, OrdenRetiro retiro, ArrayList<Bien> bienesRecolectados)
	                        Visita visita = new Visita(fechaVisita, observacionesV, tipoV, codRetiro, bienesRecolectados);
	                        // Si tu clase Visita tiene código y setter:
	                        // visita.setCodigo(codVisita);

	                        visitas.add(visita);
	                    }
	                }
	            }

	            // Asociamos las visitas a la orden
	            // Si tu OrdenRetiro las recibe en el constructor, puedes pasar 'visitas' allí en vez de null arriba.
	            ordenRetiro.setVisitas(visitas); // ajusta si tu clase usa otro método/campo
	        }

	    } catch (Exception ex) {
	        ex.printStackTrace();
	        // según tu política, puedes relanzar como unchecked o retornar null
	        return null;
	    }

	    return ordenRetiro;
	}


	@Override
	public List<OrdenRetiro> findAll() {
	    List<OrdenRetiro> resultados = new ArrayList<>();

	    final String SQL_ORDENES =
	        "SELECT o.codigo              AS codRetiro, " +
	        "       o.fechaEmision        AS fechaEmisionRetiro, " +
	        "       o.ordenPedido         AS codOrdenPedido, " +
	        "       op.fechaEmision       AS fechaEmisionPedido, " +
	        "       op.cargaPesada, " +
	        "       op.observaciones, " +
	        "       op.codDonante, " +
	        "       op.codDonacion " +
	        "FROM   OrdenRetiro o " +
	        "JOIN   OrdenPedido op ON op.codigo = o.ordenPedido " +
	        "ORDER BY o.fechaEmision DESC";

	    final String SQL_VISITAS =
	        "SELECT v.codigo, v.fechaVisita, v.observaciones, v.tipo " +
	        "FROM   Visita v " +
	        "WHERE  v.ordenRetiro = ? " +
	        "ORDER BY v.fechaVisita ASC";

	    final String SQL_BIENES_POR_VISITA =
	        "SELECT b.codigo, b.nombre, b.descripcion, b.fechaVencimiento, b.tipo " +
	        "FROM   VisitaBien vb " +
	        "JOIN   Bien b ON b.codigo = vb.codBien " +
	        "WHERE  vb.codVisita = ?";

	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement stOrdenes = conn.prepareStatement(SQL_ORDENES)) {

	        try (ResultSet rs = stOrdenes.executeQuery()) {
	            while (rs.next()) {
	                // --- Cabecera OrdenRetiro + OrdenPedido ---
	                String codRetiro       = rs.getString("codRetiro");
	                String codOrdenPedido  = rs.getString("codOrdenPedido");

	                // fechaEmisionRetiro: DATE o TIMESTAMP -> LocalDate
	                LocalDate fechaEmisionRetiro;
	                java.sql.Date dRet = rs.getDate("fechaEmisionRetiro");
	                if (dRet != null) {
	                    fechaEmisionRetiro = dRet.toLocalDate();
	                } else {
	                    java.sql.Timestamp tsRet = rs.getTimestamp("fechaEmisionRetiro");
	                    fechaEmisionRetiro = (tsRet != null) ? tsRet.toLocalDateTime().toLocalDate() : null;
	                }

	                // En tu versión actual estás usando LocalDate para OrdenPedido
	                java.sql.Date dPed = rs.getDate("fechaEmisionPedido");
	                LocalDate fechaEmisionPedido = (dPed != null) ? dPed.toLocalDate() : null;

	                boolean cargaPesada    = rs.getBoolean("cargaPesada");
	                String observacionesOP = rs.getString("observaciones");
	                String codDonante      = rs.getString("codDonante");
	                String codDonacion     = rs.getString("codDonacion");

	                OrdenPedido ordenPedido = new OrdenPedido(
	                        /* LocalDate */ fechaEmisionPedido,
	                        cargaPesada,
	                        observacionesOP,
	                        codDonante,
	                        codDonacion
	                );
	                // Evitar generar nuevo código
	                ordenPedido.setCodigo(codOrdenPedido);

	                OrdenRetiro ordenRetiro = new OrdenRetiro(fechaEmisionRetiro, ordenPedido, /*visitas*/ null);
	                // Si tu clase expone el setter de código, podés conservarlo:
	                // ordenRetiro.setCodigo(codRetiro);

	                // --- Visitas de la orden ---
	                ArrayList<Visita> visitas = new ArrayList<>();
	                try (PreparedStatement stVisitas = conn.prepareStatement(SQL_VISITAS)) {
	                    stVisitas.setString(1, codRetiro);
	                    try (ResultSet rsVis = stVisitas.executeQuery()) {
	                        while (rsVis.next()) {
	                            String codVisita = rsVis.getString("codigo");

	                            java.sql.Date dVis = rsVis.getDate("fechaVisita");
	                            LocalDate fechaVisita = (dVis != null) ? dVis.toLocalDate() : null;

	                            String observacionesV = rsVis.getString("observaciones");
	                            String tipoV          = rsVis.getString("tipo");

	                            // --- Bienes por visita ---
	                            ArrayList<Bien> bienesRecolectados = new ArrayList<>();
	                            try (PreparedStatement stBienes = conn.prepareStatement(SQL_BIENES_POR_VISITA)) {
	                                stBienes.setString(1, codVisita);
	                                try (ResultSet rsB = stBienes.executeQuery()) {
	                                    while (rsB.next()) {
	                                        String codBien     = rsB.getString("codigo");
	                                        String nombre      = rsB.getString("nombre");
	                                        String descripcion = rsB.getString("descripcion");
	                                        java.sql.Date sqlDate = rs.getDate("fechaVisita");
	                    	                LocalDate fechaVenc = (sqlDate != null) ? sqlDate.toLocalDate() : null;
	                                        String tipoBien    = rsB.getString("tipo");

	                                        Bien bien = new Bien(nombre, descripcion, fechaVenc, tipoBien);
	                                        // Si querés conservar el código del bien:
	                                        // bien.setCodigo(codBien);

	                                        bienesRecolectados.add(bien);
	                                    }
	                                }
	                            }

	                            // Usás este constructor en tu código actual: (fecha, obs, tipo, codRetiro, bienes)
	                            Visita visita = new Visita(fechaVisita, observacionesV, tipoV, codRetiro, bienesRecolectados);
	                            // Si tu clase tiene código:
	                            // visita.setCodigo(codVisita);

	                            visitas.add(visita);
	                        }
	                    }
	                }

	                // Asociar visitas
	                ordenRetiro.setVisitas(visitas);

	                resultados.add(ordenRetiro);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        // Política simple: devolver lo acumulado o null; aquí devuelvo lista (posiblemente vacía).
	    }

	    return resultados;
	}


}
