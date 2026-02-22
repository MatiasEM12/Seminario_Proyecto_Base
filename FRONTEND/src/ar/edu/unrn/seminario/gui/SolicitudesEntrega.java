package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.BeneficiarioDTO;
import ar.edu.unrn.seminario.exception.DataDateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.exception.DataObjectException;
import ar.edu.unrn.seminario.modelo.Donacion;
import ar.edu.unrn.seminario.dto.*;
abstract class SolicitudesEntrega extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabla;
	private DefaultTableModel modelo;
	private IApi api;
	ArrayList <SolicitudBienDTO> solicitudesDTO;

	/**
	 * Create the frame.
	 */
	public SolicitudesEntrega(IApi api) {

		solicitudesDTO=api.obtenerSolicitudesPendientes();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 380);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        String[] titulos = { "Beneficiario", "Tipo", "Prioridad" };
        modelo = new DefaultTableModel(new Object[][] {}, titulos) {
            private static final long serialVersionUID = 1L;
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(10, 10, 764, 280);
        contentPane.add(scroll);

        JButton btnVer = new JButton("Ver Solicitud");
        btnVer.setBounds(10, 305, 120, 25);
        contentPane.add(btnVer);


        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(654, 305, 120, 25);
        btnCerrar.addActionListener(e -> {
            setVisible(false);
            dispose();
        });
        contentPane.add(btnCerrar);

        // cargar inicialmente
        try {
			cargarSolicitudes();
		} catch (DataNullException | DataEmptyException | DataObjectException | DataDateException e1) {
			JOptionPane.showMessageDialog(this, "Error al refrescar donaciones: " + e1.getMessage());
		}

        // listeners
        btnVer.addActionListener(e -> {
            try {
                verPedido();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al refrescar donaciones: " + ex.getMessage());
            }
        });

     
    }

    private void cargarSolicitudes() throws DataNullException, DataEmptyException, DataObjectException, DataDateException {
       
    	List <BeneficiarioDTO> beneficiarios= new ArrayList<>();
        try {
          
        	for (SolicitudBienDTO s: solicitudesDTO) {
        		beneficiarios.add(s.getBeneficiario());
        	}
        	
        	
        } catch (Exception e) {
           
            beneficiarios = java.util.Collections.emptyList();
            System.err.println("Error al obtener donaciones pendientes: " + e.getMessage());
        }

        if (beneficiarios== null) beneficiarios = java.util.Collections.emptyList();

        modelo.setRowCount(0);

        for (BeneficiarioDTO b : beneficiarios) {
            if (b == null) continue;

         
            modelo.addRow(new Object[] {
                    safeString(b.getCodigo()),
                    safeString("Entrega"),
                    safeString(b.getPrioridad())
            });
        }
    }

    private String safeString(Object o) {
        return o == null ? "" : String.valueOf(o);
    }

    private void verPedido() {
        int fila = tabla.getSelectedRow();

        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccioná un beneficiario.");
            return;
        }

        String codigoBeneficiario = modelo.getValueAt(fila, 0).toString();

        SolicitudBienDTO solicitud = buscarSolicitud(codigoBeneficiario);

        if (solicitud == null) {
            JOptionPane.showMessageDialog(this, "No se encontró la solicitud.");
            return;
        }

        //  abrir la ventana de pedido
        VentanaPedido ventanaPedido = new VentanaPedido(api, solicitud);
        ventanaPedido.setVisible(true);

        dispose();
    }
    private SolicitudBienDTO buscarSolicitud(String codBeneficiario) {
    	
    	for (SolicitudBienDTO s: solicitudesDTO) {
    		if(s.getBeneficiario().getCodigo().equalsIgnoreCase(codBeneficiario)) {
    			return s;
    		}
    	}
    	return null;
    }
}


