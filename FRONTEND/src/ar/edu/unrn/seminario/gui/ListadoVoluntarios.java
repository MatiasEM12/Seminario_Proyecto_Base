package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.OrdenPedidoDTO;
import ar.edu.unrn.seminario.dto.VoluntarioDTO;
import ar.edu.unrn.seminario.exception.DAOException;

public class ListadoVoluntarios extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabla;
	private DefaultTableModel modelo;
	private List<VoluntarioDTO> voluntarios;
	private AltaOrdenRetiro ventanaRetiro;
	IApi api;
	private JButton btnCancelar;
	/**
	 * Create the frame.
	 */

	public ListadoVoluntarios(AltaOrdenRetiro ventanaRetiro, IApi api) {
	    this.api = api;
        this.ventanaRetiro = ventanaRetiro;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // no cerrar toda la app
        setBounds(100, 100, 800, 340);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblNewLabel = new JLabel("Voluntarios");
        lblNewLabel.setBounds(280, 10, 250, 16);
        contentPane.add(lblNewLabel);

        // Títulos que coinciden con los datos que vamos a agregar 
        String[] titulos = {
            "CODIGO","USERNAME"
        };

        modelo = new DefaultTableModel(new Object[][] {}, titulos) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(10, 28, 764, 128);
        contentPane.add(scroll);

        // Cargar datos desde la API
        try {
			cargarVoluntarios();
		} catch (DAOException e) {
			
			e.printStackTrace();
		}

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
                    seleccionarVoluntario();
                }
            }
        });

        // Botón “Seleccionar”
        btnSeleccionar.addActionListener(e -> seleccionarVoluntario());
    }

    private void cargarVoluntarios() throws DAOException {
        
        voluntarios = api.obtenerVoluntarios();

        if (voluntarios== null) {
            voluntarios = java.util.Collections.emptyList();
        }

       
      
        modelo.setRowCount(0);
        for (VoluntarioDTO v : voluntarios) {
            
            modelo.addRow(new Object[]{
                v.getCodigo(),                  
                v.getUsername()
            });
        }
		
	}
  
    private void seleccionarVoluntario() {
        int filaVista = tabla.getSelectedRow();
        if (filaVista < 0) {
            JOptionPane.showMessageDialog(this, "Seleccioná un voluntario");
            return;
        }

        int filaModelo = tabla.convertRowIndexToModel(filaVista);

        VoluntarioDTO seleccionado = voluntarios.get(filaModelo);

        ventanaRetiro.recibirVoluntario(seleccionado);
        dispose();
    }
}
