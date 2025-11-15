package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;



import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.Donacion;
import ar.edu.unrn.seminario.modelo.Donante;
import ar.edu.unrn.seminario.modelo.OrdenPedido;

public class DonacionDAOJDBC implements DonacionDAO {

    DonanteDao d;
    OrdenPedidoDao op;
    BienDAO b;

    public DonacionDAOJDBC() {
        this.d  = new DonanteDAOJDBC();
        this.op = new OrdenPedidoDAOJDBC();
        this.b  = new BienDAOJDBC();
    }

    @Override
    public void create(Donacion donacion) {
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO donacion(codigo, observacion, Fecha_Donacion, codigoDonante, codigoOrdenPedido) " +
                "VALUES (?, ?, ?, ?, ?)"
            );

            // MODELO: LocalDateTime -> BD: DATE
            LocalDate fecha = donacion.getFechaDonacion();
            java.sql.Date fechaSQL = java.sql.Date.valueOf(fecha);

            statement.setString(1, donacion.getCodigo());
            statement.setString(2, donacion.getObservacion());
            statement.setDate(3, fechaSQL);
            statement.setString(4, donacion.getDonante().getCodigo());

            // En la BD codigoOrdenPedido es NOT NULL.
            // Si querés permitir donación sin pedido, usamos "" para cumplir NOT NULL.
            String codPedido = (donacion.getPedido() != null)
                    ? donacion.getPedido().getCodigo()
                    : "";
            statement.setString(5, codPedido);

            int cantidad = statement.executeUpdate();
            if (cantidad <= 0) {
                System.out.println("Error al insertar donación");
            }

        } catch (SQLException e) {
            System.out.println("Error al procesar consulta (INSERT donacion): " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void update(Donacion donacion) {
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                "UPDATE donacion SET observacion = ?, Fecha_Donacion = ?, " +
                "codigoDonante = ?, codigoOrdenPedido = ? WHERE codigo = ?"
            );

            LocalDate fecha = donacion.getFechaDonacion();
            java.sql.Date fechaSQL = java.sql.Date.valueOf(fecha);

            statement.setString(1, donacion.getObservacion());
            statement.setDate(2, fechaSQL);
            statement.setString(3, donacion.getDonante().getCodigo());

            String codPedido = (donacion.getPedido() != null)
                    ? donacion.getPedido().getCodigo()
                    : "";
            statement.setString(4, codPedido);

            statement.setString(5, donacion.getCodigo());

            int cantidad = statement.executeUpdate();
            if (cantidad > 0) {
                System.out.println("La donación se ha actualizado correctamente");
            } else {
                System.out.println("No se encontró la donación a actualizar");
            }

        } catch (SQLException e) {
            System.out.println("Error al procesar consulta (UPDATE donacion): " + e.getMessage());
        } finally {
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
                java.sql.Date sqlDate = rs.getDate("Fecha_Donacion");
                LocalDate fecha = (sqlDate != null) ? sqlDate.toLocalDate() : null;
                

                ArrayList<Bien> bienes = (b != null)
                        ? b.findBienDonacion(rs.getString("codigo"))
                        : new ArrayList<>();

                Donante donante = (d != null)
                        ? d.find(rs.getString("codigoDonante"))
                        : null;

                OrdenPedido pedido = (op != null)
                        ? op.find(rs.getString("codigoOrdenPedido"))
                        : null;

                donacion = new Donacion(
                        fecha,
                        rs.getString("observacion"),
                        bienes,
                        donante,
                        pedido
                );
                // si tu modelo tiene setCodigo(), podés usarlo:
                // donacion.setCodigo(rs.getString("codigo"));
            }
        } catch (SQLException e) {
            System.out.println("Error al procesar consulta (SELECT donacion): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado en find Donacion: " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }
        return donacion;
    }

 


    @Override
    public void remove(Long id) {
        // no usado
    }

    @Override
    public void remove(String codigo) {
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                "DELETE FROM donacion WHERE codigo = ?"
            );

            statement.setString(1, codigo);

            int cantidad = statement.executeUpdate();
            if (cantidad > 0) {
                System.out.println("Donacion eliminada correctamente.");
            } else {
                System.out.println("No se encontró la donacion.");
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar donacion: " + e.getMessage());
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void remove(Donacion donacion) {
        remove(donacion.getCodigo());
    }



	@Override
	public java.util.List<Donacion> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
