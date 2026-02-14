   package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataLengthException;
import ar.edu.unrn.seminario.exception.DataListException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;
import ar.edu.unrn.seminario.exception.StateChangeException;
import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.Coordenada;
import ar.edu.unrn.seminario.modelo.Donacion;
import ar.edu.unrn.seminario.modelo.Donante;
import ar.edu.unrn.seminario.modelo.OrdenPedido;
import ar.edu.unrn.seminario.modelo.Rol;

public class DonacionDAOJDBC implements DonacionDAO{

DonanteDao d= new DonanteDAOJDBC();
OrdenPedidoDao op= new OrdenPedidoDAOJDBC();
BienDAO  b = new BienDAOJDBC();


	@Override
	public void create(Donacion donacion) throws DataNullException {
	    Connection conn = null;
	    PreparedStatement statement = null;

	    try {

	        conn = ConnectionManager.getConnection();

	        // Si la donación viene sin código, lo generamos a partir de la BD
	        String codigo = donacion.getCodigo();
	        if (codigo == null || codigo.trim().isEmpty()) {
	            codigo = generarNuevoCodigo(conn);
	        }

	        statement = conn.prepareStatement(
	                "INSERT INTO donacion(" +
	                        "codigo, observacion, Fecha_Donacion, codigoDonante, codigoOrdenPedido" +
	                ") VALUES (?, ?, ?, ?, ?)"
	        );

	    	 conn = ConnectionManager.getConnection();
		        statement = conn.prepareStatement(
		            "INSERT INTO donacion(" +
		                "codigo, observacion, Fecha_Donacion, codigoDonante, codigoOrdenPedido" +
		            ") VALUES (?, ?, ?, ?, ?)"
		        );


		        LocalDate fecha = donacion.getFechaDonacion();
		        java.sql.Date fechaSQL = java.sql.Date.valueOf(fecha);

	        statement.setString(1, codigo);
	        statement.setString(2, donacion.getObservacion());
	        statement.setDate(3, fechaSQL);

		        statement.setString(1, donacion.getCodigo());
		        statement.setString(2, donacion.getObservacion());
		        statement.setDate(3, fechaSQL);

		        Donante donante = donacion.getDonante();
		        if (donante == null) {
		            throw new DataNullException("La donación no tiene donante asociado. codigo error D100 ");
		        }
		        statement.setString(4, donante.getCodigo());


	        if (donacion.getPedido() != null) {
	            statement.setString(5, donacion.getPedido().getCodigo());
	        } else {
	            statement.setNull(5, java.sql.Types.VARCHAR);
	        }

	        int cantidad = statement.executeUpdate();
	        if (cantidad > 0) {
	            System.out.println("INSERT Donacion OK - codigo=" + codigo +
	                               ", codDonante=" + donante.getCodigo());
	        } else {
	            System.out.println("Error al insertar Donacion (executeUpdate devolvió 0). codigo error D101");
	        }

		        if (donacion.getPedido() != null) {
		            statement.setString(5, donacion.getPedido().getCodigo());
		        } else {
		            statement.setNull(5, java.sql.Types.VARCHAR);
		        }

	    } catch (SQLException e) {
	        System.out.println("Error al procesar consulta (INSERT Donacion): " + e.getMessage()+". codigo error D102");
	        throw new RuntimeException(e);
	    } finally {
	    	 try { if (statement != null) statement.close(); } catch (SQLException ex) {}
	        ConnectionManager.disconnect();
	    }
	}




	@Override
	public void update(Donacion donacion) {
		try {

			Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn
					.prepareStatement("UPDATE donacion SET observacion=?,Fecha_Donacion=?,codigoDonante=?,codigoOrdenPedido=? "
							+ "WHERE codigo = ?");

			LocalDate fecha = donacion.getFechaDonacion();
			java.sql.Date fechaSQL = java.sql.Date.valueOf(fecha);
			
			
			statement.setString(1, donacion.getObservacion());
			statement.setDate(2, fechaSQL);
			statement.setString(3, donacion.getDonante() != null ? donacion.getDonante().getCodigo() : null);
			statement.setString(4,  donacion.getPedido() != null ? donacion.getPedido().getCodigo() : null);
			statement.setString(5, donacion.getCodigo());
			
			int cantidad = statement.executeUpdate();
			if (cantidad > 0) {
				 System.out.println("La Donacion se ha actualizado correctamente");
			} else {
				System.out.println("Error al actualizar. codigo error D200");
				// TODO: disparar Exception propia
			}

		} catch (SQLException e) {
			System.out.println("Error al procesar consulta. codigo error D201");
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
	public void remove(String codigo) {
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM donacion WHERE codigo = ? "
		        );

		        statement.setString(1, codigo);

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("Donacion eliminada correctamente.");
		        } else {
		            System.out.println("No se encontró la donacion. codigo error D300");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar donacion. codigo error D301");
		}finally {
			ConnectionManager.disconnect();
		}
		
	}

	@Override
	public void remove(Donacion donacion) {
		try {
			 Connection conn = ConnectionManager.getConnection();
		        PreparedStatement statement = conn.prepareStatement(
		            "DELETE FROM donacion WHERE codigo = ? "
		        );

		        statement.setString(1, donacion.getCodigo());

		        int cantidad = statement.executeUpdate();
		        if (cantidad > 0) {
		            System.out.println("Donacion eliminada correctamente.");
		        } else {
		            System.out.println("No se encontró la donacion. codigo error D400");
		        }
			
		}catch(SQLException e) {
			System.out.println("Error al Eliminar donacion. codigo error D401");
		}finally {
			ConnectionManager.disconnect();
		}
		
		
	}

	@Override
	public Donacion find(String codigo) {
	    Donacion donacion = null;

	    try {
	        Connection conn = ConnectionManager.getConnection();
	        PreparedStatement sent = conn.prepareStatement(
	            "SELECT codigo, observacion, Fecha_Donacion, codigoDonante, codigoOrdenPedido " +
	            "FROM donacion WHERE codigo = ?"
	        );

	        sent.setString(1, codigo);
	        ResultSet rs = sent.executeQuery();

	        if (rs.next()) {

	            LocalDate fecha = rs.getDate("Fecha_Donacion").toLocalDate();

	            String codOrdenPedido = rs.getString("codigoOrdenPedido");

	           
	            OrdenPedido ordenPedido = null;
	            if (codOrdenPedido != null) {
	            	  donacion = new Donacion(
	      	                fecha,
	      	                rs.getString("observacion"),
	      	                b.findBienDonacion(rs.getString("codigo")),
	      	                d.find(rs.getString("codigoDonante")),
	      	                this.op.find(codOrdenPedido),                   
	      	                rs.getString("codigo")
	      	            );
	            }else {
	            	 donacion = new Donacion(
		      	                fecha,
		      	                rs.getString("observacion"),
		      	                b.findBienDonacion(rs.getString("codigo")),
		      	                d.find(rs.getString("codigoDonante")),                
		      	                rs.getString("codigo")
		      	                );
	            }

	          
	        }

	    } catch (SQLException e) {
	        System.out.println("Error al procesar consulta " + e.getMessage() + ". codigo error D500");
	    } catch (Exception e) {
	        System.out.println("Error al construir Donacion " + e.getMessage());
	    } finally {
	        ConnectionManager.disconnect();
	    }

	    return donacion;
	}


	
	@Override
	public List<Donacion> findAll() throws DataNullException, DataEmptyException,
	        DataObjectException, DataDateException, DAOException,
	        DataLengthException, DataListException {

	    List<Donacion> listado = new ArrayList<>();

	    String sql = "SELECT codigo, observacion, Fecha_Donacion, codigoDonante, codigoOrdenPedido FROM donacion";

	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {

	            String codigo = rs.getString("codigo");
	            String observacion = rs.getString("observacion");
	            LocalDate fecha = rs.getDate("Fecha_Donacion").toLocalDate();

	            String codDonante = rs.getString("codigoDonante");
	            String codOrdenPedido = rs.getString("codigoOrdenPedido");

	            Donante donante = (codDonante != null) ? d.find(codDonante) : null;
	            OrdenPedido pedido = (codOrdenPedido != null) ? op.find(codOrdenPedido) : null;

	            ArrayList<Bien> bienes;
	            try {
	                bienes = b.findBienDonacion(rs.getString("codigo"));
	            } catch (Exception e) {
	                bienes = new ArrayList<>();
	            }
	            OrdenPedido ordenPedido = null;
	            Donacion donacion = null;
	            if (codOrdenPedido != null) {
	            	  donacion = new Donacion(
	      	                fecha,
	      	                rs.getString("observacion"),
	      	                b.findBienDonacion(rs.getString("codigo")),
	      	                d.find(rs.getString("codigoDonante")),
	      	                this.op.find(codOrdenPedido),                   
	      	                rs.getString("codigo")
	      	            );
	            }else {
	            	 donacion = new Donacion(
		      	                fecha,
		      	                rs.getString("observacion"),
		      	                b.findBienDonacion(rs.getString("codigo")),
		      	                d.find(rs.getString("codigoDonante")),                
		      	                rs.getString("codigo")
		      	                );
	            }
	            listado.add(donacion);
	        }

	    } catch (SQLException e) {
	        throw new DAOException("Error MySQL. codigo error D600" + e);
	    }

	    return listado;
	}

	

	@Override
	public List<Donacion> findAllPendiente() throws DataNullException,
	        DataEmptyException, DataObjectException, DataDateException,
	        DAOException, DataLengthException, DataListException {

	    List<Donacion> listado = new ArrayList<>();

	    String sql = "SELECT codigo, observacion, Fecha_Donacion, codigoDonante  FROM donacion WHERE codigoOrdenPedido IS NULL";

	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {

	            String codigo = rs.getString("codigo");
	            String observacion = rs.getString("observacion");
	            LocalDate fecha = rs.getDate("Fecha_Donacion").toLocalDate();

	            String codDonante = rs.getString("codigoDonante");
	            Donante donante = (codDonante != null) ? d.find(codDonante) : null;

	            ArrayList<Bien> bienes;
	            try {
	                bienes = b.findBienDonacion(rs.getString("codigo"));
	            } catch (Exception e) {
	                bienes = new ArrayList<>();
	            }
	            Donacion donacion = new Donacion(
	                    fecha,
	                    observacion,
	                    bienes,
	                    donante,
	                    codigo
	            );

	            listado.add(donacion);
	        }

	    } catch (SQLException e) {
	        throw new DAOException("Error MySQL. codigo error D700" + e);
	    }

	    return listado;
	}


	
	@Override
	public Donacion findPorOrdenPedido(String codigoOrdenPedido) throws DataNullException, DAOException, DataEmptyException, DataObjectException, DataDateException, DataLengthException, DataListException {

	    if (codigoOrdenPedido == null || codigoOrdenPedido.isBlank()) {
	        throw new DataNullException("Código de orden pedido inválido");
	    }

	    Donacion donacion = null;

	    String sql = "SELECT codigo, observacion, Fecha_Donacion, codigoDonante FROM donacion WHERE codigoOrdenPedido = ?";

	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, codigoOrdenPedido);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {

	            String codigo = rs.getString("codigo");
	            LocalDate fecha = rs.getDate("Fecha_Donacion").toLocalDate();

	            String codDonante = rs.getString("codigoDonante");

	            Donante donante = (codDonante != null) ? d.find(codDonante) : null;
	            OrdenPedido pedido = op.find(codigoOrdenPedido);

	            ArrayList<Bien> bienes;
	            try {
	                bienes = b.findBienDonacion(rs.getString("codigo"));
	            } catch (Exception e) {
	                bienes = new ArrayList<>();
	            }
	            donacion = new Donacion(
	                    fecha,
	                    rs.getString("observacion"),
	                    bienes,
	                    donante,
	                    pedido,
	                    codigo
	            );
	        }

	    } catch (SQLException e) {
	        throw new DAOException("Error MySQL. codigo error D800" + e);
	    }

	    return donacion;
	}

	
	private String generarNuevoCodigo(Connection conn) throws SQLException {
	    String ultimoCodigo = null;
	    String sql = "SELECT codigo FROM donacion ORDER BY codigo DESC LIMIT 1";

	    try (PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        if (rs.next()) {
	            ultimoCodigo = rs.getString(1); // ej: "DN00001"
	        }
	    }

	    int siguienteNumero = 1;
	    if (ultimoCodigo != null && ultimoCodigo.startsWith("DN")) {
	        // Tomo la parte numérica después del prefijo "DN"
	        String parteNumerica = ultimoCodigo.substring(2); // "00001"
	        try {
	            int valor = Integer.parseInt(parteNumerica);
	            siguienteNumero = valor + 1;
	        } catch (NumberFormatException e) {
	            // Si por alguna razón no es número, dejamos siguienteNumero en 1
	            siguienteNumero = 1;
	        }
	    }

	    // Formato DN00001, DN00002, etc.
	    return String.format("DN%05d", siguienteNumero);
	}

	public int obtenerCantidadDonaciones() throws SQLException {
	    String sql = "SELECT COUNT(*) FROM donacion";

	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        if (rs.next()) {
	            return rs.getInt(1);  // devuelve el COUNT(*)
	        }
	    }finally {
			ConnectionManager.disconnect();
		}
	    return 0;
	}
	public int obtenerMaximoDonaciones() throws SQLException {
	    String sql = "SELECT MAX(codigo) FROM donacion";

	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        if (rs.next()) {
	            String maxCodigo = rs.getString(1);

	            if (maxCodigo != null) {
	            
	                return Integer.parseInt(maxCodigo.substring(2));
	            }
	        }
	    } finally {
	        ConnectionManager.disconnect();
	    }
	    return 0;
	}


}
