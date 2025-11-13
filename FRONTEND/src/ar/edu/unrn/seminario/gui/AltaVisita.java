package ar.edu.unrn.seminario.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.OrdenPedidoDTO;
import ar.edu.unrn.seminario.dto.OrdenRetiroDTO;
import ar.edu.unrn.seminario.dto.VisitaDTO;
import ar.edu.unrn.seminario.dto.VoluntarioDTO;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;

public class AltaVisita extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private VoluntarioDTO voluntario;
	private IApi api;
	private JTextField txtFecha;
	private JTextField txtCodigo;
	private JTextField textField;

	
	AltaVisita(IApi api) {
		this.api=api;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 414, 378);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Fecha:");
		lblNewLabel.setBounds(10, 30, 133, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1_1 = new JLabel("Tipo:");
		lblNewLabel_1_1.setBounds(9, 107, 102, 13);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("Observaciones:");
		lblNewLabel_2.setBounds(10, 146, 96, 13);
		contentPane.add(lblNewLabel_2);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(161, 307, 85, 21);
		contentPane.add(btnGuardar);
		
		JButton btnNewButton_1 = new JButton("Cerrar");
		btnNewButton_1.setBounds(290, 307, 85, 21);
		contentPane.add(btnNewButton_1);
		JLabel lblVoluntario = new JLabel("Voluntario asignado:");
		// Llenado del combo
		for (VoluntarioDTO v : api.obtenerVoluntarios()) {
		    comboVoluntarios.addItem(v);
		}

		// Agregado a la ventana
		contentPane.add(lblVoluntario);
		
		txtFecha = new JTextField();
		txtFecha.setBounds(140, 30, 151, 19);
		contentPane.add(txtFecha);
		txtFecha.setColumns(10);
		
		JLabel lblCodigo = new JLabel("Codigo OrdenRetiro:");
		lblCodigo.setBounds(10, 10, 133, 13);
		contentPane.add(lblCodigo);
		
		txtCodigo = new JTextField();
		txtCodigo.setColumns(10);
		txtCodigo.setBounds(140, 7, 151, 19);
		contentPane.add(txtCodigo);
	
		btnGuardar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		      
		    	String codigo = txtCodigo.getText();
		    	String fechaTexto = txtFecha.getText();
		    	String observaciones=txtObservaciones.getText();
		    	VoluntarioDTO vSel = (VoluntarioDTO) comboVoluntarios.getSelectedItem();
		        String tipo = (String) comboTipo.getSelectedItem();
		        
		    	try {
		    	    // Convertir texto a LocalDateTime
		    	    LocalDateTime fecha = LocalDateTime.parse(fechaTexto);

		    	    // Crear DTO (no entidad)
		    	    VisitaDTO visita = new VisitaDTO(
		                    codigo,
		                    fecha,
		                    vSel.getCodigo(),  // asociamos el voluntario por su código
		                    observaciones,
		                    tipo
		                );

		    	 // Registrar usando la API (capa Facade)
		            api.registrarVisita(visita);

		            // Avisar y limpiar
		            JOptionPane.showMessageDialog(contentPane, "Visita registrada correctamente.", "OK", JOptionPane.INFORMATION_MESSAGE);
		            txtCodigo.setText("");
		            txtFecha.setText("");
		            txtObservaciones.setText("");
		            comboVoluntarios.setSelectedIndex(0);
		            comboTipo.setSelectedIndex(0);

		   // 	    btnGuardar.addActionListener(e1 -> dispose());
		    	} catch (Exception ex) {
		    	    JOptionPane.showMessageDialog(null, 
		    	        "Error al crear la orden de retiro. Verificá el formato de la fecha (ejemplo: 2025-10-26T15:00)",
		    	        "Error",
		    	        JOptionPane.ERROR_MESSAGE);
		    	}
		    }
		});

		setContentPane(contentPane);       // montar el panel en el frame
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancelar.setBounds(26, 307, 85, 21);
		contentPane.add(btnCancelar);
		
		JButton btnSeleccionBien = new JButton("Bienes");
		btnSeleccionBien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSeleccionBien.setBounds(89, 78, 89, 23);
		contentPane.add(btnSeleccionBien);
		
		textField = new JTextField();
		textField.setBounds(89, 146, 258, 78);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Seleccioonar ");
		lblNewLabel_1_1_1.setBounds(9, 83, 102, 13);
		contentPane.add(lblNewLabel_1_1_1);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Visita Final");
		rdbtnNewRadioButton.setBounds(26, 264, 109, 23);
		contentPane.add(rdbtnNewRadioButton);
        setLocationRelativeTo(null);       // centrar
        setVisible(true);  
	}
}