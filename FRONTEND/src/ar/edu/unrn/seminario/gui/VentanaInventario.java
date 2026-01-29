package ar.edu.unrn.seminario.gui;


import java.awt.EventQueue;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.BienDTO;
import ar.edu.unrn.seminario.dto.InventarioDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DAOException;
import ar.edu.unrn.seminario.exception.DataNullException;
import ar.edu.unrn.seminario.modelo.Bien;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;


public class VentanaInventario extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel modelo;
    private JComboBox<String> comboBox;
    IApi api;

    public VentanaInventario(IApi api) {
        initialize(api);
    }

    private void initialize(IApi api) {
        this.api = api;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 623, 468);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        table = new JTable();
        String[] titulos = { "Codigo", "Tipo","Nombre","Desc","Peso","Vencimiento","Talle","Material"};
        modelo = new DefaultTableModel(new Object[][] {}, titulos);
        table.setModel(modelo);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 65, 573, 286);
        contentPane.add(scrollPane);

        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JButton btnNewButton = new JButton("Buscar");
        btnNewButton.setBounds(362, 10, 100, 30);
        contentPane.add(btnNewButton);
        
        JLabel lblNewLabel = new JLabel("Tipo de Donacion");
        lblNewLabel.setBounds(129, 10, 90, 30);
        contentPane.add(lblNewLabel);
        
        JComboBox comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel<>(new String[] {"Todos", "Alimento", "Medicamento", "Ropa", "Mueble", "Electrodomestico", "Otros", "Bienes vencidos"}));
        comboBox.setBounds(229, 10, 123, 30);
        contentPane.add(comboBox);
        
        JButton btnNewButton_1 = new JButton("Eliminar");
        btnNewButton_1.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            int opcion = JOptionPane.showConfirmDialog(null, "¿Seguro que querés eliminar el bien?",
	                    "Confirmación", JOptionPane.YES_NO_OPTION);
	            if (opcion == JOptionPane.YES_OPTION) {
	                int filaSeleccionada = table.getSelectedRow();
	                if (filaSeleccionada >= 0) {
	                    String codigo = (String) table.getValueAt(filaSeleccionada, 0);
	                    try {
							api.eliminarBineInventario(codigo);
						} catch (DataNullException | DAOException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
						}
	                    DefaultTableModel model = (DefaultTableModel) table.getModel();
	                    model.removeRow(filaSeleccionada);
						JOptionPane.showMessageDialog(null, "se eliminaron correctamente. un total de 1 bien", "Inventario", JOptionPane.INFORMATION_MESSAGE);
	                }
	            } else {
	                JOptionPane.showMessageDialog(null, "Operación cancelada.");
	            }
	        }
	    });
        btnNewButton_1.setBounds(453, 380, 100, 30);
        contentPane.add(btnNewButton_1);
        
        JButton btnNewButton_2 = new JButton("Cantidad");
        btnNewButton_2.setBounds(343, 383, 100, 25);
        contentPane.add(btnNewButton_2);
        
        
        JButton btnNewButton_3 = new JButton("Modificar");
        btnNewButton_3.addActionListener(e -> {
            int fila=table.getSelectedRow();
            if (fila<0) {
                JOptionPane.showMessageDialog(null, "No se seleciono un bien para modificar.");
                return;
            }
            String codigo=(String) table.getValueAt(fila, 0);
            Bien bien = null;
			try {
				bien = api.ObtenerBien(codigo);
			} catch (DataNullException | DAOException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			}
            if (bien==null) {
                JOptionPane.showMessageDialog(null, "Ocurio un error al intenar cargar el bien.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ModificarBien ventana=new ModificarBien(api, bien, VentanaInventario.this);
            ventana.setVisible(true);

        });
        btnNewButton_3.setBounds(233, 383, 100, 25);
        contentPane.add(btnNewButton_3);
        
        
        btnNewButton_2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String tipoSeleccionado=(String) comboBox.getSelectedItem();
                int cantidad=0;
                for (int i=0; i<modelo.getRowCount(); i++){
                    String tipoFila=(String) modelo.getValueAt(i, 1);
                    //el if comprueba que el tipo de busqueda se realiso si era todos contara todos, sino comprobara si tienen el mismo tipo
                    if (tipoSeleccionado.equals("Todos")||tipoFila.equals(tipoSeleccionado)){
                        cantidad++;
                    }
                }
                JOptionPane.showMessageDialog(null,
                        "Cantidad de bienes de tipo '" + tipoSeleccionado + "': " + cantidad,
                        "Inventario",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        comboBox.addActionListener(e -> {
            modelo.setRowCount(0);              // Limpiar las filas actuales
            try {
                filtrar((String) comboBox.getSelectedItem());  // Filtra usando el valor seleccionado
            } catch (DataNullException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    
    
    
    
    //no terminado del todo porque no se como va a estar inventario dto
	private void filtrar(String tipo) throws DataNullException {
	    //Obtiene los bienes de acuerdo al tipo elegido
	    List<BienDTO> bienes = obtenerBienesPorTipo(tipo);
	    for (BienDTO bien : bienes) {
	        modelo.addRow(new Object[] {
	            bien.getCodigo(),
	            bien.getTipo(),
	            bien.getNombre(),
	            bien.getDescripcion(),
	            bien.getPeso(),
	            bien.getFechaVencimiento(),
	            bien.getTalle(),
	            bien.getMaterial()
	        });
	    }
	}
	
	private List<BienDTO> obtenerBienesPorTipo(String tipo) {
	    if (tipo.equals("Todos")) {
	        try {
				return api.obtenerTodosLosBienes();
			} catch (DAOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			}
	    } else {
	        try {
				return api.obtenerBienesPorTipo(tipo);
			} catch (DataNullException | DAOException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
			}
	    }
	    return List.of();
	}
	// poner como condicion que el almacendao de bien se true para que sea que esta almacenado, false el bien aun no esta almacenado
}
