package ar.edu.unrn.seminario.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

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
import ar.edu.unrn.seminario.dto.SolicitudBienDTO;
import ar.edu.unrn.seminario.dto.VoluntarioDTO;
import com.toedter.calendar.JCalendar;

public class AltaOrdenEntrega extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;



	private OrdenPedidoDTO ordenSeleccionada;
    private VoluntarioDTO voluntario;

	private JTextField txtFecha;
	private JTextField txtEstado;
	private JTextField txtCodigo;
	IApi api;
	private JTextField txtCodVoluntario;

	/**
	 * Create the frame.
	 */
	public AltaOrdenEntrega(IApi api, SolicitudBienDTO solicitud) {
		this.api=api;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 381, 503);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel codBeneficiario = new JLabel("Codigo Pedido:");
		codBeneficiario.setBounds(10, 11, 133, 13);
		contentPane.add(codBeneficiario);
		
		JLabel lblNewLabel_1_1 = new JLabel("Estado:");
		lblNewLabel_1_1.setBounds(10, 119, 102, 13);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("Fecha Emision:");
		lblNewLabel_2.setBounds(10, 156, 96, 13);
		contentPane.add(lblNewLabel_2);
		
		txtFecha = new JTextField();
		txtFecha.setEditable(false);
		txtFecha.setBounds(140, 152, 96, 19);
		contentPane.add(txtFecha);
		txtFecha.setColumns(10);
		
		txtEstado = new JTextField();
		txtEstado.setEditable(false);
		txtEstado.setColumns(10);
		txtEstado.setBounds(140, 115, 96, 19);
		contentPane.add(txtEstado);
		
		txtCodigo = new JTextField();
		txtCodigo.setEditable(false);
		txtCodigo.setColumns(10);
		txtCodigo.setBounds(140, 7, 96, 19);
		contentPane.add(txtCodigo);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(175, 432, 85, 21);
		contentPane.add(btnGuardar);
		
		JButton btnNewButton_1 = new JButton("Cerrar");
		btnNewButton_1.setBounds(270, 432, 85, 21);
		contentPane.add(btnNewButton_1);
		 btnNewButton_1.addActionListener(e -> dispose());
		
		JLabel seleccionarVoluntario = new JLabel("Selecionar Voluntario");
		seleccionarVoluntario.setBounds(10, 61, 133, 13);
		contentPane.add(seleccionarVoluntario);
		
		JButton btnVoluntarios = new JButton("Voluntario");
		btnVoluntarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListadoVoluntarios ventanaVoluntarios = new ListadoVoluntarios(null, api,AltaOrdenEntrega.this);
				ventanaVoluntarios.setLocationRelativeTo(AltaOrdenEntrega.this);
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
		
		JLabel codVoluntario = new JLabel("Codigo Voluntario");
		codVoluntario.setBounds(10, 88, 133, 13);
		contentPane.add(codVoluntario);
		
		JLabel lblNewLabel_2_1 = new JLabel("Fecha Programada:");
		lblNewLabel_2_1.setBounds(10, 193, 96, 13);
		contentPane.add(lblNewLabel_2_1);
		
		JCalendar calendar = new JCalendar();
		calendar.setBounds(107, 193, 184, 153);
		contentPane.add(calendar);
		
		JButton btnVerBienes = new JButton("Ver Bienes");
		btnVerBienes.setBounds(10, 386, 102, 21);
		contentPane.add(btnVerBienes);
		btnGuardar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		      
		    	String codigo = txtCodigo.getText();
		    	String estado= "Pendiente";
		    	String fechaTexto = txtFecha.getText();

		    	try {
		    	    // Convertir texto a LocalDate
		    	    LocalDate fecha = LocalDate.parse(fechaTexto);
		    	    String[] codVisitas = {};
		    	    // Crear DTO (no entidad)
		    	    OrdenRetiroDTO retiro = new OrdenRetiroDTO(

		    	    	    fecha,

		    	    	    estado,                    // estado
		    	    	    null,                     
		    	    	    
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
