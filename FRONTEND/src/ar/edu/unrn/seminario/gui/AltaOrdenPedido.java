package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.MemoryApi;
import ar.edu.unrn.seminario.dto.DonacionDTO;
import ar.edu.unrn.seminario.dto.OrdenPedidoDTO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class AltaOrdenPedido extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

    private JTextField txtFecha;
    private JTextField txtEstado;
    private JTextField txtCodigo;
    private DonacionDTO donacionSeleccionada;
    IApi api;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IApi api=new MemoryApi();
					AltaOrdenPedido frame = new AltaOrdenPedido(api);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	    public AltaOrdenPedido(IApi api) {
	        this.api = api;

	        setTitle("Alta Orden de Pedido");
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setBounds(100, 100, 381, 227);

	        contentPane = new JPanel();
	        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	        contentPane.setLayout(null);
	        setContentPane(contentPane);

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

	        txtEstado = new JTextField();
	        txtEstado.setEditable(false);
	        txtEstado.setBounds(140, 74, 96, 19);
	        contentPane.add(txtEstado);

	        txtCodigo = new JTextField();
	        txtCodigo.setEditable(false);
	        txtCodigo.setBounds(140, 29, 96, 19);
	        contentPane.add(txtCodigo);

	        JButton btnGuardar = new JButton("Guardar");
	        btnGuardar.setBounds(30, 159, 85, 21);
	        contentPane.add(btnGuardar);

	        JButton btnCerrar = new JButton("Cerrar");
	        btnCerrar.setBounds(232, 159, 85, 21);
	        btnCerrar.addActionListener(e -> dispose());
	        contentPane.add(btnCerrar);

	        JButton btnDonaciones = new JButton("Donaciones");
	        btnDonaciones.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        	}
	        });
	        btnDonaciones.setBounds(140, 6, 151, 21);
	        contentPane.add(btnDonaciones);
	        btnDonaciones.addActionListener(e -> {
				ListadoDonaciones listadoDonaciones = new ListadoDonaciones(api);
				listadoDonaciones.setLocationRelativeTo(this);
				listadoDonaciones.setVisible(true);
		});
	        btnGuardar.addActionListener(e -> {
	            String fechaTexto = txtFecha.getText();
	            try {
	                // LocalDate espera formato yyyy-MM-dd (sin hora)
	                LocalDate fecha = LocalDate.parse(fechaTexto);

	                OrdenPedidoDTO ordenPedido = new OrdenPedidoDTO(
	                    fecha,
	                    txtEstado.getText(),
	                    null,
	                    txtCodigo.getText(),
	                    true,
	                    null,
	                    null,
	                    null
	                );

	                api.registrarOrdenPedido(ordenPedido);

	                // Limpieza
	                txtCodigo.setText("");
	                txtEstado.setText("");
	                txtFecha.setText("");
	                
	                JLabel lblNewLabel_1_1_1 = new JLabel("Carga:");
	                lblNewLabel_1_1_1.setBounds(10, 55, 102, 13);
	                contentPane.add(lblNewLabel_1_1_1);
	                
	                JCheckBox chckbxNewCheckBox = new JCheckBox("Pesada");
	                chckbxNewCheckBox.setBounds(140, 47, 93, 21);
	                contentPane.add(chckbxNewCheckBox);
	                
	                JCheckBox chckbxLiviana = new JCheckBox("Liviana");
	                chckbxLiviana.setBounds(242, 47, 93, 21);
	                contentPane.add(chckbxLiviana);

	                // Si querés cerrar después de guardar:
	                // dispose();
	            } catch (Exception ex) {
	                JOptionPane.showMessageDialog(
	                    this,
	                    "Error al crear la orden. Formato de fecha: 2025-10-26",
	                    "Error",
	                    JOptionPane.ERROR_MESSAGE
	                );
	            }
	        });
	    }
	    
	    public void recibirDonaciones(DonacionDTO donacion) {
	        this.donacionSeleccionada = donacion;

	      //  txtCodigo.setText(donacion.getCodigo());
	        //txtEstado.setText(donacion.getEstado().toString());             
	        //txtFecha.setText(donacion.getFechaEmision().toString());
	    }
	}
