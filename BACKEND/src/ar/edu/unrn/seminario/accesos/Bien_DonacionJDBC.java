package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DataDoubleException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Bien;
import ar.edu.unrn.seminario.modelo.Coordenada;

public class Bien_DonacionJDBC implements Bien_DonacionDAO {

    private BienDAO bienDao; // inyectalo por constructor o setter si lo necesitás

    @Override
    public void create(String codBien, String codDonacion) {
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO Bien_Donacion(codBien, codDonacion) VALUES (?, ?)"
            );

            statement.setString(1, codBien);
            statement.setString(2, codDonacion);

            int cantidad = statement.executeUpdate();
            if (cantidad <= 0) {
                System.out.println("No se insertó registro en Bien_Donacion");
            }

        } catch (SQLException e) {
            System.out.println("Error al procesar consulta (INSERT Bien_Donacion): " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e); // si querés que reviente y no diga "Donación OK"
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void update(String codBienNuevo, String codBienViejo, String codDonacion) {
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                "UPDATE Bien_Donacion SET codBien = ? " +
                "WHERE codBien = ? AND codDonacion = ?"
            );

            statement.setString(1, codBienNuevo);
            statement.setString(2, codBienViejo);
            statement.setString(3, codDonacion);

            int cantidad = statement.executeUpdate();
            if (cantidad > 0) {
                System.out.println("El Bien_Donacion se ha actualizado correctamente");
            } else {
                System.out.println("No se encontró registro a actualizar en Bien_Donacion");
            }

        } catch (SQLException e) {
            System.out.println("Error al procesar consulta (UPDATE Bien_Donacion): " + e.getMessage());
            e.printStackTrace();
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void remove(Long id) {
        // no lo usás
    }

    @Override
    public void remove(String codigoDonacion) {
        // si lo necesitás, implementalo así:
        /*
        try {
            Connection conn = ConnectionManager.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                "DELETE FROM Bien_Donacion WHERE codDonacion = ?"
            );
            statement.setString(1, codigoDonacion);
            int cantidad = statement.executeUpdate();
            ...
        } catch (SQLException e) {
            ...
        } finally {
            ConnectionManager.disconnect();
        }
        */
    }

    @Override
    public List<Bien> findAll() {
        return null; // no lo usás
    }

    @Override
    public List<Bien> findDonacion(String codDonacion) throws DataNullException, DataDoubleException {
        ArrayList<Bien> resultado = new ArrayList<>();
        if (codDonacion == null || codDonacion.trim().isEmpty()) return resultado;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();

            String sql =
                "SELECT b.codigo, b.tipo, b.nombre, b.peso, b.descripcion, b.nivelNecesidad, " +
                "       b.fechaVencimiento, b.talle, b.material " +
                "FROM bien b " +
                "JOIN Bien_Donacion bd ON b.codigo = bd.codBien " +
                "WHERE bd.codDonacion = ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, codDonacion);

            rs = ps.executeQuery();
            while (rs.next()) {
                Bien bien = new Bien(
                    rs.getString("codigo"),
                    rs.getString("tipo"),
                    rs.getObject("peso") != null ? rs.getDouble("peso") : 0.0,
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getObject("nivelNecesidad") != null ? rs.getInt("nivelNecesidad") : 0,
                    rs.getDate("fechaVencimiento") != null
                            ? rs.getDate("fechaVencimiento").toLocalDate()
                            : null,
                    rs.getObject("talle") != null ? rs.getDouble("talle") : null,
                    rs.getString("material")
                );

                resultado.add(bien);
            }

        } catch (SQLException e) {
            System.out.println("Error al recuperar bienes de donación: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
            try { if (ps != null) ps.close(); } catch (SQLException ignored) {}
            ConnectionManager.disconnect();
        }

        return resultado;
    }
}
