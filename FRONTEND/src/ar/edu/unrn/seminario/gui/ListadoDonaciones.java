package ar.edu.unrn.seminario.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.DonacionDTO;
import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;

public class ListadoDonaciones extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tabla;
    private DefaultTableModel modelo;
    private java.util.List<DonacionDTO> donaciones;
    private IApi api;
    private AltaOrdenPedido ventanaPedido;

    public ListadoDonaciones(IApi api, AltaOrdenPedido altaPedido) throws DataNullException, DataEmptyException, DataObjectException, DataDateException {
        this.api = api;
        this.ventanaPedido = altaPedido;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 380);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        String[] titulos = { "CÓDIGO", "OBSERVACIONES", "FECHA DONACIÓN", "DONANTE", "COD PEDIDO" };
        modelo = new DefaultTableModel(new Object[][] {}, titulos) {
            private static final long serialVersionUID = 1L;
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(10, 10, 764, 280);
        contentPane.add(scroll);

        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.setBounds(10, 305, 120, 25);
        contentPane.add(btnRefrescar);

        JButton btnSeleccionar = new JButton("Seleccionar");
        btnSeleccionar.setBounds(147, 306, 134, 23);
        contentPane.add(btnSeleccionar);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(654, 305, 120, 25);
        btnCerrar.addActionListener(e -> {
            setVisible(false);
            dispose();
        });
        contentPane.add(btnCerrar);

        // cargar inicialmente
        cargarOrdenes();

        // listeners
        btnRefrescar.addActionListener(e -> {
            try {
                cargarOrdenes();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al refrescar donaciones: " + ex.getMessage());
            }
        });

        btnSeleccionar.addActionListener(e -> seleccionarPedido());
    }

    private void cargarOrdenes() throws DataNullException, DataEmptyException, DataObjectException, DataDateException {
        // intentamos obtener donaciones pendientes 
        try {
            donaciones = api.obtenerDonacionesPendientes();
        } catch (Exception e) {
            // si falla, mostramos lista vacía y aviso
            donaciones = java.util.Collections.emptyList();
            System.err.println("Error al obtener donaciones pendientes: " + e.getMessage());
        }

        if (donaciones == null) donaciones = java.util.Collections.emptyList();

        modelo.setRowCount(0);

        for (DonacionDTO D : donaciones) {
            if (D == null) continue;

            Object fecha = D.getFechaDonacion() != null ? D.getFechaDonacion() : null;
            modelo.addRow(new Object[] {
                    safeString(D.getCodigo()),
                    safeString(D.getObservacion()),
                    fecha,
                    safeString(D.getCodDonante()),
                    safeString(D.getCodPedido())
            });
        }
    }

    private String safeString(Object o) {
        return o == null ? "" : String.valueOf(o);
    }

    private void seleccionarPedido() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccioná una donación.");
            return;
        }
        DonacionDTO seleccionada = donaciones.get(fila);

        if (ventanaPedido != null) {
            ventanaPedido.recibirDonacion(seleccionada);
        }

        setVisible(false);
        dispose();
    }
}
