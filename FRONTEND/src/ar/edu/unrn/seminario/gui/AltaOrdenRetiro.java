package ar.edu.unrn.seminario.gui;


import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.OrdenPedidoDTO;
import ar.edu.unrn.seminario.dto.OrdenRetiroDTO;

import javax.swing.JButton;

public class AltaOrdenRetiro extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private OrdenPedidoDTO ordenSeleccionada;


	private JTextField txtFecha;
	private JTextField txtEstado;
	private JTextField txtCodigo;
	IApi api;

	
	AltaOrdenRetiro(IApi api) {
		this.api=api;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 381, 227);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Seleccionar:");
		lblNewLabel.setBounds(10, 10, 133, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Codigo Pedido:");
		lblNewLabel_1.setBounds(10, 33, 133, 13);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Estado:");
		lblNewLabel_1_1.setBounds(10, 78, 102, 13);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("Fecha Emision:");
		lblNewLabel_2.setBounds(10, 102, 96, 13);
		contentPane.add(lblNewLabel_2);
		
		txtFecha = new JTextField();
		txtFecha.setBounds(140, 100, 96, 19);
		contentPane.add(txtFecha);
		txtFecha.setColumns(10);
		
		txtEstado = new JTextField();
		txtEstado.setEditable(false);
		txtEstado.setColumns(10);
		txtEstado.setBounds(140, 74, 96, 19);
		contentPane.add(txtEstado);
		
		txtCodigo = new JTextField();
		txtCodigo.setEditable(false);
		txtCodigo.setColumns(10);
		txtCodigo.setBounds(140, 29, 96, 19);
		contentPane.add(txtCodigo);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(30, 159, 85, 21);
		contentPane.add(btnGuardar);
		
		JButton btnNewButton_1 = new JButton("Cerrar");
		btnNewButton_1.setBounds(232, 159, 85, 21);
		contentPane.add(btnNewButton_1);
		 btnNewButton_1.addActionListener(e -> dispose());
		JButton btnOrdenesPedido = new JButton("Ordenes Pedido");
		btnOrdenesPedido.setBounds(140, 6, 151, 21);
		contentPane.add(btnOrdenesPedido);
		btnOrdenesPedido.addActionListener(e -> {
					VentanaOrdenPedido ventanaOrdenPedido = new VentanaOrdenPedido(this, api);
					ventanaOrdenPedido.setLocationRelativeTo(this);
					ventanaOrdenPedido.setVisible(true);
			});
		btnGuardar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		      
		    	String codigo = txtCodigo.getText();
		    	ordenSeleccionada.getEstado();
		    	String fechaTexto = txtFecha.getText();

		    	try {
		    	    // Convertir texto a LocalDateTime
		    	    LocalDate fecha = LocalDate.parse(fechaTexto);

		    	    // Crear DTO (no entidad)
		    	    OrdenRetiroDTO retiro = new OrdenRetiroDTO(
		    	    	    fecha,                                  // fecha de emisión
		    	    	    ordenSeleccionada.getEstado(),                    // estado
		    	    	    ordenSeleccionada.getTipo(),            // tipo de la orden
		    	    	    txtCodigo.getText(),                     // código de la orden de retiro
		    	    	    ordenSeleccionada.getCodigo(),           // código del pedido
		    	    	    null,                                   // código del voluntario (lo asignarás después)
		    	    	    null                                    // visitas aún no asignadas
		    	    	);

		    	    // Registrar la orden de retiro en memoria
		    	    // en AltaOrdenRetiro, después de registrar el DTO
		    	    api.registrarOrdenRetiro(retiro);

		    	   
		    	    if (ordenSeleccionada != null && ordenSeleccionada.getCodigo() != null) {
		    	        api.inicializarOrdenesRetiro(ordenSeleccionada.getCodigo());
		    	    } else {
		    	      
		    	        api.inicializarOrdenesRetiro(retiro.getPedido());
		    	    }
		    	    // Limpieza de campos
		    	    ordenSeleccionada = null;
		    	    txtCodigo.setText("");
		    	    txtEstado.setText("");
		    	    txtFecha.setText("");
		    	    btnGuardar.addActionListener(e1 -> dispose());
		    	} catch (Exception ex) {
		    	    JOptionPane.showMessageDialog(null, 
		    	        "Error al crear la orden de retiro. Verificá el formato de la fecha (ejemplo: 2025-10-26T15:00)",
		    	        "Error",
		    	        JOptionPane.ERROR_MESSAGE);
		    	}
		    }
		});

		
	}
	public void recibirOrdenPedido(OrdenPedidoDTO orden) {
	    this.ordenSeleccionada = orden;
	 
	    txtCodigo.setText(orden.getCodigo()); 
	    txtEstado.setFont(orden.getEstado());
	    txtFecha.setText(orden.getFechaEmision().toString());
	}
}
