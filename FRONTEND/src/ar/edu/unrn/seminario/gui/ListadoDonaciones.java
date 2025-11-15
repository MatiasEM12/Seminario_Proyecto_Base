package ar.edu.unrn.seminario.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.DonacionDTO;
import ar.edu.unrn.seminario.exception.DataNullException;


public class ListadoDonaciones extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tabla;
    private DefaultTableModel modelo;
    private java.util.List<DonacionDTO> donaciones;
    private IApi api;

    public ListadoDonaciones(IApi api) throws DataNullException {
        this.api = api;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 380);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Títulos de la tabla (asegurate que coincidan con los campos que vas a añadir)
        String[] titulos = { "CÓDIGO", "CARGA PESADA", "OBSERVACIONES", "FECHA DONACIÓN", "ESTADO", "DONANTE", "COD DONACIÓN" };
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

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(654, 305, 120, 25);
        contentPane.add(btnCerrar);

        // Cargar datos por primera vez
        cargarOrdenes();

        // Listeners
        btnRefrescar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					cargarOrdenes();
				} catch (DataNullException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

        btnCerrar.addActionListener(e -> {
            setVisible(false);
            dispose();
        });
    }

 
    private void cargarOrdenes() throws DataNullException {
        donaciones = api.obtenerDonacionesPendientes();
        if (donaciones == null) {
            donaciones = java.util.Collections.emptyList();
        }

       
         donaciones = donaciones.stream()
                .filter(Objects::nonNull)
                .filter(d -> "Pendiente".equalsIgnoreCase(String.valueOf(api.obtenerEstadoOrdenPedido(d.getCodPedido()))))
                .collect(Collectors.toList());

        // Asegurar lista limpia
        modelo.setRowCount(0);

        for (DonacionDTO D : donaciones) {
            if (D == null) continue;

            // Obtenemos valores de forma segura (si faltan getters adapta aquí)
            Object cargaPesada = null;
            try {
                // Intentar usar isCargaPesada() si existe en DonacionDTO
                cargaPesada = D.getClass().getMethod("isCargaPesada").invoke(D);
            } catch (Exception ex) {
                // método no disponible o error -> dejar false por defecto
                cargaPesada = Boolean.FALSE;
            }

            Object estado = null;
            try {
                Object st = api.obtenerEstadoOrdenPedido(D.getCodPedido());
                estado = (st == null) ? "" : st.toString();
            } catch (Exception ex) {
                estado = "";
            }

            Object fecha = null;
            try {
                fecha = D.getFechaDonacion(); // LocalDate esperado
            } catch (Exception ex) {
                fecha = null;
            }

            modelo.addRow(new Object[] {
                    safeString(D.getCodigo()),
                    cargaPesada,
                    safeString(D.getObservacion() != null ? D.getObservacion() : D.getObservacion()), // distintos DTOs usan 'observacion' o 'observaciones'
                    fecha,
                    estado,
                    safeString(D.getCodDonante()),
                    safeString(api.obtenerEstadoOrdenPedido(D.getCodPedido()))
            });
        }
    }

    private String safeString(Object o) {
        return o == null ? "" : String.valueOf(o);
    }
}
