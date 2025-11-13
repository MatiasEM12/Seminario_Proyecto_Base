package ar.edu.unrn.seminario.gui;

import java.util.Objects;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.OrdenPedidoDTO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class VentanaOrdenPedido extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tabla;
    private DefaultTableModel modelo;
    private List<OrdenPedidoDTO> ordenes;
    private AltaOrdenRetiro ventanaRetiro;
    IApi api;

    private JButton btnCancelar;

    public VentanaOrdenPedido(AltaOrdenRetiro ventanaRetiro, IApi api) {
        this.api = api;
        this.ventanaRetiro = ventanaRetiro;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // no cerrar toda la app
        setBounds(100, 100, 800, 340);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblNewLabel = new JLabel("Órdenes de Pedido Pendientes");
        lblNewLabel.setBounds(280, 10, 250, 16);
        contentPane.add(lblNewLabel);

        // Títulos que coinciden con los datos que vamos a agregar (7 columnas)
        String[] titulos = {
            "CÓDIGO", "CARGA PESADA", "OBSERVACIONES",
            "FECHA EMISIÓN", "ESTADO", "DONANTE", "DONACIÓN"
        };

        modelo = new DefaultTableModel(new Object[][] {}, titulos) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(10, 28, 764, 128);
        contentPane.add(scroll);

        // Cargar datos desde la API
        cargarOrdenes();

        JButton btnSeleccionar = new JButton("Seleccionar");
        btnSeleccionar.setBounds(167, 269, 136, 21);
        contentPane.add(btnSeleccionar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
        btnCancelar.setBounds(464, 269, 85, 21);
        contentPane.add(btnCancelar);

        // MouseListener: doble clic selecciona
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    seleccionarOrden();
                }
            }
        });

        // Botón “Seleccionar”
        btnSeleccionar.addActionListener(e -> seleccionarOrden());
    }

    private void cargarOrdenes() {
        // traer todas las ordenes desde la API
        ordenes = api.obtenerOrdenesPedido();

        if (ordenes == null) {
            ordenes = java.util.Collections.emptyList();
        }

        // Filtrar pendientes: si getEstado() es String
        try {
        	ordenes = ordenes.stream()
        		    .filter(o -> o.getEstado() != null && o.getEstado().toString().equalsIgnoreCase("Pendiente"))
        		    .collect(Collectors.toList());

        } catch (Exception ex) {
            
        }

        modelo.setRowCount(0);
        for (OrdenPedidoDTO o : ordenes) {
            
            modelo.addRow(new Object[]{
                o.getCodigo(),                   // CÓDIGO
                o.isCargaPesada(),               // CARGA PESADA (boolean)
                o.getObservaciones(),            // OBSERVACIONES
                o.getFechaEmision(),             // FECHA EMISIÓN
                o.getEstado(),                   // ESTADO
                o.getCodDonante(),               // DONANTE
                o.getCodDonacion()               // DONACIÓN
            });
        }
    }

    private void seleccionarOrden() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccioná una orden");
            return;
        }
        OrdenPedidoDTO seleccionada = ordenes.get(fila);

        // Pasa los datos a la ventana de Retiro
        ventanaRetiro.recibirOrdenPedido(seleccionada);

        dispose(); // cerrar esta ventana
    }
}

