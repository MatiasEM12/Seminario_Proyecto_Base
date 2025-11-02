package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.StateChangeException;

import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import ar.edu.unrn.seminario.api.IApi;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListadoUsuario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JButton btnActivar;
	JButton btnDesactivar;
	private JTable table;
	private DefaultTableModel modelo;
	private JTextField textField;
	IApi api;

	/**
	 * Create the frame.
	 */
	public ListadoUsuario(	IApi api) {
		  this.api = api;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 623, 468);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);	
		
		JComboBox lUsuarioBox = new JComboBox();
		lUsuarioBox.addItem("Todos");
		lUsuarioBox.addItem("Administradores");
		lUsuarioBox.addItem("Donantes");
		lUsuarioBox.addItem("Voluntarios");
		//lUsuarioBox.addItem("Beneficiarios");
		lUsuarioBox.setBounds(91, 13, 122, 21);
		contentPane.add(lUsuarioBox);
		
      
		table = new JTable();
		String[] titulos = { "Codigo", "tipo", "Usuario", "Nombre", "Contacto", "Estado" };
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Habilitar botones
				habilitarBotones(true);

			}
		});
		modelo = new DefaultTableModel(new Object[][] {}, titulos);
		  JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setBounds(10, 45, 581, 308);
			contentPane.add(scrollPane);
			
		// Obtiene la lista de usuarios a mostrar
		filtrar(lUsuarioBox, textField);   
		table.setModel(modelo);

		scrollPane.setViewportView(table);
        
		
		JLabel lblNewLabel = new JLabel("Listar por:");
		lblNewLabel.setBounds(10, 21, 69, 13);
		contentPane.add(lblNewLabel);
		
		
		
		JLabel lblBuscarPor = new JLabel("Buscar por:");
		lblBuscarPor.setBounds(245, 21, 69, 13);
		contentPane.add(lblBuscarPor);
		
		textField = new JTextField();
		textField.setBounds(324, 14, 108, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(461, 16, 89, 23);
		contentPane.add(btnBuscar);
		
		JPanel panelButtom = new JPanel();
		panelButtom.setBounds(10, 359, 581, 70);
		contentPane.add(panelButtom);
		panelButtom.setLayout(null);
		
		
		JButton btnEliminar = new JButton("Eliminar");
	    btnEliminar.setBounds(29, 7, 90, 31);
	    panelButtom.add(btnEliminar);
	    btnEliminar.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            int opcion = JOptionPane.showConfirmDialog(null, "¿Seguro que querés eliminar el usuario?",
	                    "Confirmación", JOptionPane.YES_NO_OPTION);
	            if (opcion == JOptionPane.YES_OPTION) {
	                int filaSeleccionada = table.getSelectedRow();
	                if (filaSeleccionada >= 0) {
	                    String user = (String) table.getValueAt(filaSeleccionada, 0);
	                    api.eliminarUsuario(user);
	                    DefaultTableModel model = (DefaultTableModel) table.getModel();
	                    model.removeRow(filaSeleccionada);
	                }
	            } else {
	                JOptionPane.showMessageDialog(null, "Operación cancelada.");
	            }
	        }
	    });


	    btnActivar = new JButton("Activar");
	    btnActivar.setEnabled(false);
	    btnActivar.setBounds(284, 7, 92, 31);
	    panelButtom.add(btnActivar);
	    btnActivar.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            int opcionSeleccionada = JOptionPane.showConfirmDialog(null,
	                    "Estas seguro que queres cambiar el estado del Usuario?", "Confirmar cambio de estado.",
	                    JOptionPane.YES_NO_OPTION);
	            if (opcionSeleccionada == JOptionPane.YES_OPTION) {
	                String username = (String) table.getModel().getValueAt(table.getSelectedRow(), 2);
	                try {
	                    api.activarUsuario(username);
	                    filtrar(lUsuarioBox, textField);
	                } catch (StateChangeException eS) {
	                    JOptionPane.showMessageDialog(null, eS.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	                }
	            }
	        }
	    });

	    btnDesactivar = new JButton("Desactivar");
	    btnDesactivar.setEnabled(false);
	    btnDesactivar.setBounds(158, 7, 92, 31);
	    panelButtom.add(btnDesactivar);
	    btnDesactivar.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            int reply = JOptionPane.showConfirmDialog(null, "Estas seguro que queres cambiar el estado del Usuario?",
	                    "Confirmar cambio de estado.", JOptionPane.YES_NO_OPTION);
	            if (reply == JOptionPane.YES_OPTION) {
	                String username = (String) table.getModel().getValueAt(table.getSelectedRow(), 2);
	                try {
	                    api.desactivarUsuario(username);
	                    filtrar(lUsuarioBox, textField);
	                } catch (StateChangeException eS) {
	                    JOptionPane.showMessageDialog(null, eS.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	                }
	            }
	        }
	    });


		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setBounds(415, 7, 90, 31);
	    panelButtom.add(btnCerrar);
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		// cuando cambie el combo: limpiar modelo y aplicar filtro
		lUsuarioBox.addActionListener(e -> {
		    modelo.setRowCount(0);              // limpiar filas actuales
		    filtrar(lUsuarioBox, textField);   
		});


		// Deshabilitar botones que requieren tener una fila seleccionada
		habilitarBotones(false);

	}
	
	private void habilitarBotones(boolean b) {
		btnActivar.setEnabled(b);
		btnDesactivar.setEnabled(b);

	}
	
	
	private void filtrar(JComboBox<String> combo, JTextField texto) {
		modelo.setRowCount(0);
		String filtro =combo.getSelectedItem().toString();
		List<UsuarioDTO> usuarios;
	
		
		
		//Solo Donante 
		if(filtro.equals("Donantes")) {
			
			usuarios = api.obtenerUserDonantes();
			for (UsuarioDTO u : usuarios) {
				modelo.addRow(new Object[] { u.getCodigo(), u.getRol(),u.getUsername(), u.getNombre(),u.getEmail(),u.getEstado() });
			}
			
			
		}else if(filtro.equals("Voluntarios")) {	//Solo Voluntario
			
			usuarios = api.obtenerUserVoluntarios();
			for (UsuarioDTO u : usuarios) {
				modelo.addRow(new Object[] { u.getCodigo(), u.getRol(),u.getUsername(), u.getNombre(),u.getEmail(),u.getEstado() });
			}
			
		}else if(filtro.equals("Administradores")) {//Solo ADM
			usuarios = api.obtenerUserAdministrador();
			for (UsuarioDTO u : usuarios) {
				modelo.addRow(new Object[] { u.getCodigo(), u.getRol(),u.getUsername(), u.getNombre(),u.getEmail(),u.getEstado() });
			}
			
		}else {//todos
			
			usuarios = api.obtenerUsuarios();
			
			for (UsuarioDTO u : usuarios) {
				modelo.addRow(new Object[] { u.getCodigo(), u.getRol(),u.getUsername(), u.getNombre(),u.getEmail(),u.getEstado() });
			}
			
		}
				
		
	}
	
	
}

