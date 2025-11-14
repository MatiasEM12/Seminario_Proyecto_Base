package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import ar.edu.unrn.seminario.modelo.OrdenPedido;


public class OrdenPedidoDAOJDBC implements OrdenPedidoDao {

    
    @Override
    public void create(OrdenPedido orden) {
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement statement = conn.prepareStatement(
            		"INSERT INTO OrdenPedido (codigo, tipo, cargaPesada, estado, FechaCreacion) " +
                            "VALUES (?, ?, ?, ?, ?,?)"
            );

            java.sql.Date fechaSQL = java.sql.Date.valueOf(orden.getFechaEmision());

            statement.setString(1, orden.getCodigo());
            statement.setString(2, OrdenPedido.getTipo());
            statement.setBoolean(3, orden.isCargaPesada());
            statement.setString(4, orden.getObservaciones());
            statement.setString(5, orden.getEstado().toString());
            statement.setDate(6, fechaSQL);
            

            int cantidad = statement.executeUpdate();
            if (cantidad <= 0) {
                System.out.println("No se insertó la OrdenPedido");
            }

        } catch (SQLException e) {
            System.out.println("Error al procesar consulta (INSERT OrdenPedido): " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionManager.disconnect();
        }
    }
    @Override
    public void update(OrdenPedido orden) {
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                "UPDATE OrdenPedido " +
                "SET fechaCreacion = ?, cargaPesada = ?, observaciones = ?, codDonante = ?, codDonacion = ? " +
                "WHERE codigo = ?"
            );

            java.sql.Date fechaSQL = java.sql.Date.valueOf(orden.getFechaEmision());

            statement.setDate(1, fechaSQL);
            statement.setBoolean(2, orden.isCargaPesada());
            statement.setString(3, orden.getObservaciones());
            statement.setString(4, orden.getCodDonante());
            statement.setString(5, orden.getCodDonacion());
            statement.setString(6, orden.getCodigo());

            int cantidad = statement.executeUpdate();
            if (cantidad > 0) {
                System.out.println("La orden se ha actualizado correctamente");
            } else {
                System.out.println("No se encontró la orden a actualizar");
            }

        } catch (SQLException e) {
            System.out.println("Error al procesar consulta (UPDATE OrdenPedido): " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void remove(String id) {
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                "DELETE FROM OrdenPedido WHERE codigo = ?"
            );

            statement.setString(1, id);

            int cantidad = statement.executeUpdate();
            if (cantidad > 0) {
                System.out.println("OrdenPedido eliminada correctamente.");
            } else {
                System.out.println("No se encontró la OrdenPedido con ese código.");
            }

        } catch (SQLException e) {
            System.out.println("Error al Eliminar OrdenPedido: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void remove(OrdenPedido orden) {
        remove(orden.getCodigo());
    }

    @Override
    public OrdenPedido find(String codigo) {
        OrdenPedido orden = null;
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                /*"SELECT o.codigo, o.fechaCreacion, o.cargaPesada, o.observaciones, o.codDonante, o.codDonacion " +
                "FROM OrdenPedido o WHERE o.codigo = ?"*/
            		"SELECT o.codigo, o.fechaCreacion, o.cargaPesada, o.observaciones" +
                    "FROM OrdenPedido o WHERE o.codigo = ?"
            );

            statement.setString(1, codigo);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {

                    LocalDate fecha = rs.getDate("fechaEmision").toLocalDate();

                    orden = new OrdenPedido(
                        fecha,
                        rs.getBoolean("cargaPesada"),
                        rs.getString("observaciones"),
                        rs.getString("codDonante"),
                        rs.getString("codDonacion")
                    );
                    orden.setCodigo(rs.getString("codigo"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al procesar consulta (SELECT OrdenPedido): " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error inesperado en find OrdenPedido: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionManager.disconnect();
        }

        return orden;
    }

    @Override
    public List<OrdenPedido> findAll() {
        List<OrdenPedido> ordenes = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                /*"SELECT o.codigo, o.fechaCreacion, o.cargaPesada, o.observaciones, o.codDonante, o.codDonacion" +
                "FROM OrdenPedido o"*/
            		"SELECT o.codigo, o.fechaCreacion, o.cargaPesada, o.observaciones" +
                    "FROM OrdenPedido o"
            );

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                LocalDate fecha = rs.getDate("fechaEmision").toLocalDate();

                OrdenPedido orden = new OrdenPedido(
                    fecha,
                    rs.getBoolean("cargaPesada"),
                    rs.getString("observaciones"),
                    rs.getString("codDonante"),
                    rs.getString("codDonacion")
                );
                orden.setCodigo(rs.getString("codigo"));

                ordenes.add(orden);
            }

        } catch (SQLException e) {
            System.out.println("Error al procesar consulta (SELECT ALL OrdenPedido): " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error inesperado en findAll OrdenPedido: " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionManager.disconnect();
        }

        return ordenes;
    }
}
