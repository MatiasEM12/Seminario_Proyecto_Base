package ar.edu.unrn.seminario.gui;


import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.OrdenPedidoDTO;
import ar.edu.unrn.seminario.dto.OrdenRetiroDTO;
import ar.edu.unrn.seminario.dto.VoluntarioDTO;
import ar.edu.unrn.seminario.modelo.Visita;

import javax.swing.JButton;

public class AltaOrdenRetiro extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private OrdenPedidoDTO ordenSeleccionada;
    private VoluntarioDTO voluntario;

	private JTextField txtFecha;
	private JTextField txtEstado;
	private JTextField txtCodigo;
	IApi api;
	private JTextField txtCodVoluntario;

	
	AltaOrdenRetiro(IApi api) {
		this.api=api;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 381, 310);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel seleccionarPedido = new JLabel("Seleccionar:");
		seleccionarPedido.setBounds(10, 10, 133, 13);
		contentPane.add(seleccionarPedido);
		
		JLabel codPedido = new JLabel("Codigo Pedido:");
		codPedido.setBounds(10, 33, 133, 13);
		contentPane.add(codPedido);
		
		JLabel lblNewLabel_1_1 = new JLabel("Estado:");
		lblNewLabel_1_1.setBounds(10, 137, 102, 13);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("Fecha Emision:");
		lblNewLabel_2.setBounds(16, 177, 96, 13);
		contentPane.add(lblNewLabel_2);
		
		txtFecha = new JTextField();
		txtFecha.setBounds(140, 173, 96, 19);
		contentPane.add(txtFecha);
		txtFecha.setColumns(10);
		
		txtEstado = new JTextField();
		txtEstado.setEditable(false);
		txtEstado.setColumns(10);
		txtEstado.setBounds(140, 133, 96, 19);
		contentPane.add(txtEstado);
		
		txtCodigo = new JTextField();
		txtCodigo.setEditable(false);
		txtCodigo.setColumns(10);
		txtCodigo.setBounds(140, 29, 96, 19);
		contentPane.add(txtCodigo);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(27, 239, 85, 21);
		contentPane.add(btnGuardar);
		
		JButton btnNewButton_1 = new JButton("Cerrar");
		btnNewButton_1.setBounds(233, 239, 85, 21);
		contentPane.add(btnNewButton_1);
		 btnNewButton_1.addActionListener(e -> dispose());
		JButton btnOrdenesPedido = new JButton("Ordenes Pedido");
		btnOrdenesPedido.setBounds(140, 6, 151, 21);
		contentPane.add(btnOrdenesPedido);
		
		JLabel seleccionarVoluntario = new JLabel("Seleccionar:");
		seleccionarVoluntario.setBounds(10, 61, 133, 13);
		contentPane.add(seleccionarVoluntario);
		
		JButton btnVoluntarios = new JButton("Ordenes Pedido");
		btnVoluntarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListadoVoluntarios ventanaVoluntarios = new ListadoVoluntarios(AltaOrdenRetiro.this, api);
				ventanaVoluntarios.setLocationRelativeTo(AltaOrdenRetiro.this);
				ventanaVoluntarios.setVisible(true);
			}
		});
		btnVoluntarios.setBounds(140, 57, 151, 21);
		contentPane.add(btnVoluntarios);
		
		txtCodVoluntario = new JTextField();
		txtCodVoluntario.setEditable(false);
		txtCodVoluntario.setColumns(10);
		txtCodVoluntario.setBounds(140, 85, 96, 19);
		contentPane.add(txtCodVoluntario);
		
		JLabel codVoluntario = new JLabel("Codigo Pedido:");
		codVoluntario.setBounds(10, 88, 133, 13);
		contentPane.add(codVoluntario);
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
		    	    // Convertir texto a LocalDate
		    	    LocalDate fecha = LocalDate.parse(fechaTexto);
		    	    String[] codVisitas = {};
		    	    // Crear DTO (no entidad)
		    	    OrdenRetiroDTO retiro = new OrdenRetiroDTO(

		    	    	    fecha,

		    	    	    ordenSeleccionada.getEstado().toString(),                    // estado

		    	    	   
		    	    	    txtCodigo.getText(),                     // código de la orden de retiro
		    	    	    ordenSeleccionada.getCodigo(),           // código del pedido
		    	    	    voluntario.getCodigo(),codVisitas
		    	    	);
		    	    api.registrarOrdenRetiro(retiro);

                  
                    // Limpieza
                    ordenSeleccionada = null;
                    txtCodigo.setText("");
                    txtEstado.setText("");
                    txtFecha.setText("");

                    JOptionPane.showMessageDialog(null, 
                        "Orden de Retiro registrada correctamente.",
                        "OK",
                        JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, 
                        "Error al crear la orden de retiro.\nFormato de fecha válido: 2025-10-26",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void recibirOrdenPedido(OrdenPedidoDTO orden) {
        this.ordenSeleccionada = orden;

        txtCodigo.setText(orden.getCodigo());
        txtEstado.setText(orden.getEstado().toString());             
        txtFecha.setText(orden.getFechaEmision().toString());
    }
    void recibirVoluntario(VoluntarioDTO voluntario) {
       this.voluntario=voluntario;
       txtCodVoluntario.setText(voluntario.getCodigo());
    }
}
