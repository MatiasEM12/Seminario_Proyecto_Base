package ar.edu.unrn.seminario.gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.BienDTO;
import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataNullException;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.util.ArrayList;
import java.util.function.Consumer;

public class ListadoBienesOrden extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel modelo;

    private Consumer<ArrayList<BienDTO>> onSeleccion;
    private IApi api;
    private ArrayList<BienDTO> bienes;

    public ListadoBienesOrden(IApi api,
                         ArrayList<BienDTO> bienesDTO,
                         Consumer<ArrayList<BienDTO>> onSeleccion) {

        this.api = api;
        this.onSeleccion = onSeleccion;
        this.bienes = new ArrayList<>(bienesDTO == null ? new ArrayList<>() : bienesDTO);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 650, 320);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // ===================== TABLA =====================
        String[] titulos = {
                "Seleccionar",
                "Codigo", "Tipo", "Nombre", "Descripcion",
                "Peso", "Vencimiento", "Talle", "Material"
        };

        modelo = new DefaultTableModel(new Object[][] {}, titulos) {

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Boolean.class; // checkbox
                }
                return Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // solo checkbox editable
            }
        };

        table = new JTable(modelo);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 11, 615, 200);
        contentPane.add(scrollPane);

        // ===================== CARGA DE BIENES =====================
        for (BienDTO b : bienes) {
            modelo.addRow(new Object[] {
                    false, // checkbox
                    b.getCodigo(),
                    b.getTipo(),
                    b.getNombre(),
                    b.getDescripcion(),
                    b.getPeso(),
                    b.getFechaVencimiento(),
                    b.getTalle(),
                    b.getMaterial()
            });
        }

        // ===================== BOTONES =====================
        JButton btnSeleccionar = new JButton("Seleccionar");
        btnSeleccionar.setBounds(350, 230, 120, 25);
        contentPane.add(btnSeleccionar);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(505, 230, 120, 25);
        contentPane.add(btnCerrar);

        // ===================== EVENTOS =====================
        btnSeleccionar.addActionListener(e -> seleccionarBienes());

        btnCerrar.addActionListener(e -> {
            dispose();
        });
    }

    // ===================== MÉTODO SELECCIÓN =====================
    private void seleccionarBienes() {

        ArrayList<BienDTO> seleccionados = new ArrayList<>();

        for (int i = 0; i < modelo.getRowCount(); i++) {

            Boolean marcado = (Boolean) modelo.getValueAt(i, 0);

            if (Boolean.TRUE.equals(marcado)) {

                String codigo = (String) modelo.getValueAt(i, 1);

                try {
                    BienDTO bien = api.obtenerBien(codigo);
                    if (bien != null) {
                        seleccionados.add(bien);
                    }
                } catch (DataNullException | DAOException ex) {
                    JOptionPane.showMessageDialog(this,
                            ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }

        if (seleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar al menos un bien",
                    "Atención",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (onSeleccion != null) {
            onSeleccion.accept(seleccionados);
        }

        dispose();
    }
}

