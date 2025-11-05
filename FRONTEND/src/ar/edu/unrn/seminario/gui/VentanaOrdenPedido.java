package ar.edu.unrn.seminario.gui;


import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;

import ar.edu.unrn.seminario.dto.OrdenPedidoDTO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaOrdenPedido extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tabla;
    private DefaultTableModel modelo;
    private java.util.List<OrdenPedidoDTO> ordenes;
    private AltaOrdenRetiro ventanaRetiro;
    IApi api;

    private JButton btnCancelar;



    public VentanaOrdenPedido(AltaOrdenRetiro ventanaRetiro,IApi api) {
        this.api = api; // guardar la instancia
        this.ventanaRetiro=ventanaRetiro;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 340);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblNewLabel = new JLabel("Órdenes de Pedido Pendientes");
        lblNewLabel.setBounds(280, 10, 250, 16);
        contentPane.add(lblNewLabel);
       
        // Títulos que COINCIDEN con los datos que vas a agregar
        String[] titulos = {
            "CÓDIGO", "CARGA PESADA", "OBSERVACIONES",
            "FECHA EMISIÓN", "ESTADO", "DONANTE", "DONACIÓN"
        };

        modelo = new DefaultTableModel(new Object[][] {}, titulos) {
            // Para que las celdas no sean editables:
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        // Cargar datos desde la API (INSTANCIA, no estático)
       cargarOrdenes();

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(10, 28, 764, 128);
        contentPane.add(scroll);
        
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
        cargarOrdenes();

        // 🖱 MouseListener: doble clic selecciona
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    seleccionarOrden();
                }
            }
        });

        // O el botón “Seleccionar”
        btnSeleccionar.addActionListener(e -> seleccionarOrden());
    }

    private void cargarOrdenes() {
        ordenes = api.obtenerOrdenesPedido();
        ordenes= ordenes.stream().filter(o->o.getEstado().equals("Pendiente")).collect(Collectors.toList());
        modelo.setRowCount(0);
        for (OrdenPedidoDTO o : ordenes) {
            modelo.addRow(new Object[]{
                o.getCodigo(),
                o.getEstado(),
                o.getObservaciones(),
                o.getFechaEmision(),
                o.getCodDonante()
            });
        }
    }

    private void seleccionarOrden() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccioná una orden");
            return;
        }
        OrdenPedidoDTO seleccionada = ordenes.get(fila);

        // 🔁 Pasa los datos a la ventana de Retiro
        ventanaRetiro.recibirOrdenPedido(seleccionada);

        dispose(); // cerrar esta ventana
    }
}
