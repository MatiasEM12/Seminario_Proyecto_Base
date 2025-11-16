package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.exception.StateChangeException;

public class ListadoRol extends JFrame {

	private List<RolDTO> roles = new ArrayList<>();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	/**
	 * Create the frame.
	 * @throws StateChangeException 
	 */
	public ListadoRol(IApi api) throws StateChangeException {
		
		this.roles= api.obtenerRoles();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 434, 201);
		contentPane.add(scrollPane);
		

		
		  // Modelo de la tabla
		
	    DefaultTableModel modelo = new DefaultTableModel() {
	    	 public Class<?> getColumnClass(int columnIndex) {
	             if (columnIndex == 2) {
	                 return Boolean.class; // columna "Estado" como Boolean -> JCheckBox
	             }
	             return String.class;
	         }

	         public boolean isCellEditable(int row, int column) {
	             return column == 2; // solo la columna Estado es editable
	         }
	    };
	    
	    modelo.addColumn("Nombre");
	    modelo.addColumn("Codigo");
	    modelo.addColumn("Estado");
	    modelo.addColumn("Descripcion");


	   
	    
	    for(RolDTO r : roles) {
	    	modelo.addRow(new Object[]{
    	         r.getNombre(),
    	         r.getCodigo(),
    	         r.isActivo(),
    	         r.getDescripcion()
    	       
    	        });
	    }
	    
	    table = new JTable(modelo);
	    
	    scrollPane.setViewportView(table);
	    
	    JPanel panelButtom = new JPanel();
	    panelButtom.setBounds(0, 200, 434, 61);
	    contentPane.add(panelButtom);
	    
	    JButton btnEstado = new JButton("Aplicar Cambio ");
	    btnEstado.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		
	    		
	    		int filaSeleccionada = table.getSelectedRow();
	    	    if(filaSeleccionada >= 0) {
	    	        Integer codigo = (Integer) table.getValueAt(filaSeleccionada, 1);
	    	        Boolean activo = (Boolean) table.getValueAt(filaSeleccionada, 2);

	    	        if (activo)
						try {
							api.activarRol(codigo);
							JOptionPane.showMessageDialog(null, "Estado actualizado", "Estado", JOptionPane.INFORMATION_MESSAGE);
						} catch (StateChangeException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
							e1.printStackTrace();
						}
					else
						try {
							api.desactivarRol(codigo);
							JOptionPane.showMessageDialog(null, "Estado actualizado", "Estado", JOptionPane.INFORMATION_MESSAGE);
						} catch (StateChangeException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
							e1.printStackTrace();
						}

	    	        // refrescar tabla
	    	        try {
						roles = api.obtenerRoles();
					} catch (StateChangeException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	    	        for (int i = 0; i < roles.size(); i++) {
	    	            table.setValueAt(roles.get(i).isActivo(), i, 2);
	    	        }
	    	    }
	    	    
	    	}
	    });
	    
	
	    panelButtom.add(btnEstado);
	    
	    JButton btnCerrar = new JButton("Cerrar");
	    btnCerrar.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		dispose();
	    	}
	    });
	    panelButtom.add(btnCerrar);
	    
	}
	
	
}


