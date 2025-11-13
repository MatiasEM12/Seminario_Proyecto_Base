package ar.edu.unrn.seminario.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.VisitaDTO;

import java.util.ArrayList;
import java.util.List;

public class ListadoVisitas extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tabla;
    private DefaultTableModel modelo;
    private IApi api;
    private java.util.List<VisitaDTO> visitas;

    public ListadoVisitas(IApi api, String codOrdenRetiro) {
        this.api = api;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 700, 320);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5,5,5,5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        String[] titulos = {"CÓDIGO", "FECHA", "VOLUNTARIO", "TIPO", "OBSERVACIONES"};
        modelo = new DefaultTableModel(new Object[][]{}, titulos) {
            private static final long serialVersionUID = 1L;
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(10, 10, 664, 220);
        contentPane.add(scroll);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(560, 245, 114, 25);
        contentPane.add(btnCerrar);
        btnCerrar.addActionListener(e -> { setVisible(false); dispose(); });

        cargarVisitas(codOrdenRetiro);
    }

    private void cargarVisitas(String codOrdenRetiro) {
        visitas = api.obtenerVisitas(codOrdenRetiro);
        if (visitas == null) visitas = new ArrayList<>();

        modelo.setRowCount(0);
        for (VisitaDTO v : visitas) {
            String usuario = "";
            try {
                // si VisitaDTO tiene codVoluntario lo usamos para pedir nombre
                if (v.getCodVoluntario() != null) {
                    String usr = api.obtenerUsernameVoluntarioPorOrdenRetiro(v.getCodVoluntario());
                    usuario = usr == null ? "" : usr;
                }
            } catch (Exception ex) { usuario = ""; }

            modelo.addRow(new Object[] {
                    safeString(v.getCodigo()),
                    v.getFechaVisita(),
                    usuario,
                    safeString(v.getTipo()),
                    safeString(v.getObservaciones())
            });
        }
    }

    private String safeString(Object o) { return o == null ? "" : String.valueOf(o); }
}

