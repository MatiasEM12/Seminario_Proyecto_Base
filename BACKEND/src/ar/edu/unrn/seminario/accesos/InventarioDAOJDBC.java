package ar.edu.unrn.seminario.accesos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.modelo.Bien;

public class InventarioDAOJDBC implements InventarioDAO {

    private BienDAOJDBC bienDao = new BienDAOJDBC();

    @Override
    public void create(String codBien, String tipoBien, boolean disponible) throws DAOException {

        if (existe(codBien)) {
            throw new DAOException("El bien ya existe en inventario");
        }

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                 "INSERT INTO inventario(codigoBien,tipoBien,disponible) VALUES (?, ?, ?)"
             )) {

            statement.setString(1, codBien);
            statement.setString(2, tipoBien);
            statement.setBoolean(3, disponible);

            int cantidad = statement.executeUpdate();
            if (cantidad <= 0) {
                throw new DAOException("Error al actualizar. codigo error I100");
            }

        } catch (SQLException e) {
            throw new DAOException(
                "Error al procesar consulta (INSERT INVENTARIO): " + e.getMessage() + ". codigo error I101"+ e
            );
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void update(String codBien, String tipoBien, boolean disponible) throws DAOException {

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                 "UPDATE inventario SET tipoBien= ?, disponible = ? WHERE codigoBien = ?"
             )) {

            statement.setString(1, tipoBien);
            statement.setBoolean(2, disponible);
            statement.setString(3, codBien);

            int cantidad = statement.executeUpdate();
            if (cantidad > 0) {
                System.out.println("El inventario se ha actualizado correctamente");
            } else {
                throw new DAOException("Error al actualizar. codigo error I200");
            }

        } catch (SQLException e) {
            throw new DAOException("Error al procesar consulta." + e.getMessage() + " codigo error I201"+ e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public void remove(String codBien) throws DAOException {

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                 "DELETE FROM inventario WHERE codigoBien= ?"
             )) {

            statement.setString(1, codBien);

            int cantidad = statement.executeUpdate();
            if (cantidad > 0) {
                System.out.println("inventario eliminado correctamente el bien.");
            } else {
                throw new DAOException("No se encontró el bien en el inventario. codigo error I300");
            }

        } catch (SQLException e) {
            throw new DAOException("Error al Eliminar bien del inventario." + e.getMessage() + " codigo error I301"+ e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public Bien findBien(String codBien) throws DAOException {

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement sent = conn.prepareStatement(
                 "SELECT codigoBien FROM inventario WHERE codigoBien = ?"
             )) {

            sent.setString(1, codBien);

            try (ResultSet rs = sent.executeQuery()) {
                if (rs.next()) {
                    // Si existe en inventario, traigo el Bien
                    return this.bienDao.find(codBien);
                }
                return null;
            }

        } catch (SQLException e) {
            throw new DAOException("Error al procesar consulta" + e.getMessage() + ". codigo error I500"+ e);
        } catch (Exception e) {
            throw new DAOException("Error inesperado: " + e.getMessage() + ". codigo error I501"+ e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    @Override
    public List<Bien> findAll() throws DAOException {

        List<Bien> bienes = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement sent = conn.prepareStatement(
                 "SELECT codigoBien FROM inventario"
             );
             ResultSet rs = sent.executeQuery()) {

            while (rs.next()) {
                String cod = rs.getString("codigoBien");
              
                Bien b = bienDao.find(cod);
                if (b != null) bienes.add(b);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al procesar consulta" + e.getMessage() + ". codigo error I600"+ e);
        } catch (Exception e) {
            throw new DAOException("Error inesperado: " + e.getMessage() + ". codigo error I601"+ e);
        } finally {
            ConnectionManager.disconnect();
        }

        return bienes;
    }

    @Override
    public List<Bien> findBienesDisponibles() throws DAOException {

        List<Bien> bienes = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement sent = conn.prepareStatement(
                 "SELECT codigoBien FROM inventario WHERE disponible = 1"
             );
             ResultSet rs = sent.executeQuery()) {

            while (rs.next()) {
                String cod = rs.getString("codigoBien");
               
                Bien b = bienDao.find(cod);
                if (b != null) bienes.add(b);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al procesar consulta" + e.getMessage() + ". codigo error I600"+ e);
        } catch (Exception e) {
            throw new DAOException("Error inesperado: " + e.getMessage() + ". codigo error I601"+ e);
        } finally {
            ConnectionManager.disconnect();
        }

        return bienes;
    }

    @Override
    public List<Bien> findBienesNoDisponibles() throws DAOException {

        List<Bien> bienes = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement sent = conn.prepareStatement(
                 "SELECT codigoBien FROM inventario WHERE disponible = 0"
             );
             ResultSet rs = sent.executeQuery()) {

            while (rs.next()) {
                String cod = rs.getString("codigoBien");
                
                Bien b = bienDao.find(cod);
                if (b != null) bienes.add(b);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al procesar consulta" + e.getMessage() + ". codigo error I600"+ e);
        } catch (Exception e) {
            throw new DAOException("Error inesperado: " + e.getMessage() + ". codigo error I601"+ e);
        } finally {
            ConnectionManager.disconnect();
        }

        return bienes;
    }

    public boolean existe(String codBien) throws DAOException {

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT 1 FROM inventario WHERE codigoBien = ?"
             )) {

            ps.setString(1, codBien);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new DAOException(e.getMessage()+ e);
        } finally {
            ConnectionManager.disconnect();
        }
    }

    public boolean esDisponible(String codBien) throws DAOException {

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT disponible FROM inventario WHERE codigoBien = ?"
             )) {

            ps.setString(1, codBien);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("disponible");
                }
                return false; // no existe
            }

        } catch (SQLException e) {
            throw new DAOException("Error al verificar disponibilidad del bien: " + e.getMessage()+ e);
        } finally {
            ConnectionManager.disconnect();
        }
    }
}
